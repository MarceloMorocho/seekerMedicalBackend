package seekerMedical.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class HorariosAtencionDTO implements Serializable {
    private Integer hmId;
    private String hm1Inicio;
    private String hm1Fin;
    private String hm2Inicio;
    private String hm2Fin;
}
