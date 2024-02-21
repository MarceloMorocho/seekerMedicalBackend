package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.EspMedMedicoRequest;
import seekerMedical.servicio.EspMedMedicosServicio;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.validador.EspMedMedicoValidadorImpl;
import seekerMedical.validador.Validaciones;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/espMedMedicos") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class EspMedMedicosControlador {
    @Autowired //Utilizado para retornar la información que se necesita traer y enlazar
    private EspMedMedicosServicio espMedMedicosServicio;

    @Autowired
    private EspMedMedicoValidadorImpl espMedMedicoValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produce = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE )
    //Se retorna la lista colocada en diasServicio
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(this.espMedMedicosServicio.findAll());
    }

    @GetMapping (value = "/emmId/{emmId}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByEmmId(@PathVariable("emmId") Integer emmId){
        return ResponseEntity.ok(this.espMedMedicosServicio.findByEmmId(emmId));
    }

    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveEspMedMedico(@RequestBody EspMedMedicoRequest request) throws ApiUnprocessableEntity {
        this.espMedMedicoValidadorImpl.validator(request);
        this.espMedMedicosServicio.save(request);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteEspMedMedicos(@PathVariable Integer id){
        this.espMedMedicosServicio.deleteByEmmId(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updateEspMedMedico(@RequestBody EspMedMedicoRequest request, @PathVariable Integer id){
        this.espMedMedicosServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}