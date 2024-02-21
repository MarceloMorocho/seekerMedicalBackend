package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.EspecialidadMedicaRequest;
import seekerMedical.servicio.EspecialidadesMedicasServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Component //Identifica la clase como componente y especifica metadatos
public class EspecialidadMedicaValidadorImpl implements EspecialidadMedicaValidador {
    @Autowired // Permite inyectar unas dependencias con otras
    EspecialidadesMedicasServicioImp especialidadesMedicasServicioImp;

    @Autowired // Agrega la inyección de Validaciones
    Validaciones validaciones;

    @Override // Asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    public void validator(EspecialidadMedicaRequest request) throws ApiUnprocessableEntity {
        if(request.getEmDescripcion() == null ){
            this.message("Debe seleccionar mínimo una especialidad");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }
}