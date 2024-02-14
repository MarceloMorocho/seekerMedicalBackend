package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class FarmaciaRequest implements Serializable {
    //@JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("farRuc")
    private String farRuc;

    @JsonProperty("farNombreNegocio")
    private String farNombreNegocio;

    @JsonProperty("farCorreoElec")
    private String farCorreoElec;

    @JsonProperty("farLogotipo")
    private byte [] farLogotipo;

    @JsonProperty("farDireccionCalles")
    private String farDireccionCalles;

    @JsonProperty("farDireccionCom")
    private String farDireccionCom;

    @JsonProperty("farDireccionRef")
    private String farDireccionRef;

    @JsonProperty("farTelefonoMov")
    private String farTelefonoMov;

    @JsonProperty("farTelefonoCon")
    private String farTelefonoCon;

    @JsonProperty("farUbicacionLat")
    private Double farUbicacionLat;

    @JsonProperty("farUbicacionLon")
    private Double farUbicacionLon;

    @JsonProperty("farTagW")
    private Boolean farTagW;

    @JsonProperty("farTagD")
    private Boolean farTagD;

    @JsonProperty("farTag247")
    private Boolean farTag247;

    @JsonProperty("farClave")
    private String farClave;

    @JsonProperty("farNuevaClave") // Campo para el cambio de clave
    private String farNuevaClave;
}