package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Farmacias")
public class Farmacias {
    @Id
    @Column(name = "far_ruc", unique = true)
    private String farRuc;

    @Column(name = "far_nombre_negocio")
    private String farNombreNegocio;

    @Column(name = "far_correo_elec")
    private String farCorreoElec;

    @Lob // Especifica que una propiedad o campo debe conservarse como objeto grande
    @Column(name = "far_logotipo")
    private byte[] farLogotipo;

    @Column(name = "far_direccion_calles")
    private String farDireccionCalles;

    @Column(name = "far_direccion_com")
    private String farDireccionCom;

    @Column(name = "far_direccion_ref")
    private String farDireccionRef;

    @Column(name = "far_telefono_mov")
    private String farTelefonoMov;

    @Column(name = "far_telefono_con")
    private String farTelefonoCon;

    @Column(name = "far_ubicacion_lat")
    private Double farUbicacionLat;

    @Column(name = "far_ubicacion_lon")
    private Double farUbicacionLon;

    @Column(name = "far_tag_w")
    private Boolean farTagW;

    @Column(name = "far_tag_d")
    private Boolean farTagD;

    @Column(name = "far_tag_247")
    private Boolean farTag247;

    @Column(name = "far_clave")
    private String farClave;

    // Método para establecer la contraseña encriptada
    public void setFarClave(String farClave) {
        // Genera un hash BCrypt de la contraseña proporcionada
        String hashedPassword = BCrypt.hashpw(farClave, BCrypt.gensalt());
        this.farClave = hashedPassword;
    }

    // Método para verificar si la contraseña en texto plano coincide con la almacenada
    public boolean checkPassword(String plainPassword) {
        // Verifica el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.farClave);
    }

    // Especifica la relación uno a muchos entre la tabla Farmacias y HorariosAtencion
    @OneToMany(mappedBy = "farRuc", cascade = CascadeType.ALL)
    private List<HorariosAtencion> horariosAtencion;
}
