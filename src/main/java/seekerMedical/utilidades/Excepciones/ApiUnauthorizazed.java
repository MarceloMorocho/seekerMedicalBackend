package seekerMedical.utilidades.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApiUnauthorizazed extends Exception{
    public ApiUnauthorizazed(String mensaje){
        super(mensaje);
    }
}