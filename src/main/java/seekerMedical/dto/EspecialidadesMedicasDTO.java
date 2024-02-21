package seekerMedical.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class EspecialidadesMedicasDTO implements Serializable {
    private Integer emId;
    private String emDescripcion;
    private List<EspMedMedicosDTO> espMedMedicos; // Lista la especialidad del médico
}