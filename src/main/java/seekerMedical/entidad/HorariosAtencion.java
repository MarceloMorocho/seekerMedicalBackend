package seekerMedical.entidad;

import jakarta.persistence.*;
import lombok.Data;

@Data // Facilita no agregar los set y get
@Entity // Especifica que una clase es una entidad (objeto)
@Table(name = "Horarios_atencion")
public class HorariosAtencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que se genera de forma automática incremental
    @Column(name = "hm_id", unique = true)
    private int hmId;

    @Column(name = "hm1_inicio")
    private String hm1Inicio;

    @Column(name = "hm1_fin")
    private String hm1Fin;

    @Column(name = "hm2_inicio")
    private String hm2Inicio;

    @Column(name = "hm2_fin")
    private String hm2Fin;

    // Especifica las relaciones de muchos a uno entre HorariosAtencion y Medicos, Farmacias, Dias
    @ManyToOne
    @JoinColumn(name = "med_ci")
    private Medicos medCi;

    @ManyToOne
    @JoinColumn(name = "dia_id")
    private Dias diaId;

    @ManyToOne
    @JoinColumn(name = "far_ruc")
    private Farmacias farRuc;
}