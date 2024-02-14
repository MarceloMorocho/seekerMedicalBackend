package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.EspMedMedicoRequest;
import seekerMedical.servicio.EspMedMedicosServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;


@Component //Identifica la clase como componente y especifica metadatos
public class EspMedMedicoValidadorImpl implements EspMedMedicoValidador {

    @Autowired //Permite inyectar unas dependencias con otras dentro de Spring Boot
    EspMedMedicosServicioImp espMedMedicosServicioImp;

    @Autowired // Agrega la inyección de Validaciones
    Validaciones validaciones;
    //Override asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    @Override
    public void validator(EspMedMedicoRequest request) throws ApiUnprocessableEntity {
        /*if(request.getDiaDescripcion() == null ){
            this.message("Debe seleccionar mínimo un día");
        }*/
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }
}
