package seekerMedical.servicio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import seekerMedical.dto.MedicosDTO;
import seekerMedical.dto.MedicoRequest;
import seekerMedical.entidad.EspMedMedicos;
import seekerMedical.entidad.EspecialidadesMedicas;
import seekerMedical.entidad.Medicos;
import seekerMedical.repositorio.EspMedMedicosRepositorio;
import seekerMedical.repositorio.MedicosRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component /* Utilizado para indicar una clase como componente
Spring detectará automáticamente estas clases para inyección de dependencias*/
public class MedicosServicioImp implements MedicosServicio {
    @Autowired // Permite inyectar unas dependencias con otras
    private MedicosRepositorio medicosRepositorio;

    @Autowired // Permite inyectar unas dependencias con otras
    private EspMedMedicosRepositorio espMedMedicosRepositorio;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que estás
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución*/
    public List<MedicosDTO> findAll() {
        List<MedicosDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<Medicos> medicos = this.medicosRepositorio.findAll();
        for (Medicos med: medicos){
            MedicosDTO medDTO = MHelpers.modelMapper().map(med, MedicosDTO.class);
            dto.add(medDTO);
        }
        return dto;
    }

    @Override
    public MedicosDTO findByMedCorreoElec(String medCorreoElec) {
        Optional<Medicos> medicos = this.medicosRepositorio.findByMedCorreoElec(medCorreoElec);
        if (!medicos.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(medicos.get(), MedicosDTO.class);
    }

    @Override
    public MedicosDTO findByMedCi(String medCi) {
        Optional<Medicos> medicos = this.medicosRepositorio.findById(medCi);
        if (!medicos.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(medicos.get(), MedicosDTO.class);
    }

    @Override
    public void save(MedicoRequest medicosDTO) {
        Medicos medicos = MHelpers.modelMapper().map(medicosDTO, Medicos.class);
        this.medicosRepositorio.save(medicos);
    }

    @Override
    public void update(MedicoRequest request, String id) {
        Optional<Medicos> medico = this.medicosRepositorio.findById(id);
        Medicos meci = medico.get();
        meci.setMedNombres(request.getMedNombres());
        meci.setMedApellidos(request.getMedApellidos());
        meci.setMedCorreoElec(request.getMedCorreoElec());
        meci.setMedFotografia(request.getMedFotografia());
        meci.setMedDenominacion(request.getMedDenominacion());
        meci.setMedDescripcionPro(request.getMedDescripcionPro());
        meci.setMedDireccionCalles(request.getMedDireccionCalles());
        meci.setMedDireccionCom(request.getMedDireccionCom());
        meci.setMedDireccionRef(request.getMedDireccionRef());
        meci.setMedTelefonoMov(request.getMedTelefonoMov());
        meci.setMedTelefonoCon(request.getMedTelefonoCon());
        meci.setMedTagW(request.getMedTagW());
        meci.setMedUbicacionLat(request.getMedUbicacionLat());
        meci.setMedUbicacionLon(request.getMedUbicacionLon());
        meci.setMedTagD(request.getMedTagD());
        meci.setMedTagE(request.getMedTagE());
        meci.setMedTagM(request.getMedTagM());
        meci.setMedTag247(request.getMedTag247());
        this.medicosRepositorio.save(meci);
    }

    @Override
    public void saveAll(List<MedicoRequest> medicos) {
        List<Medicos> med = new ArrayList<>();
        for(MedicoRequest meci: medicos){
            Medicos m = MHelpers.modelMapper().map(meci,Medicos.class);
            med.add(m);
        }
        this.medicosRepositorio.saveAll(med);
    }

    @Override
    public void deleteByMedCi(String medCi) {
        this.medicosRepositorio.deleteById(medCi);
    }

    @Override
    public void updateClave(MedicosDTO medicoDTO) {
        // Obtiene la entidad original de la base de datos
        Medicos medico = this.medicosRepositorio.findById(medicoDTO.getMedCi()).orElse(null);
        if (medico!= null) {
            // Actualiza solo la contraseña
            medico.setMedClave(medicoDTO.getMedClave());
            // Guarda la entidad actualizada
            this.medicosRepositorio.save(medico);
        }
    }

    @Override
    public EspecialidadesMedicas findEspecialidadByEmmId(int emmId) {
        // Realiza una búsqueda en la base de datos para encontrar la especialidad por emmId
        Optional<EspMedMedicos> espMedOptional = espMedMedicosRepositorio.findByEmmId(emmId);

        // Verifica si se encontró la relación EspMedMedicos
        if (espMedOptional.isPresent()) {
            EspMedMedicos espMed = espMedOptional.get();
            return espMed.getEmId();
        } else {
            // Manejo si no se encuentra la relación EspMedMedicos con el emmId proporcionado
            return null;
        }
    }

    @Override
    public boolean validaCorreoPerteneceMedico(String medCi, String medCorreoElec) {
        // Busca al médico por su cédula
        Optional<Medicos> medicoOptional = this.medicosRepositorio.findById(medCi);

        if (medicoOptional.isPresent()) {
            // Verifica si el correo electrónico coincide con el médico encontrado
            Medicos medico = medicoOptional.get();
            return medico.getMedCorreoElec().equals(medCorreoElec);
        } else {
            // Si el médico no está registrado, retorna falso
            return false;
        }
    }

    @Override
    public void enviarClaveTemporal(String medCi, String medCorreoElec, String nuevaClaveTemporal) {
        // Actualiza la clave en la base de datos
        Optional<Medicos> medicoOptional = this.medicosRepositorio.findById(medCi);
        if (medicoOptional.isPresent()) {
            Medicos medico = medicoOptional.get();
            // Imprime la clave temporal sin cifrar antes de actualizar
            System.out.println("Clave Temporal Sin Cifrar: " + nuevaClaveTemporal);
            medico.setMedClave(nuevaClaveTemporal);
            medicosRepositorio.save(medico);
            // Imprime un mensaje cuando la actualización es exitosa
            System.out.println("MedClave actualizado correctamente");
            // Envía la clave temporal sin cifrar por correo electrónico solo si la validación es exitosa
            enviarCorreoElectronico(medCorreoElec, "Clave Temporal de acceso a Seeker Medical", "Estimado(a) médico,<br><br>"
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

    private MedicosDTO convertToMedicosDTO(final Medicos medicos){
        return MHelpers.modelMapper().map(medicos, MedicosDTO.class);
    }
}