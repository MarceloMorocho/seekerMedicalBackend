package seekerMedical.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class DiasDTO implements Serializable {
    private Integer diaId;
    private String diaDescripcion;
    private List<HorariosAtencionDTO> horariosAtencion; // Lista de horarios de atención
}