package seekerMedical.servicio;

import seekerMedical.dto.MedicoRequest;
import seekerMedical.dto.MedicosDTO;
import seekerMedical.entidad.EspecialidadesMedicas;
import java.util.List;

public interface MedicosServicio {

    List<MedicosDTO> findAll();

    MedicosDTO findByMedCorreoElec(String medCorreoElec);

    MedicosDTO findByMedCi(String medCi);

    void save(MedicoRequest medico);

    void saveAll(List<MedicoRequest> medicos);

    void deleteByMedCi(String medCi);

    void update(MedicoRequest medico, String id);

    void updateClave(MedicosDTO medico);

    EspecialidadesMedicas findEspecialidadByEmmId(int emmId);

    boolean validaCorreoPerteneceMedico(String medCi, String medCorreoElec);

    void enviarClaveTemporal(String medCi, String medCorreoElec, String nuevaClaveTemporal);

    void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo);
}