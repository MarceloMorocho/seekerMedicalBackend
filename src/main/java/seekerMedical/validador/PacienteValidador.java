package seekerMedical.validador;

import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;
import seekerMedical.dto.PacienteRequest;

@Service /*Permite construir una clase de Servicio para conectar a varios respositorios y agrupar su funcionalidad
Permite declarar constantes que van a estar disponibles para todas las clases*/
public interface PacienteValidador {
    //Throw se utiliza para lanzar explícitamente una excepción
    void validator(PacienteRequest request) throws ApiUnprocessableEntity;
}
