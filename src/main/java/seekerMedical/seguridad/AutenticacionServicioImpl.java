package seekerMedical.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import seekerMedical.dto.FarmaciasDTO;
import seekerMedical.dto.MedicosDTO;
import seekerMedical.dto.PacientesDTO;
import seekerMedical.servicio.FarmaciasServicio;
import seekerMedical.servicio.MedicosServicio;
import seekerMedical.servicio.PacientesServicio;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicioImpl implements AutenticacionServicio {
    @Autowired
    private PacientesServicio pacientesServicio;

    @Autowired
    private MedicosServicio medicosServicio;

    @Autowired
    private FarmaciasServicio farmaciasServicio;

    @Override
    public boolean autenticacionPaciente(String pacCi, String pacClave) {
        PacientesDTO paciente = pacientesServicio.findByPacCi(pacCi);
        return paciente != null && paciente.checkPassword(pacClave);
    }

    @Override
    public boolean autenticacionMedico(String medCi, String medClave) {
        MedicosDTO medico = medicosServicio.findByMedCi(medCi);
        return medico != null && medico.checkPassword(medClave);
    }

    @Override
    public boolean autenticacionFarmacia(String farRuc, String farClave) {
        FarmaciasDTO farmacia = farmaciasServicio.findByFarRuc(farRuc);
        return farmacia != null && farmacia.checkPassword(farClave);
    }
}