package seekerMedical.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.servicio.HorariosAtencionServicio;;
import seekerMedical.servicio.MedicosServicio;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import seekerMedical.validador.HorariosAtencionValidadorImpl;
import seekerMedical.validador.Validaciones;

@RestController // Componente capaz de recibir peticiones http y responderlas
@RequestMapping(path = "/horariosAtencion") // Utilizado para asignar solicitudes web a clases de controlador específicas
public class HorariosAtencionControlador {
    @Autowired // Utilizado para retornar la información que se necesita traer y enlazar con los servicios
    private HorariosAtencionServicio horariosAtencionServicio;

    @Autowired
    private HorariosAtencionValidadorImpl horariosAtencionValidadorImpl;

    @Autowired
    private Validaciones validaciones;

    @Autowired
    private MedicosControlador medicosControlador;

    @Autowired
    private MedicosServicio medicosServicio;

    // Método para leer
    // GetMapping se utiliza para el mapeo de solicitudes
    // produce = MediaType.APPLICATION_JSON_VALUE significa que la respuesta que se generará se convertirá al formato JSON
   @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE )// Simplifica el manejo de los métodos
    // Se retorna la lista colocada en medicosServicio
    public ResponseEntity<Object> getAll(){
       return ResponseEntity.ok(this.horariosAtencionServicio.findAll());
    }

    @GetMapping (value = "/by/{hmId}",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> findByHmId(@PathVariable("hmId") Integer hmId){
        return ResponseEntity.ok(this.horariosAtencionServicio.findByHmId(hmId));
    }

    // Método para guardar
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)//Simplifica el manejo de los métodos
    public ResponseEntity<Object> saveHorariosAtencion(@RequestBody HorariosAtencionRequest request) throws ApiUnprocessableEntity {
        this.horariosAtencionValidadorImpl.validator(request);
        this.horariosAtencionServicio.save(request);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para borrar
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Object> deleteHorariosAtencion(@PathVariable Integer id){
        this.horariosAtencionServicio.deleteByHmId(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    // Método para actualizar
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Object> updateHorariosAtencion(@RequestBody HorariosAtencionRequest request, @PathVariable Integer id){
        this.horariosAtencionServicio.update(request,id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}