package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;
import java.util.List;

@Data
public class FarmaciasDTO implements Serializable {
    private String farRuc;
    private String farNombreNegocio;
    private String farCorreoElec;
    private byte[] farLogotipo;
    private String farDireccionCalles;
    private String farDireccionCom;
    private String farDireccionRef;
    private String farTelefonoMov;
    private String farTelefonoCon;
    private Double farUbicacionLat;
    private Double farUbicacionLon;
    private Boolean farTagW;
    private Boolean farTagD;
    private Boolean farTag247;
    private String farLogotipoBase64;  // Nuevo campo para el logotipo en formato base64
    @JsonIgnore
    private String farClave;
    private List<HorariosAtencionDTO> horariosAtencion; // Lista de horarios de atención

    public boolean checkPassword(String plainPassword) {
        // Verificar el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.farClave);
    }
}
