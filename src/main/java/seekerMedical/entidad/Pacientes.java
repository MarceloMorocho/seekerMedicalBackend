package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Pacientes")
public class Pacientes {
    @Id
    @Column(name = "pac_ci", unique = true)
    private String pacCi;

    @Column(name = "pac_nombres")
    private String pacNombres;

    @Column(name = "pac_apellidos")
    private String pacApellidos;

    @Column(name = "pac_correo_elec")
    private String pacCorreoElec;

    @Lob // Especifica que una propiedad o campo debe conservarse como objeto grande
    @Column(name = "pac_fotografia")
    private byte[] pacFotografia;

    @Column(name = "pac_telefono_mov")
    private String pacTelefonoMov;

    @Column(name = "pac_clave")
    private String pacClave;

    // Método para establecer la contraseña encriptada
    public void setPacClave(String pacClave) {
        // Generar un hash BCrypt de la contraseña proporcionada
        String hashedPassword = BCrypt.hashpw(pacClave, BCrypt.gensalt());
        this.pacClave = hashedPassword;
    }

    // Método para verificar si la contraseña en texto plano coincide con la almacenada
    public boolean checkPassword(String plainPassword) {
        // Verificar el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.pacClave);
    }
}