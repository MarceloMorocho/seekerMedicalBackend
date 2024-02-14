package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seekerMedical.validador.PacienteValidadorImpl;
import seekerMedical.validador.Validaciones;

@RestController
@RequestMapping (path = "/validaciones")
public class ValidacionesControlador {
    @Autowired
    private Validaciones validaciones;

    @Autowired //Permite inyectar unas dependencias con otras
    private PacienteValidadorImpl pacienteValidadorImpl;
}
