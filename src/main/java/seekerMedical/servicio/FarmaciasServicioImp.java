package seekerMedical.servicio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import seekerMedical.dto.FarmaciaRequest;
import seekerMedical.dto.FarmaciasDTO;
import seekerMedical.entidad.Farmacias;
import seekerMedical.repositorio.FarmaciasRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component /* Utilizado para indicar una clase como componente
Spring detectará automáticamente estas clases para inyección de dependencias*/
public class FarmaciasServicioImp implements FarmaciasServicio {

    @Autowired // Permite inyectar unas dependencias con otras
    private FarmaciasRepositorio farmaciasRepositorio;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que estás
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución*/
    public List<FarmaciasDTO> findAll() {
        List<FarmaciasDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<Farmacias> farmacias = this.farmaciasRepositorio.findAll();
        for (Farmacias far: farmacias){
            FarmaciasDTO farDTO = MHelpers.modelMapper().map(far, FarmaciasDTO.class);
            dto.add(farDTO);
        }
        return dto;
    }

    @Override
    public FarmaciasDTO findByFarCorreoElec(String farCorreoElec) {
        Optional<Farmacias> farmacias = this.farmaciasRepositorio.findByFarCorreoElec(farCorreoElec);
        if (!farmacias.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(farmacias.get(), FarmaciasDTO.class);
    }

    @Override
    public FarmaciasDTO findByFarRuc(String farRuc) {
        Optional<Farmacias> farmacias = this.farmaciasRepositorio.findById(farRuc);
        if (!farmacias.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(farmacias.get(), FarmaciasDTO.class);
    }

    @Override
    public void save(FarmaciaRequest farmaciasDTO) {
        Farmacias farmacias = MHelpers.modelMapper().map(farmaciasDTO, Farmacias.class);
        this.farmaciasRepositorio.save(farmacias);
    }

    @Override
    public void update(FarmaciaRequest request, String id) {
        Optional<Farmacias> farmacia = this.farmaciasRepositorio.findById(id);
        Farmacias faruc = farmacia.get();
        faruc.setFarNombreNegocio(request.getFarNombreNegocio());
        faruc.setFarCorreoElec(request.getFarCorreoElec());
        faruc.setFarLogotipo(request.getFarLogotipo());
        faruc.setFarDireccionCalles(request.getFarDireccionCalles());
        faruc.setFarDireccionCom(request.getFarDireccionCom());
        faruc.setFarDireccionRef(request.getFarDireccionRef());
        faruc.setFarTelefonoMov(request.getFarTelefonoMov());
        faruc.setFarTelefonoCon(request.getFarTelefonoCon());
        faruc.setFarTagW(request.getFarTagW());
        faruc.setFarUbicacionLat(request.getFarUbicacionLat());
        faruc.setFarUbicacionLon(request.getFarUbicacionLon());
        faruc.setFarTagD(request.getFarTagD());
        faruc.setFarTag247(request.getFarTag247());
        this.farmaciasRepositorio.save(faruc);
    }

    @Override
    public void saveAll(List<FarmaciaRequest> farmacias) {
        List<Farmacias> far = new ArrayList<>();
        for(FarmaciaRequest faruc: farmacias){
            Farmacias f = MHelpers.modelMapper().map(faruc,Farmacias.class);
            far.add(f);
        }
        this.farmaciasRepositorio.saveAll(far);
    }

    @Override
    public void deleteByFarRuc(String farRuc) {
        this.farmaciasRepositorio.deleteById(farRuc);
    }
    @Override
    public void updateClave(FarmaciasDTO farmaciaDTO) {
        // Obtener la entidad original de la base de datos
        Farmacias farmacia = this.farmaciasRepositorio.findById(farmaciaDTO.getFarRuc()).orElse(null);
        if (farmacia != null) {
            // Actualiza solo la contraseña
            farmacia.setFarClave(farmaciaDTO.getFarClave());
            // Guarda la entidad actualizada
            this.farmaciasRepositorio.save(farmacia);
        }
    }

    @Override
    public boolean validaCorreoPerteneceFarmacia(String farRuc, String farCorreoElec) {
        // Busca a la farmacia por su RUC
        Optional<Farmacias> farmaciaOptional = this.farmaciasRepositorio.findById(farRuc);
        if (farmaciaOptional.isPresent()) {
            // Verifica si el correo electrónico coincide con la farmacia encontrada
            Farmacias farmacia = farmaciaOptional.get();
            return farmacia.getFarCorreoElec().equals(farCorreoElec);
        } else {
            // Si la farmacia no está registrada, retorna falso
            return false;
        }
    }

    @Override
    public void enviarClaveTemporal(String farRuc, String farCorreoElec, String nuevaClaveTemporal) {
        // Actualiza la clave en la base de datos
        Optional<Farmacias> farmaciaOptional = this.farmaciasRepositorio.findById(farRuc);
        if (farmaciaOptional.isPresent()) {
            Farmacias farmacia = farmaciaOptional.get();
            // Imprime la clave temporal sin cifrar antes de actualizar
            System.out.println("Clave Temporal Sin Cifrar: " + nuevaClaveTemporal);
            farmacia.setFarClave(nuevaClaveTemporal);
            farmaciasRepositorio.save(farmacia);
            // Imprime un mensaje cuando la actualización es exitosa
            System.out.println("MedClave actualizado correctamente");
            // Envía la clave temporal sin cifrar por correo electrónico solo si la validación es exitosa
            enviarCorreoElectronico(farCorreoElec, "Clave Temporal de acceso a Seeker Medical", "Estimado(a) administrador de farmacia,<br><br>"
                    + "Tu nueva clave temporal es: " + nuevaClaveTemporal + "<br>"
                    + "Si usted no solicitó la clave temporal, no dudes en contactarnos al número 0983894072.<br><br>"
                    + "Saludos,<br>El equipo de Seeker Medical");
            // Imprime un mensaje después de enviar el correo
            System.out.println("Correo electrónico enviado correctamente");
        }
    }

    @Override
    public void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo) {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);
        try {
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo, true);  // El parámetro true indica que el cuerpo es HTML
            javaMailSender.send(mensaje);
        } catch (MessagingException e) {
            // Maneja la excepción en caso de error al enviar el correo
            e.printStackTrace();
        }
    }

    private FarmaciasDTO convertToFarmaciasDTO(final Farmacias farmacias){
        return MHelpers.modelMapper().map(farmacias, FarmaciasDTO.class);
    }
}