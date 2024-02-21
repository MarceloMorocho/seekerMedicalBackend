package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class DiaRequest implements Serializable {
    // @JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("diaId")
    private Integer diaId;

    @JsonProperty("diaDescripcion")
    private String diaDescripcion;
}