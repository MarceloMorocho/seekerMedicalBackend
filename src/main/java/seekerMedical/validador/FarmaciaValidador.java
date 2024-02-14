package seekerMedical.validador;

import org.springframework.stereotype.Service;
import seekerMedical.dto.FarmaciaRequest;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Service /*Permite construir una clase de Servicio para conectar a varios respositorios y agrupar su funcionalidad
Permite declarar constantes que van a estar disponibles para todas las clases*/
public interface FarmaciaValidador {
    //Throw se utiliza para lanzar explícitamente una excepción
    void validator(FarmaciaRequest request) throws ApiUnprocessableEntity;
}
