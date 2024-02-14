package seekerMedical.servicio;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import seekerMedical.utilidades.MHelpers;
import seekerMedical.dto.PacienteRequest;
import seekerMedical.dto.PacientesDTO;
import seekerMedical.entidad.Pacientes;
import seekerMedical.repositorio.PacientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component /* Utilizado para indicar una clase como componente
Spring detectará automáticamente estas clases para inyección de dependencias*/
public class PacientesServicioImp implements PacientesServicio {

    @Autowired // Permite inyectar unas dependencias con otras
    private PacientesRepositorio pacientesRepositorio;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que estás
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución*/
    public List<PacientesDTO> findAll() {
        List<PacientesDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<Pacientes> pacientes = this.pacientesRepositorio.findAll();
        for (Pacientes pac: pacientes){
            PacientesDTO pacDTO = MHelpers.modelMapper().map(pac, PacientesDTO.class);
            dto.add(pacDTO);
        }
        return dto;
    }

    @Override
    public PacientesDTO findByPacCorreoElec(String pacCorreoElec) {
        Optional<Pacientes> pacientes = this.pacientesRepositorio.findByPacCorreoElec(pacCorreoElec);
        if (!pacientes.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(pacientes.get(), PacientesDTO.class);
    }

    @Override
    public PacientesDTO findByPacCi(String pacCi) {
        Optional<Pacientes> pacientes = this.pacientesRepositorio.findById(pacCi);
        if (!pacientes.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(pacientes.get(), PacientesDTO.class);
    }

    @Override
    public void save(PacienteRequest pacientesDTO) {
        Pacientes pacientes = MHelpers.modelMapper().map(pacientesDTO, Pacientes.class);
        this.pacientesRepositorio.save(pacientes);
    }

    @Override
    public void update(PacienteRequest request, String id) {
        Optional<Pacientes> paciente = this.pacientesRepositorio.findById(id);
        Pacientes paci = paciente.get();
        paci.setPacNombres(request.getPacNombres());
        paci.setPacApellidos(request.getPacApellidos());
        paci.setPacCorreoElec(request.getPacCorreoElec());
        paci.setPacFotografia(request.getPacFotografia());
        paci.setPacTelefonoMov(request.getPacTelefonoMov());
        this.pacientesRepositorio.save(paci);
    }

    @Override
    public void saveAll(List<PacienteRequest> pacientes) {
        List<Pacientes> pac = new ArrayList<>();
        for(PacienteRequest paci: pacientes){
            Pacientes p = MHelpers.modelMapper().map(paci,Pacientes.class);
            pac.add(p);
        }
        this.pacientesRepositorio.saveAll(pac);
    }

    @Override
    public void deleteByPacCi(String pacCi) {
        this.pacientesRepositorio.deleteById(pacCi);
    }

    @Override
    public void updateClave(PacientesDTO pacienteDTO) {
        // Convertir el objeto PacientesDTO a la entidad Pacientes
        Pacientes paciente = MHelpers.modelMapper().map(pacienteDTO, Pacientes.class);
        // Actualizar la entidad Pacientes en la base de datos
        this.pacientesRepositorio.save(paciente);
    }

    @Override
    public boolean validaCorreoPertenecePaciente(String pacCi, String pacCorreoElec) {
        // Busca al paciente por su cédula
        Optional<Pacientes> pacienteOptional = this.pacientesRepositorio.findById(pacCi);
        if (pacienteOptional.isPresent()) {
            // Verifica si el correo electrónico coincide con el paciente encontrado
            Pacientes paciente = pacienteOptional.get();
            return paciente.getPacCorreoElec().equals(pacCorreoElec);
        } else {
            // Si el paciente no está registrado, retorna falso
            return false;
        }
    }

    @Override
    public void enviarClaveTemporal(String pacCi, String pacCorreoElec, String nuevaClaveTemporal) {
        // Actualiza la clave en la base de datos
        Optional<Pacientes> pacienteOptional = this.pacientesRepositorio.findById(pacCi);
        if (pacienteOptional.isPresent()) {
            Pacientes paciente = pacienteOptional.get();
            // Imprime la clave temporal sin cifrar antes de actualizar
            System.out.println("Clave Temporal Sin Cifrar: " + nuevaClaveTemporal);
            paciente.setPacClave(nuevaClaveTemporal);
            pacientesRepositorio.save(paciente);
            // Imprime un mensaje cuando la actualización es exitosa
            System.out.println("PacClave actualizado correctamente");
            // Envía la clave temporal sin cifrar por correo electrónico solo si la validación es exitosa
            enviarCorreoElectronico(pacCorreoElec, "Clave Temporal de acceso a Seeker Medical", "Estimado(a) paciente,<br><br>"
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

    private PacientesDTO convertToPacientesDTO(final Pacientes pacientes){
        return MHelpers.modelMapper().map(pacientes, PacientesDTO.class);
    }
}