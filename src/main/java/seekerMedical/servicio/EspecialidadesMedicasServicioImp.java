package seekerMedical.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.EspecialidadesMedicasDTO;
import seekerMedical.dto.EspecialidadMedicaRequest;
import seekerMedical.entidad.EspecialidadesMedicas;
import seekerMedical.repositorio.EspecialidadesMedicasRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component // Utilizado para indicar una clase como componente
public class EspecialidadesMedicasServicioImp implements EspecialidadesMedicasServicio {
    @Autowired // Permite inyectar unas dependencias con otras
    private EspecialidadesMedicasRepositorio especialidadesMedicasRepositorio;

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que está
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución */
    public List<EspecialidadesMedicasDTO> findAll() {
        List<EspecialidadesMedicasDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<EspecialidadesMedicas> especialidadesMedicas = this.especialidadesMedicasRepositorio.findAll();
        for (EspecialidadesMedicas em: especialidadesMedicas){
            EspecialidadesMedicasDTO emDTO = MHelpers.modelMapper().map(em, EspecialidadesMedicasDTO.class);
            dto.add(emDTO);
        }
        return dto;
    }

    @Override
    public EspecialidadesMedicasDTO findByEmDescripcion(String emDescripcion) {
        Optional<EspecialidadesMedicas> especialidadesMedicas = this.especialidadesMedicasRepositorio.findByEmDescripcion(emDescripcion);
        if (!especialidadesMedicas.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(especialidadesMedicas.get(), EspecialidadesMedicasDTO.class);
    }

    @Override
    public EspecialidadesMedicasDTO findByEmId(Integer emId) {
        Optional<EspecialidadesMedicas> especialidadesMedicas = this.especialidadesMedicasRepositorio.findById(emId);
        if (!especialidadesMedicas.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(especialidadesMedicas.get(), EspecialidadesMedicasDTO.class);
    }

    // Método que se declara como Integer porque devuelve el emId
    @Override
    public void save(EspecialidadMedicaRequest especialidadesMedicasDTO) {
        EspecialidadesMedicas especialidadesMedicas = MHelpers.modelMapper().map(especialidadesMedicasDTO, EspecialidadesMedicas.class);
        this.especialidadesMedicasRepositorio.save(especialidadesMedicas);
    }

    @Override
    public void update(EspecialidadMedicaRequest request, Integer id) {
        Optional<EspecialidadesMedicas> especialidadMedica = this.especialidadesMedicasRepositorio.findById(id);
        EspecialidadesMedicas emId = especialidadMedica.get();
        emId.setEmDescripcion(request.getEmDescripcion());
        this.especialidadesMedicasRepositorio.save(emId);
    }

    @Override
    public void saveAll(List<EspecialidadMedicaRequest> dias) {
        List<EspecialidadesMedicas> especialidadMedica = new ArrayList<>();
        for(EspecialidadMedicaRequest emId: dias){
            EspecialidadesMedicas e = MHelpers.modelMapper().map(emId,EspecialidadesMedicas.class);
            especialidadMedica.add(e);
        }
        this.especialidadesMedicasRepositorio.saveAll(especialidadMedica);
    }

    @Override
    public void deleteByEmId(Integer emId) {
        this.especialidadesMedicasRepositorio.deleteById(emId);
    }

    private EspecialidadesMedicasDTO convertToEspecialidadesMedicasDTO(final EspecialidadesMedicas especialidadesMedicas){
        return MHelpers.modelMapper().map(especialidadesMedicas, EspecialidadesMedicasDTO.class);
    }
}