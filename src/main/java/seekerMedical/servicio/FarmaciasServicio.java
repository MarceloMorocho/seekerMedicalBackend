package seekerMedical.servicio;

import seekerMedical.dto.FarmaciaRequest;
import seekerMedical.dto.FarmaciasDTO;
import java.util.List;

public interface FarmaciasServicio {

    List<FarmaciasDTO> findAll();

    FarmaciasDTO findByFarCorreoElec(String farCorreoElec);

    FarmaciasDTO findByFarRuc(String farRuc);

    void save(FarmaciaRequest farmacia);

    void saveAll(List<FarmaciaRequest> farmacias);

    void deleteByFarRuc(String farRuc);

    void update(FarmaciaRequest farmacia, String id);

    void updateClave(FarmaciasDTO farmacia);

    boolean validaCorreoPerteneceFarmacia(String farRuc, String farCorreoElec);

    void enviarClaveTemporal(String farRuc, String farCorreoElec, String nuevaClaveTemporal);

    void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo);
}