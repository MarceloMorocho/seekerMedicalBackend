package seekerMedical.seguridad;

import lombok.Data;

@Data
public class AutenticacionRequest {
    private String username;
    private String password;
}
