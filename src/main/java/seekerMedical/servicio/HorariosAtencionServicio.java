package seekerMedical.servicio;

import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.dto.HorariosAtencionDTO;
import java.util.List;

public interface HorariosAtencionServicio {
    List<HorariosAtencionDTO> findAll();

    HorariosAtencionDTO findByHmId(Integer hmId);

    void save(HorariosAtencionRequest horariosAtencion);

    void update(HorariosAtencionRequest request, Integer id);

    void saveAll(List<HorariosAtencionRequest> horariosAtencion);

    void deleteByHmId(Integer hmId);
}