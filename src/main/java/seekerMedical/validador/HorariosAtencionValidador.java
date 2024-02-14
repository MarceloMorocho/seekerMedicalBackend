package seekerMedical.validador;

import org.springframework.stereotype.Service;
import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Service /*Permite construir una clase de Servicio para conectar a varios respositorios y agrupar su funcionalidad
Permite declarar constantes que van a estar disponibles para todas las clases*/
public interface HorariosAtencionValidador {
    //Throw se utiliza para lanzar explícitamente una excepción
    void validator(HorariosAtencionRequest request) throws ApiUnprocessableEntity;
}
