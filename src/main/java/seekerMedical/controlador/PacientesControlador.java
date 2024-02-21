package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.PacientesDTO;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.dto.PacienteRequest;
import seekerMedical.servicio.PacientesServicio;
import seekerMedical.validador.PacienteValidadorImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import seekerMedical.validador.Validaciones;
import java.security.SecureRandom;
import java.util.Base64;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/pacientes") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class PacientesControlador {

    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar con los servicios
    private PacientesServicio pacientesServicio;

    @Autowired
    private PacienteValidadorImpl pacienteValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produce = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)//Simplifica el manejo de los métodos
    // Retorna la lista colocada en pacientesServicio
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(this.pacientesServicio.findAll());
    }

    // Método para validar la existencia de la cédula
    @GetMapping("/validaExistenciaPacCi/{pacCi}")
    public ResponseEntity<String> validaExistenciaPacCi(@PathVariable String pacCi) {
        if (pacienteValidadorImpl.validaExistenciaPacCi(pacCi)) {
            return ResponseEntity.ok("El paciente ya existe");
        } else {
            return ResponseEntity.ok("El paciente no existe");
        }
    }

    // Método para validar la cédula ecuatoriana
    @GetMapping("/validaCedulaEcuatoriana/{pacCi}")
    public ResponseEntity<String> validaCedulaEcuatoriana(@PathVariable String pacCi) {
        if (validaciones.validaCedulaEcuatoriana(pacCi)) {
            return ResponseEntity.ok("La cédula es válida");
        } else {
            return ResponseEntity.ok("La cédula no es válida");
        }
    }

    // Método para validar la existencia del correo electrónico
    @GetMapping("/validaExistenciaPacCorreoElec/{pacCorreoElec}")
    public ResponseEntity<String> validaExistenciaPacCorreoElec(@PathVariable String pacCorreoElec) {
        if (pacienteValidadorImpl.validaExistenciaPacCorreoElec(pacCorreoElec)) {
            return ResponseEntity.ok("El correo ya existe");
        } else {
            return ResponseEntity.ok("El correo no existe");
        }
    }

    // Método para validar el correo electrónico
    @GetMapping("/validaCorreoElec/{pacCorreoElec}")
    public ResponseEntity<String> validaCorreoElec(@PathVariable String pacCorreoElec) {
        if (validaciones.validaCorreoElec(pacCorreoElec)) {
            return ResponseEntity.ok("El correo es válido");
        } else {
            return ResponseEntity.ok("El correo no es válido");
        }
    }

    // Método para obtener información del paciente por pacCi
    @GetMapping(value = "/pacCi/{pacCi}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByPacCi(@PathVariable("pacCi") String pacCi) {
        // Obtiene información del paciente por su ID
        PacientesDTO paciente = this.pacientesServicio.findByPacCi(pacCi);
        // Verifica si se encontró el paciente
        if (paciente != null) {
            // Verifica si la fotografía del paciente no es nula
            if (paciente.getPacFotografia() != null) {
                // Convierte bytes a base64
                String base64Image = Base64.getEncoder().encodeToString(paciente.getPacFotografia());
                paciente.setPacFotografiaBase64(base64Image);
            }
            // Log para imprimir la respuesta antes de devolverla
            System.out.println("Respuesta del servicio: " + paciente.toString());
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para obtener información por su correo
    @GetMapping(value = "/correo/{correo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByPacCorreoElec(@PathVariable("correo") String correo) {
        return ResponseEntity.ok(this.pacientesServicio.findByPacCorreoElec(correo));
    }

    // Método para validar que el correo electrónico pertenezca al paciente
    @GetMapping("/validaCorreoPertenecePaciente/{pacCi}/{pacCorreoElec}")
    public ResponseEntity<String> validaCorreoPertenecePaciente(
            @PathVariable String pacCi,
            @PathVariable String pacCorreoElec) {
        // Verifica si el correo electrónico pertenece al paciente con la cédula pacCi
        if (pacientesServicio.validaCorreoPertenecePaciente(pacCi, pacCorreoElec)) {
            return ResponseEntity.ok("El correo electrónico pertenece al paciente");
        } else {
            return ResponseEntity.ok("El correo electrónico no coincide con los datos registrados del paciente");
        }
    }

    // Método para enviar la clave temporal
    @GetMapping("/enviarClaveTemporal/{pacCi}/{pacCorreoElec}")
    public ResponseEntity<String> enviarClaveTemporal(
            @PathVariable String pacCi,
            @PathVariable String pacCorreoElec) {
        try {
            // Verifica si el correo electrónico pertenece al paciente
            if (!pacientesServicio.validaCorreoPertenecePaciente(pacCi, pacCorreoElec)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El correo electrónico no pertenece al paciente con la cédula proporcionada");
            }
            String nuevaClaveTemporal = generarClaveTemporalAleatoria();
            pacientesServicio.enviarClaveTemporal(pacCi, pacCorreoElec, nuevaClaveTemporal);
            return ResponseEntity.ok("Clave temporal enviada con éxito. \nRevisa tu bandeja de entrada o correo no " +
                    "deseado. \n¡Modifícala inmediatamente una vez ingresado al sistema!.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar la clave temporal. Intenta nuevamente.");
        }
    }
    private String generarClaveTemporalAleatoria() {
        // Genera una nueva clave temporal aleatoria de 12 caracteres
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[12];
        secureRandom.nextBytes(randomBytes);
        String nuevaClaveTemporal = Base64.getEncoder().encodeToString(randomBytes);

        // Retorna la clave temporal sin cifrar
        return nuevaClaveTemporal;
    }

    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> savePaciente(@RequestBody PacienteRequest request) throws ApiUnprocessableEntity {
        this.pacienteValidadorImpl.validator(request);
        this.pacientesServicio.save(request);
        // Envío del correo de bienvenida
        enviarCorreoBienvenida(request.getPacCorreoElec());
        return ResponseEntity.ok(Boolean.TRUE);
    }
    private void enviarCorreoBienvenida(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("¡Bienvenido a Seeker Medical!");
        mensaje.setText("Estimado(a) paciente,\n\n"
                + "¡Bienvenido a Seeker Medical! Gracias por registrarte en nuestra plataforma.\n"
                + "Esperamos que encuentres la atención médica que necesitas. No dudes en contactarnos si tienes alguna pregunta al número 0983894072.\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deletePaciente(@PathVariable String id){
        this.pacientesServicio.deleteByPacCi(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updatePaciente(@RequestBody PacienteRequest request, @PathVariable String id){
        this.pacientesServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar la clave del paciente
    @PutMapping("/{pacCi}/updateClave")
    public ResponseEntity<Object> updateClave(@PathVariable String pacCi, @RequestBody PacienteRequest request) {
        String pacClaveActual = request.getPacClave(); // Clave actual del formulario en Ionic
        String pacNuevaClave = request.getPacNuevaClave(); // Nueva clave del formulario en Ionic
        // Verifica la existencia del paciente
        PacientesDTO paciente = this.pacientesServicio.findByPacCi(pacCi);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El paciente con el CI " + pacCi + " no está registrado");
        }
        // Verifica que la clave actual coincida
        if (!paciente.checkPassword(pacClaveActual)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La clave actual no es válida");
        }
        // Verifica la longitud de la nueva clave
        if (pacNuevaClave.length() < 8) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La nueva clave es muy corta, debe tener mínimo 8 caracteres");
        }
        // Actualiza la clave en la entidad PacientesDTO
        paciente.setPacClave(pacNuevaClave);
        // Actualiza la entidad Pacientes en la base de datos
        this.pacientesServicio.updateClave(paciente);
        // Envia correo al paciente informándole que se ha cambiado la clave
        enviarCorreoCambioClave(paciente.getPacCorreoElec());
        return ResponseEntity.ok("{\"message\": \"Clave actualizada con éxito\"}");
    }

    // Método para enviar correo de cambio de clave
    private void enviarCorreoCambioClave(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Cambio de Clave Exitoso");
        mensaje.setText("Estimado(a) paciente,\n\n"
                + "Le informamos que su clave ha sido actualizada con éxito.\n"
                + "Si usted no realizó este cambio, por favor contáctenos de inmediato al número 0983894072.\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }
}