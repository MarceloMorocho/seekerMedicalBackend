package seekerMedical.validador;

import org.springframework.stereotype.Service;
import seekerMedical.dto.DiaRequest;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Service /*Permite construir una clase de Servicio para conectar a varios respositorios y agrupar su funcionalidad
Permite declarar constantes que van a estar disponibles para todas las clases*/
public interface DiaValidador {
    //Throw se utiliza para lanzar explícitamente una excepción
    void validator(DiaRequest request) throws ApiUnprocessableEntity;
}
