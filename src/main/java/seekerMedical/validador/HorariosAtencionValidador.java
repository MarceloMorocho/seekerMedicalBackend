package seekerMedical.validador;

import org.springframework.stereotype.Service;
import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Service // Permite declarar constantes que van a estar disponibles para todas las clases
public interface HorariosAtencionValidador {
    //Throws se utiliza para lanzar explícitamente una excepción
    void validator(HorariosAtencionRequest request) throws ApiUnprocessableEntity;
}