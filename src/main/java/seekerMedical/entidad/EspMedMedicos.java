package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Esp_med_medico")
public class EspMedMedicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que se genera de forma automática incremental
    @Column(name = "emm_id", unique = true)
    private int emmId;

    @ManyToOne
    @JoinColumn(name = "med_ci")
    private Medicos medCi;

    @ManyToOne
    @JoinColumn(name = "em_id") // Esta columna hace referencia al id de Especialidades Médicas
    private EspecialidadesMedicas emId;
}