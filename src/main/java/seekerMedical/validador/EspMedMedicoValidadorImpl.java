package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.EspMedMedicoRequest;
import seekerMedical.servicio.EspMedMedicosServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Component // Identifica la clase como componente y especifica metadatos
public class EspMedMedicoValidadorImpl implements EspMedMedicoValidador {
    @Autowired // Permite inyectar unas dependencias con otras
    EspMedMedicosServicioImp espMedMedicosServicioImp;

    @Autowired
    Validaciones validaciones;

    @Override // Asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    public void validator(EspMedMedicoRequest request) throws ApiUnprocessableEntity {
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }
}
