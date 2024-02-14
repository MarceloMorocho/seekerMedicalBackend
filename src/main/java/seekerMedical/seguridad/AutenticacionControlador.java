package seekerMedical.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.FarmaciasDTO;
import seekerMedical.dto.MedicosDTO;
import seekerMedical.dto.PacientesDTO;
import seekerMedical.servicio.FarmaciasServicio;
import seekerMedical.servicio.MedicosServicio;
import seekerMedical.servicio.PacientesServicio;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping(path = "/login")
public class AutenticacionControlador {
    @Autowired
    private AutenticacionServicio autenticacionServicio;

    @Autowired
    private PacientesServicio pacientesServicio;

    @Autowired
    private MedicosServicio medicosServicio;

    @Autowired
    private FarmaciasServicio farmaciasServicio;

    @PostMapping("/paciente")
    public ResponseEntity<Object> autenticacionPaciente(@RequestBody AutenticacionRequest request) {
        boolean authenticated = autenticacionServicio.autenticacionPaciente(request.getUsername(), request.getPassword());
        if (authenticated) {
            // Obtiene el pacCi del servicio utilizando el método findByPacCi
            PacientesDTO paciente = pacientesServicio.findByPacCi(request.getUsername());
            // Asegura de que paciente no sea nulo antes de intentar obtener el pacCi
            if (paciente != null) {
                // Transforma la fotografía a base64 si no es nula
                if (paciente.getPacFotografia() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(paciente.getPacFotografia());
                    paciente.setPacFotografiaBase64(base64Image);
                }
                // Devuelve un mapa con la información deseada
                return ResponseEntity.ok(Map.of("message", "Inicio de sesión exitoso para el paciente", "pacCi", paciente.getPacCi(), "paciente", paciente));
            } else {
                // Manejo si el paciente no se encuentra
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener información del paciente");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválido para el paciente");
        }
    }

    @PostMapping("/medico")
    public ResponseEntity<Object> autenticacionMedico(@RequestBody AutenticacionRequest request) {
        boolean authenticated = autenticacionServicio.autenticacionMedico(request.getUsername(), request.getPassword());
        if (authenticated) {
            // Obtiene el MedCi del servicio utilizando el método findByMedCi
            MedicosDTO medico = medicosServicio.findByMedCi(request.getUsername());
            // Asegura de que médico no sea nulo antes de intentar obtener el medCi
            if (medico != null) {
                // Transforma la fotografía a base64 si no es nula
                if (medico.getMedFotografia() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(medico.getMedFotografia());
                    medico.setMedFotografiaBase64(base64Image);
                }
                // Devuelve un mapa con la información deseada
                return ResponseEntity.ok(Map.of("message", "Inicio de sesión exitoso para el médico", "medCi", medico.getMedCi(), "medico", medico));
            } else {
                // Manejo si el médico no se encuentra
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener información del médico");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválido para el médico");
        }
    }

    @PostMapping("/farmacia")
    public ResponseEntity<Object> autenticacionFarmacia(@RequestBody AutenticacionRequest request) {
        boolean authenticated = autenticacionServicio.autenticacionFarmacia(request.getUsername(), request.getPassword());
        if (authenticated) {
            // Obtiene el farRuc del servicio utilizando el método findByFarRuc
            FarmaciasDTO farmacia = farmaciasServicio.findByFarRuc(request.getUsername());
            // Asegura de que farmacia no sea nulo antes de intentar obtener el farRuc
            if (farmacia != null) {
                // Transforma el logotipo a base64 si no es nula
                if (farmacia.getFarLogotipo() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(farmacia.getFarLogotipo());
                    farmacia.setFarLogotipoBase64(base64Image);
                }
                // Devuelve un mapa con la información deseada
                return ResponseEntity.ok(Map.of("message", "Inicio de sesión exitoso para la farmacia", "farRuc", farmacia.getFarRuc(), "farmacia", farmacia));
            } else {
                // Manejo si la farmacia no se encuentra
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener información de la farmacia");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña inválido para la farmacia");
        }
    }
}