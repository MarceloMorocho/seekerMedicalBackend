package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class EspMedMedicoRequest implements Serializable {
    //@JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("emmId")
    private Integer emmId;

    @JsonProperty("emId")
    private Integer emId;

    @JsonProperty("medCi")
    private String medCi;
}