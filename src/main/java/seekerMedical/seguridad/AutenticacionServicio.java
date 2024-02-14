package seekerMedical.seguridad;

public interface AutenticacionServicio {
    boolean autenticacionPaciente(String pacCi, String pacClave);

    boolean autenticacionMedico(String medCi, String medClave);

    boolean autenticacionFarmacia(String farRuc, String farClave);

}

