package seekerMedical.servicio;

import seekerMedical.dto.DiaRequest;
import seekerMedical.dto.DiasDTO;
import java.util.List;

public interface DiasServicio {
    List<DiasDTO> findAll();

    DiasDTO findByDiaId(Integer diaId);

    void save(DiaRequest dias);

    void saveAll(List<DiaRequest> dias);

    void deleteByDiaId(Integer diaId);

    void update(DiaRequest dias, Integer id);
}