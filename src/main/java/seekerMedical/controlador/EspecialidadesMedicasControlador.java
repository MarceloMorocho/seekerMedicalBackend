package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.EspecialidadMedicaRequest;
import seekerMedical.servicio.EspecialidadesMedicasServicio;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.validador.EspecialidadMedicaValidadorImpl;
import seekerMedical.validador.Validaciones;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/especialidadesMedicas") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class EspecialidadesMedicasControlador {
    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar
    private EspecialidadesMedicasServicio especialidadesMedicasServicio;

    @Autowired
    private EspecialidadMedicaValidadorImpl especialidadMedicaValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produces = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE )
    // Retorna la lista colocada en especialidadesMedicasServicio
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(this.especialidadesMedicasServicio.findAll());
    }

    @GetMapping (value = "/byEmDescripcion/{emDescripcion}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByEmDescripcion(@PathVariable("emDescripcion") String emDescripcion){
        return ResponseEntity.ok(this.especialidadesMedicasServicio.findByEmDescripcion(emDescripcion));
    }

    @GetMapping (value = "/by/{id}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByEmId(@PathVariable("emId") Integer emId){
        return ResponseEntity.ok(this.especialidadesMedicasServicio.findByEmId(emId));
    }

    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)//Simplifica el manejo de los métodos
    public ResponseEntity<Object> saveEspecialidadMedica(@RequestBody EspecialidadMedicaRequest request) throws ApiUnprocessableEntity {
        this.especialidadMedicaValidadorImpl.validator(request);
        this.especialidadesMedicasServicio.save(request);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteEspecialidadesMedicas(@PathVariable Integer id){
        this.especialidadesMedicasServicio.deleteByEmId(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updateEspecialidadMedica(@RequestBody EspecialidadMedicaRequest request, @PathVariable Integer id){
        this.especialidadesMedicasServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}