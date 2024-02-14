package seekerMedical.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Serializable;

@Data
public class PacientesDTO implements Serializable {
    private String pacCi;
    private String pacNombres;
    private String pacApellidos;
    private String pacCorreoElec;
    private byte[] pacFotografia;
    private String pacTelefonoMov;
    @JsonIgnore
    private String pacClave;
    private String pacFotografiaBase64;  // Nuevo campo para la imagen en formato base64

    public boolean checkPassword(String plainPassword) {
        // Verificar el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.pacClave);
    }
}
