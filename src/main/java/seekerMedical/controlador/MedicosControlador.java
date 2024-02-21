package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.*;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.servicio.MedicosServicio;
import seekerMedical.validador.MedicoValidadorImpl;
import seekerMedical.validador.Validaciones;
import java.security.SecureRandom;
import java.util.*;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/medicos") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class MedicosControlador {
    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar con los servicios
    private MedicosServicio medicosServicio;

    @Autowired
    private MedicoValidadorImpl medicoValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produce = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll() {
        List<MedicosDTO> medicos = this.medicosServicio.findAll();
        // Imprime datos de imagen para verificar
        medicos.forEach(medico -> {
            if (medico.getMedFotografia() != null) {
                System.out.println("Datos de la imagen para médico con ID: " + medico.getMedCi() + ": " + medico.getMedFotografia().length + " bytes");
                // Convierte bytes a base64
                String base64Image = Base64.getEncoder().encodeToString(medico.getMedFotografia());
                medico.setMedFotografiaBase64(base64Image);
            }
        });
        return ResponseEntity.ok(medicos);
    }

    // Método para validar la existencia de la cédula
    @GetMapping("/validaExistenciaMedCi/{medCi}")
    public ResponseEntity<String> validaExistenciaMedCi(@PathVariable String medCi) {
        if (medicoValidadorImpl.validaExistenciaMedCi(medCi)) {
            return ResponseEntity.ok("El médico ya existe");
        } else {
            return ResponseEntity.ok("El médico no existe");
        }
    }

    // Método para validar la cédula ecuatoriana
    @GetMapping("/validaCedulaEcuatoriana/{medCi}")
    public ResponseEntity<String> validaCedulaEcuatoriana(@PathVariable String medCi) {
        if (validaciones.validaCedulaEcuatoriana(medCi)) {
            return ResponseEntity.ok("La cédula es válida");
        } else {
            return ResponseEntity.ok("La cédula no es válida");
        }
    }

    // Método para validar la existencia del correo electrónico
    @GetMapping("/validaExistenciaMedCorreoElec/{medCorreoElec}")
    public ResponseEntity<String> validaExistenciaMedCorreoElec(@PathVariable String medCorreoElec) {
        if (medicoValidadorImpl.validaExistenciaMedCorreoElec(medCorreoElec)) {
            return ResponseEntity.ok("El correo ya existe");
        } else {
            return ResponseEntity.ok("El correo no existe");
        }
    }

    // Método para validar el correo electrónico
    @GetMapping("/validaCorreoElec/{medCorreoElec}")
    public ResponseEntity<String> validaCorreoElec(@PathVariable String medCorreoElec) {
        if (validaciones.validaCorreoElec(medCorreoElec)) {
            return ResponseEntity.ok("El correo es válido");
        } else {
            return ResponseEntity.ok("El correo no es válido");
        }
    }

    // Método para buscar por medCi
    @GetMapping(value = "/medCi/{medCi}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findByMedCi(@PathVariable("medCi") String medCi) {
        // Obtener información del médico por su ID
        MedicosDTO medico = this.medicosServicio.findByMedCi(medCi);
        // Verificar si se encontró el paciente
        if (medico != null) {
            // Verificar si la fotografía del médico no es nula
            if (medico.getMedFotografia() != null) {
                // Convertir bytes a base64
                String base64Image = Base64.getEncoder().encodeToString(medico.getMedFotografia());
                medico.setMedFotografiaBase64(base64Image);
            }
            // Log para imprimir la respuesta antes de devolverla
            System.out.println("Respuesta del servicio: " + medico.toString());
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para buscar por el correo electrónico
    @GetMapping (value = "/by/{correo}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByMedCorreoElec(@PathVariable("correo") String correo){
        return ResponseEntity.ok(this.medicosServicio.findByMedCorreoElec(correo));
    }

    // Método para validar que el correo electrónico pertenece al médico
    @GetMapping("/validaCorreoPerteneceMedico/{medCi}/{medCorreoElec}")
    public ResponseEntity<String> validaCorreoPerteneceMedico(
            @PathVariable String medCi,
            @PathVariable String medCorreoElec) {
        // Verifica si el correo electrónico pertenece al médico con la cédula medCi
        if (medicosServicio.validaCorreoPerteneceMedico(medCi, medCorreoElec)) {
            return ResponseEntity.ok("El correo electrónico pertenece al médico");
        } else {
            return ResponseEntity.ok("El correo electrónico no coincide con los datos registrados del médico");
        }
    }

    // Método para enviar correo electrónico con clave temporal
    @GetMapping("/enviarClaveTemporal/{medCi}/{medCorreoElec}")
    public ResponseEntity<String> enviarClaveTemporal(
            @PathVariable String medCi,
            @PathVariable String medCorreoElec) {
        try { // Verifica si el correo electrónico pertenece al médico
            if (!medicosServicio.validaCorreoPerteneceMedico(medCi, medCorreoElec)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El correo electrónico no pertenece al médico con la cédula proporcionada");
            }
            String nuevaClaveTemporal = generarClaveTemporalAleatoria();
            medicosServicio.enviarClaveTemporal(medCi, medCorreoElec, nuevaClaveTemporal);
            return ResponseEntity.ok("Clave temporal enviada con éxito. \nRevisa tu bandeja de entrada o correo no " +
                    "deseado. \n¡Modifícala inmediatamente una vez ingresado al sistema!.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar la clave temporal. Intenta nuevamente.");
        }
    }
    private String generarClaveTemporalAleatoria() {
        SecureRandom secureRandom = new SecureRandom(); // Genera una nueva clave temporal aleatoria de 12 caracteres
        byte[] randomBytes = new byte[12];
        secureRandom.nextBytes(randomBytes);
        String nuevaClaveTemporal = Base64.getEncoder().encodeToString(randomBytes);
        return nuevaClaveTemporal; // Retorna la clave temporal sin cifrar
    }


    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)//Simplifica el manejo de los métodos
    public ResponseEntity<Object> saveMedico(@RequestBody MedicoRequest request) throws ApiUnprocessableEntity {
        this.medicoValidadorImpl.validator(request);
        this.medicosServicio.save(request);
        // Envío del correo de bienvenida
        enviarCorreoBienvenida(request.getMedCorreoElec());
        return ResponseEntity.ok(Boolean.TRUE);
    }
    private void enviarCorreoBienvenida(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("¡Bienvenido a Seeker Medical!");
        mensaje.setText("Estimado médico,\n\n"
                + "¡Bienvenido a Seeker Medical! Gracias por registrarte en nuestra plataforma.\n"
                + "Esperamos que puedas brindar la atención médica de calidad a los pacientes. No dudes en contactarnos si tienes alguna pregunta al número 0983894072..\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteMedico(@PathVariable String id){
        this.medicosServicio.deleteByMedCi(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{medCi}/update")
    public ResponseEntity<Object> updateMedico(@RequestBody MedicoRequest request, @PathVariable String medCi){
        this.medicosServicio.update(request,medCi);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar la clave del médico
    @PutMapping("/{medCi}/updateClave")
    public ResponseEntity<Object> updateClave(@PathVariable String medCi, @RequestBody MedicoRequest request) {
        String medClaveActual = request.getMedClave(); // Clave actual del formulario en Ionic
        String medNuevaClave = request.getMedNuevaClave(); // Nueva clave del formulario en Ionic
        // Verifica la existencia del médico
        MedicosDTO medico = this.medicosServicio.findByMedCi(medCi);
        if (medico == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El médico con el CI " + medCi + " no está registrado");
        }
        // Verifica que la clave actual coincida
        if (!medico.checkPassword(medClaveActual)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La clave actual no es válida");
        }
        // Verifica la longitud de la nueva clave
        if (medNuevaClave.length() < 8) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La nueva clave es muy corta, debe tener mínimo 8 caracteres");
        }
        // Actualiza la clave en la entidad MedicosDTO
        medico.setMedClave(medNuevaClave);
        // Actualiza la entidad Medicos en la base de datos
        this.medicosServicio.updateClave(medico);
        // Envia el correo al médico informándole que se ha cambiado la clave
        enviarCorreoCambioClave(medico.getMedCorreoElec());
        return ResponseEntity.ok("{\"message\": \"Clave actualizada con éxito\"}");
    }

    // Método para enviar correo de cambio de clave
    private void enviarCorreoCambioClave(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Cambio de Clave Exitoso");
        mensaje.setText("Estimado(a) médico,\n\n"
                + "Le informamos que su clave ha sido actualizada con éxito.\n"
                + "Si usted no realizó este cambio, por favor contáctenos de inmediato al número 0983894072.\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }
}