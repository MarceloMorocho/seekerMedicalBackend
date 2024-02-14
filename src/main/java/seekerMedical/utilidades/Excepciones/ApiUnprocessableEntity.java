package seekerMedical.utilidades.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) /* Indica que el servidor ha entendido la solicitud
del cliente, pero no puede procesarla debido a un problema con la entidad que envía la solicitud*/
public class ApiUnprocessableEntity extends Exception{
    public ApiUnprocessableEntity(String message){
        super(message);
    }
}