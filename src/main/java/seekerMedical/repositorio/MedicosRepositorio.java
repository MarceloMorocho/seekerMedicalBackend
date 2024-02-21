package seekerMedical.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import seekerMedical.entidad.Medicos;
import java.util.Optional;

@Repository // Indica que la clase decorada es un repositorio
public interface MedicosRepositorio extends CrudRepository<Medicos, String> {
    // Metadato que especifica que la interfaz debe tener una semántica transaccional
    // Transactional readOnly utilizado para que el método no guarde el estado y tenga mejor rendimiento de consulta
    @Transactional(readOnly = true)
    Optional<Medicos> findByMedCorreoElec(String medCorreoElec);
}