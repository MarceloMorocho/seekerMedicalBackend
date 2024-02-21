package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Medicos")
public class Medicos {
    @Id
    @Column(name = "med_ci", unique = true)
    private String medCi;

    @Column(name = "med_nombres")
    private String medNombres;

    @Column(name = "med_apellidos")
    private String medApellidos;

    @Column(name = "med_correo_elec")
    private String medCorreoElec;

    @Lob // Especifica que una propiedad o campo debe conservarse como objeto grande
    @Column(name = "med_fotografia")
    private byte[] medFotografia;

    @Column(name = "med_denominacion")
    private String medDenominacion;

    @Column(name = "med_descripcion_pro")
    private String medDescripcionPro;

    @Column(name = "med_direccion_calles")
    private String medDireccionCalles;

    @Column(name = "med_direccion_com")
    private String medDireccionCom;

    @Column(name = "med_direccion_ref")
    private String medDireccionRef;

    @Column(name = "med_telefono_mov")
    private String medTelefonoMov;

    @Column(name = "med_telefono_con")
    private String medTelefonoCon;

    @Column(name = "med_ubicacion_lat")
    private Double medUbicacionLat;

    @Column(name = "med_ubicacion_lon")
    private Double medUbicacionLon;

    @Column(name = "med_tag_w")
    private Boolean medTagW;

    @Column(name = "med_tag_d")
    private Boolean medTagD;

    @Column(name = "med_tag_e")
    private Boolean medTagE;

    @Column(name = "med_tag_m")
    private Boolean medTagM;

    @Column(name = "med_tag_247")
    private Boolean medTag247;

    @Column(name = "med_clave")
    private String medClave;

    // Método para establecer la contraseña encriptada
    public void setMedClave(String medClave) {
        // Genera un hash BCrypt de la contraseña proporcionada
        String hashedPassword = BCrypt.hashpw(medClave, BCrypt.gensalt());
        this.medClave = hashedPassword;
    }

    // Método para verificar si la contraseña en texto plano coincide con la almacenada
    public boolean checkPassword(String plainPassword) {
        // Verifica el hash BCrypt de la contraseña en texto plano con el hash almacenado
        return BCrypt.checkpw(plainPassword, this.medClave);
    }

    // Especifica la relación uno a muchos entre la tabla Medicos y HorariosAtencion
    @OneToMany(mappedBy = "medCi", cascade = CascadeType.ALL)
    private List<HorariosAtencion> horariosAtencion;

    // Especifica la relación uno a muchos entre la tabla Medicos y EspMedMedicos
    @OneToMany(mappedBy = "medCi", cascade = CascadeType.ALL)
    private List<EspMedMedicos> espMedMedicos;
}