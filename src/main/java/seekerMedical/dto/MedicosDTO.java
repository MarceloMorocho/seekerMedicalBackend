package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.List;

@Data
public class MedicosDTO implements Serializable {
    private String medCi;
    private String medNombres;
    private String medApellidos;
    private String medCorreoElec;
    private byte[] medFotografia;
    private String medDenominacion;
    private String medDescripcionPro;
    private String medDireccionCalles;
    private String medDireccionCom;
    private String medDireccionRef;
    private String medTelefonoMov;
    private String medTelefonoCon;
    private Double medUbicacionLat;
    private Double medUbicacionLon;
    private Boolean medTagW;
    private Boolean medTagD;
    private Boolean medTagE;
    private Boolean medTagM;
    private Boolean medTag247;
    @JsonIgnore
    private String medClave;
    private String medFotografiaBase64;  // Nuevo campo para la imagen en formato base64
    private List<HorariosAtencionDTO> horariosAtencion; // Lista de horarios de atención
    private List<EspMedMedicosDTO> espMedMedicos; // Lista de especialidad del médico

    public boolean checkPassword(String plainPassword) {
        // Verificar el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.medClave);
    }
}
