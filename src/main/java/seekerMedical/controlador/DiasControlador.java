package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.DiaRequest;
import seekerMedical.servicio.DiasServicio;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.validador.DiaValidadorImpl;
import seekerMedical.validador.Validaciones;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/dias") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class DiasControlador {
    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar
    private DiasServicio diasServicio;

    @Autowired
    private DiaValidadorImpl diaValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produces = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE )
    // Retorna la lista colocada en diasServicio
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(this.diasServicio.findAll());
    }

    @GetMapping (value = "/by/{id}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByDiaId(@PathVariable("diaId") Integer diaId){
        return ResponseEntity.ok(this.diasServicio.findByDiaId(diaId));
    }

    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveDia(@RequestBody DiaRequest request) throws ApiUnprocessableEntity {
        this.diaValidadorImpl.validator(request);
        this.diasServicio.save(request);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteDia(@PathVariable Integer id){
        this.diasServicio.deleteByDiaId(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updateDia(@RequestBody DiaRequest request, @PathVariable Integer id){
        this.diasServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}