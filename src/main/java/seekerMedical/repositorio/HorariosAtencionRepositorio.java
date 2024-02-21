package seekerMedical.repositorio;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import seekerMedical.entidad.HorariosAtencion;
import java.util.List;
import java.util.Optional;

@Repository // Indica que la clase decorada es un repositorio
public interface HorariosAtencionRepositorio extends CrudRepository<HorariosAtencion, Integer> {
    // Metadato que especifica que la interfaz debe tener una semántica transaccional
    // Transactional readOnly utilizado para que el método no guarde el estado y tenga mejor rendimiento de consulta
    @Transactional(readOnly = true)
    Optional<HorariosAtencion> findByHmId(Integer hmId);

    @Transactional(readOnly = true)
    List<HorariosAtencion> findAll();
}