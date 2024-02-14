package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Especialidades_medicas")
public class EspecialidadesMedicas {
    @Id
    @Column(name = "em_id", unique = true)
    private int emId;

    @Column(name = "em_descripcion")
    private String emDescripcion;

    // Especifica la relación uno a muchos entre la tabla Medicos y Especialidades Médicas
    @OneToMany(mappedBy = "emId", cascade = CascadeType.ALL)
    private List<EspMedMedicos> espMedMedicos;
}