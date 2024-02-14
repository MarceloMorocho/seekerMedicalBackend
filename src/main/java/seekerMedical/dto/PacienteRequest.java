package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class PacienteRequest implements Serializable {
    //@JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("pacCi")
    private String pacCi;

    @JsonProperty("pacNombres")
    private String pacNombres;

    @JsonProperty("pacApellidos")
    private String pacApellidos;

    @JsonProperty("pacCorreoElec")
    private String pacCorreoElec;

    @JsonProperty("pacFotografia")
    private byte [] pacFotografia;

    @JsonProperty("pacTelefonoMov")
    private String pacTelefonoMov;

    @JsonProperty("pacClave")
    private String pacClave;

    @JsonProperty("pacNuevaClave") // Campo para el cambio de clave
    private String pacNuevaClave;
}