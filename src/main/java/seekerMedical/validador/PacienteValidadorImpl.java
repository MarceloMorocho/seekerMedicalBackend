package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.PacienteRequest;
import seekerMedical.dto.PacientesDTO;
import seekerMedical.servicio.PacientesServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import java.util.Optional;
@Component //Identifica la clase como componente y especifica metadatos
public class PacienteValidadorImpl implements PacienteValidador {

    @Autowired //Permite inyectar unas dependencias con otras dentro de Spring Boot
    PacientesServicioImp pacientesServicioImp;

    @Autowired // Agrega la inyección de Validaciones
    Validaciones validaciones;

    //Override asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    @Override
    public void validator(PacienteRequest request) throws ApiUnprocessableEntity {
        if (validaExistenciaPacCi(request.getPacCi())) {
            this.message("El usuario ya existe");
        }
        if (!validaciones.validaCedulaEcuatoriana(request.getPacCi())) {
            this.message("La cédula no es válida");
        }

        if (!validaciones.validaNomApe(request.getPacNombres())) {
            this.message("El nombre es obligatorio, debe tener mínimo 3 caracteres");
        }

        if (!validaciones.validaNomApe(request.getPacApellidos())) {
            this.message("El apellido es obligatorio, debe tener mínimo 3 caracteres");
        }

        if (validaExistenciaPacCorreoElec(request.getPacCorreoElec())) {
            this.message("El correo ya existe");
        }

        if (request.getPacCorreoElec() == null || request.getPacCorreoElec().isEmpty()) {
            this.message("El correo electrónico es obligatorio");
        }
        if (!validaciones.validaCorreoElec(request.getPacCorreoElec())) {
            this.message("El correo electrónico no tiene un formato válido");
        }

        if (!validaciones.validaTelefonoMov(request.getPacTelefonoMov())) {
            this.message("El número del móvil es obligatorio, debe tener 10 dígitos");
        }

        if (request.getPacClave().length() < 8) {
            this.message("La clave es muy corta, debe tener mínimo 8 caracteres");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity {
        throw new ApiUnprocessableEntity(message);
    }

    //Validación de existencia del paciente registrado previamente
    public boolean validaExistenciaPacCi(String id) {
        Optional<PacientesDTO> pac = Optional.ofNullable(pacientesServicioImp.findByPacCi(id));
        return pac.isPresent();
    }

    //Validación de existencia del correo del paciente registrado previamente
    public boolean validaExistenciaPacCorreoElec(String pacCorreoElec) {
        Optional<PacientesDTO> pac = Optional.ofNullable(pacientesServicioImp.findByPacCorreoElec(pacCorreoElec));
        return pac.isPresent();
    }
}