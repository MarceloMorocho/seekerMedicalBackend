package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.*;
import seekerMedical.servicio.FarmaciasServicio;
import seekerMedical.servicio.HorariosAtencionServicio;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.validador.FarmaciaValidadorImpl;
import seekerMedical.validador.HorariosAtencionValidadorImpl;
import seekerMedical.validador.Validaciones;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/farmacias") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class FarmaciasControlador {
    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar
    private FarmaciasServicio farmaciasServicio;

    @Autowired
    private HorariosAtencionValidadorImpl horariosAtencionValidadorImpl;

    @Autowired
    private HorariosAtencionServicio horariosAtencionServicio;

    @Autowired
    private FarmaciaValidadorImpl farmaciaValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    @Autowired
    private JavaMailSender javaMailSender; // Inyecta el servicio de envío de correos

    // Método para validar la existencia de la cédula directamente en el formulario
    @GetMapping("/validaExistenciaFarRuc/{farRuc}")
    public ResponseEntity<String> validaExistenciaFarRuc(@PathVariable String farRuc) {
        if (farmaciaValidadorImpl.validaExistenciaFarRuc(farRuc)) {
            return ResponseEntity.ok("La farmacia ya existe");
        } else {
            return ResponseEntity.ok("La farmacia no existe");
        }
    }

    // Método para validar el ruc de persona natural y jurídica
    @GetMapping("/validaRuc/{farRuc}")
    public ResponseEntity<String> validaRuc(@PathVariable String farRuc) {
        if (validaciones.validaRucNatural(farRuc) || validaciones.validaRucJur(farRuc)) {
            return ResponseEntity.ok("El RUC es válido");
        } else {
            return ResponseEntity.ok("El RUC no es válido");
        }
    }

    // Método para validar la existencia del correo electrónico directamente en el formulario
    @GetMapping("/validaExistenciaFarCorreoElec/{farCorreoElec}")
    public ResponseEntity<String> validaExistenciaFarCorreoElec(@PathVariable String farCorreoElec) {
        if (farmaciaValidadorImpl.validaExistenciaFarCorreoElec(farCorreoElec)) {
            return ResponseEntity.ok("El correo ya existe");
        } else {
            return ResponseEntity.ok("El correo no existe");
        }
    }

    // Método para validar el correo electrónico
    @GetMapping("/validaCorreoElec/{farCorreoElec}")
    public ResponseEntity<String> validaCorreoElec(@PathVariable String farCorreoElec) {
        if (validaciones.validaCorreoElec(farCorreoElec)) {
            return ResponseEntity.ok("El correo es válido");
        } else {
            return ResponseEntity.ok("El correo no es válido");
        }
    }

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produce = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll() {
        List<FarmaciasDTO> farmacias = this.farmaciasServicio.findAll();
        //Imprimir datos de imagen para verificar
        farmacias.forEach(farmacia -> {
            if (farmacia.getFarLogotipo() != null) {
                System.out.println("Datos de la imagen para médico con ID: " + farmacia.getFarRuc() + ": " + farmacia.getFarLogotipo().length + " bytes");
                // Convertir bytes a base64
                String base64Image = Base64.getEncoder().encodeToString(farmacia.getFarLogotipo());
                farmacia.setFarLogotipoBase64(base64Image);
            }
        });
        return ResponseEntity.ok(farmacias);
    }

    // Método para buscar por correo
    @GetMapping (value = "/by/{correo}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByFarCorreoElec(@PathVariable("correo") String correo){
        return ResponseEntity.ok(this.farmaciasServicio.findByFarCorreoElec(correo));
    }


    // Método para validar que el correo electrónico pertenece a la farmacia
    @GetMapping("/validaCorreoPerteneceFarmacia/{farRuc}/{farCorreoElec}")
    public ResponseEntity<String> validaCorreoPerteneceFarmacia(
            @PathVariable String farRuc,
            @PathVariable String farCorreoElec) {
        // Verificar si el correo electrónico pertenece a la farmacia con el ruc farRuc
        if (farmaciasServicio.validaCorreoPerteneceFarmacia(farRuc, farCorreoElec)) {
            return ResponseEntity.ok("El correo electrónico pertenece a la farmacia");
        } else {
            return ResponseEntity.ok("El correo electrónico no coincide con los datos registrados de la farmacia");
        }
    }

    // Método para enviar la calve temporal al correo electrónico registrado por la farmacia
    @GetMapping("/enviarClaveTemporal/{farRuc}/{farCorreoElec}")
    public ResponseEntity<String> enviarClaveTemporal(
            @PathVariable String farRuc,
            @PathVariable String farCorreoElec) {
        try {
            //Verifica si el correo electrónico pertenece a la farmacia
            if (!farmaciasServicio.validaCorreoPerteneceFarmacia(farRuc, farCorreoElec)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El correo electrónico no pertenece a la farmacia con el RUC proporcionado");
            }
            String nuevaClaveTemporal = generarClaveTemporalAleatoria();
            farmaciasServicio.enviarClaveTemporal(farRuc, farCorreoElec, nuevaClaveTemporal);
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
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)//Simplifica el manejo de los métodos
    public ResponseEntity<Object> saveFarmacia(@RequestBody FarmaciaRequest request) throws ApiUnprocessableEntity {
        this.farmaciaValidadorImpl.validator(request);
        this.farmaciasServicio.save(request);
        // Envío del correo de bienvenida
        enviarCorreoBienvenida(request.getFarCorreoElec());
        return ResponseEntity.ok(Boolean.TRUE);
    }
    private void enviarCorreoBienvenida(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("¡Bienvenido a Seeker Medical!");
        mensaje.setText("Estimado(a) administrador(a) de la farmacia,\n\n"
                + "¡Bienvenido a Seeker Medical! Gracias por registrarte en nuestra plataforma.\n"
                + "Esperamos que puedas brindar los servicios médicos de calidad a los pacientes. No dudes en contactarnos si tienes alguna pregunta al número 0983894072.\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteFarmacia(@PathVariable String id){
        this.farmaciasServicio.deleteByFarRuc(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updateMedico(@RequestBody FarmaciaRequest request, @PathVariable String id){
        this.farmaciasServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar la clave del paciente
    @PutMapping("/{farRuc}/updateClave")
    public ResponseEntity<Object> updateClave(@PathVariable String farRuc, @RequestBody FarmaciaRequest request) {
        String farClaveActual = request.getFarClave(); // Clave actual del formulario en Ionic
        String farNuevaClave = request.getFarNuevaClave(); // Nueva clave del formulario en Ionic
        // Verifica la existencia de la farmacia
        FarmaciasDTO farmacia = this.farmaciasServicio.findByFarRuc(farRuc);
        if (farmacia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La farmacia con el RUC " + farRuc + " no está registrada");
        }
        // Verifica que la clave actual coincida
        if (!farmacia.checkPassword(farClaveActual)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La clave actual no es válida");
        }
        // Verifica la longitud de la nueva clave
        if (farNuevaClave.length() < 8) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La nueva clave es muy corta, debe tener mínimo 8 caracteres");
        }
        // Actualiza la clave en la entidad FarmaciasDTO
        farmacia.setFarClave(farNuevaClave);
        // Actualiza la entidad Farmacias en la base de datos
        this.farmaciasServicio.updateClave(farmacia);
        // Envia correo al administador de farmacia informándole que se ha cambiado la clave
        enviarCorreoCambioClave(farmacia.getFarCorreoElec());
        return ResponseEntity.ok("{\"message\": \"Clave actualizada con éxito\"}");
    }

    // Método para enviar correo de cambio de clave
    private void enviarCorreoCambioClave(String correo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Cambio de Clave Exitoso");
        mensaje.setText("Estimado(a) administrador(a) de farmacia,\n\n"
                + "Le informamos que su clave ha sido actualizada con éxito.\n"
                + "Si usted no realizó este cambio, por favor contáctenos de inmediato al número 0983894072.\n\n"
                + "Saludos,\nEl equipo de Seeker Medical");
        // Envía el correo
        javaMailSender.send(mensaje);
    }
}