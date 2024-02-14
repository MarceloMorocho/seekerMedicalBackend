package seekerMedical.servicio;

import seekerMedical.dto.EspecialidadMedicaRequest;
import seekerMedical.dto.EspecialidadesMedicasDTO;
import java.util.List;

public interface EspecialidadesMedicasServicio {
    List<EspecialidadesMedicasDTO> findAll();

    EspecialidadesMedicasDTO findByEmDescripcion(String emDescripcion);

    EspecialidadesMedicasDTO findByEmId(Integer emId);

    void save(EspecialidadMedicaRequest especialidadesMedicas);

    void saveAll(List<EspecialidadMedicaRequest> dias);

    void deleteByEmId(Integer emId);

    void update(EspecialidadMedicaRequest especialidadesMedicas, Integer id);
}