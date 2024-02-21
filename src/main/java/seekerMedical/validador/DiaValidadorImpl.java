package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.DiaRequest;
import seekerMedical.servicio.DiasServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Component // Identifica la clase como componente y especifica metadatos
public class DiaValidadorImpl implements DiaValidador {
    @Autowired // Permite inyectar unas dependencias con otras
    DiasServicioImp diasServicioImp;

    @Autowired
    Validaciones validaciones;

    @Override // Asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    public void validator(DiaRequest request) throws ApiUnprocessableEntity {
        if(request.getDiaDescripcion() == null ){
            this.message("Debe seleccionar mínimo un día");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }
}