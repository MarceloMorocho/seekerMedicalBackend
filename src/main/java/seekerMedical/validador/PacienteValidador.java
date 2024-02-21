package seekerMedical.validador;

import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;
import seekerMedical.dto.PacienteRequest;

@Service // Permite declarar constantes que van a estar disponibles para todas las clases
public interface PacienteValidador {
    //Throws se utiliza para lanzar explícitamente una excepción
    void validator(PacienteRequest request) throws ApiUnprocessableEntity;
}
