package seekerMedical.servicio;

import seekerMedical.dto.PacienteRequest;
import seekerMedical.dto.PacientesDTO;
import java.util.List;

public interface PacientesServicio {

    List<PacientesDTO> findAll();

    PacientesDTO findByPacCorreoElec(String pacCorreoElec);

    PacientesDTO findByPacCi(String pacCi);

    void save(PacienteRequest paciente);

    void saveAll(List<PacienteRequest> pacientes);

    void deleteByPacCi(String pacCI);

    void update(PacienteRequest paciente, String id);

    void updateClave(PacientesDTO paciente);

    boolean validaCorreoPertenecePaciente(String pacCi, String pacCorreoElec);

    void enviarClaveTemporal(String pacCi, String pacCorreoElec, String nuevaClaveTemporal);

    void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo);
}