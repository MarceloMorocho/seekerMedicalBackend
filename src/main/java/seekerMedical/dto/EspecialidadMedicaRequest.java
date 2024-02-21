package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class EspecialidadMedicaRequest implements Serializable {
    // @JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("emId")
    private Integer emId;

    @JsonProperty("emDescripcion")
    private String emDescripcion;
}