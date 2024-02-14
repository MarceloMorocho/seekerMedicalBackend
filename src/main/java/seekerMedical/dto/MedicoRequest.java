package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class MedicoRequest implements Serializable {
    //@JsonProperty ayuda a definir una propiedad lógica para un campo que se usará tanto para serializar como deserializar
    @JsonProperty("medCi")
    private String medCi;

    @JsonProperty("medNombres")
    private String medNombres;

    @JsonProperty("medApellidos")
    private String medApellidos;

    @JsonProperty("medCorreoElec")
    private String medCorreoElec;

    @JsonProperty("medFotografia")
    private byte [] medFotografia;

    @JsonProperty("medDenominacion")
    private String medDenominacion;

    @JsonProperty("medDescripcionPro")
    private String medDescripcionPro;

    @JsonProperty("medDireccionCalles")
    private String medDireccionCalles;

    @JsonProperty("medDireccionCom")
    private String medDireccionCom;

    @JsonProperty("medDireccionRef")
    private String medDireccionRef;

    @JsonProperty("medTelefonoMov")
    private String medTelefonoMov;

    @JsonProperty("medTelefonoCon")
    private String medTelefonoCon;

    @JsonProperty("medUbicacionLat")
    private Double medUbicacionLat;

    @JsonProperty("medUbicacionLon")
    private Double medUbicacionLon;

    @JsonProperty("medTagW")
    private Boolean medTagW;

    @JsonProperty("medTagD")
    private Boolean medTagD;

    @JsonProperty("medTagE")
    private Boolean medTagE;

    @JsonProperty("medTagM")
    private Boolean medTagM;

    @JsonProperty("medTag247")
    private Boolean medTag247;

    @JsonProperty("medClave")
    private String medClave;

    @JsonProperty("medNuevaClave") // Campo para el cambio de clave
    private String medNuevaClave;
}