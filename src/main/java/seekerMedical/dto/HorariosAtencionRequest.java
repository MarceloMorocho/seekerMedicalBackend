package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class HorariosAtencionRequest implements Serializable {
    //@JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("hmId")
    private Integer hmId;

    @JsonProperty("hm1Inicio")
    private String hm1Inicio;

    @JsonProperty("hm1Fin")
    private String hm1Fin;

    @JsonProperty("hm2Inicio")
    private String hm2Inicio;

    @JsonProperty("hm2Fin")
    private String hm2Fin;

    @JsonProperty("medCi")
    private String medCi;

    @JsonProperty("farRuc")
    private String farRuc;

    @JsonProperty("diaId")
    private Integer diaId;
}