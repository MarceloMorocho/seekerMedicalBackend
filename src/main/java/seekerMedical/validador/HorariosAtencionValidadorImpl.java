package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.servicio.HorariosAtencionServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;

@Component // Identifica la clase como componente y especifica metadatos
public class HorariosAtencionValidadorImpl implements HorariosAtencionValidador {
    @Autowired // Permite inyectar unas dependencias con otras
    HorariosAtencionServicioImp horariosAtencionServicioImp;

    @Autowired
    Validaciones validaciones;

    @Override // Asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    public void validator(HorariosAtencionRequest request) throws ApiUnprocessableEntity {
       if (!validaciones.validaHorario(request.getHm1Inicio())) {
            this.message("El horario inicio 1 es obligatorio, debe tener 5 caracteres");
        }
        if (!validaciones.validaHorario(request.getHm1Fin())) {
            this.message("El horario fin 1 es obligatorio, debe tener 5 caracteres");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }
}

