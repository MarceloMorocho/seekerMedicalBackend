package seekerMedical.servicio;

import seekerMedical.dto.EspMedMedicoRequest;
import seekerMedical.dto.EspMedMedicosDTO;
import java.util.List;

public interface EspMedMedicosServicio {
    List<EspMedMedicosDTO> findAll();

    EspMedMedicosDTO findByEmmId(Integer emmId);

    void save(EspMedMedicoRequest EspMedMedicos);

    void saveAll(List<EspMedMedicoRequest> EspMedMedicos);

    void deleteByEmmId(Integer emmId);

    void update(EspMedMedicoRequest EspMedMedicos, Integer id);
}