package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Dias")
public class Dias {
    @Id
    @Column(name = "dia_id", unique = true)
    private int diaId;

    @Column(name = "dia_descripcion")
    private String diaDescripcion;

    // Establece la relación de uno a muchos entre Dias y HorariosAtencion
    @OneToMany(mappedBy = "diaId", cascade = CascadeType.ALL)
    private List<HorariosAtencion> horariosAtencion;
}