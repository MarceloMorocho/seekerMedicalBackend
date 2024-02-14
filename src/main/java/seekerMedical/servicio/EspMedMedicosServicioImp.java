package seekerMedical.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.EspMedMedicoRequest;
import seekerMedical.dto.EspMedMedicosDTO;
import seekerMedical.entidad.EspMedMedicos;
import seekerMedical.repositorio.EspMedMedicosRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component /*Utilizada para indicar una clase como componente
Spring detectará automáticamente estas clases para inyección de dependencias*/
public class EspMedMedicosServicioImp implements EspMedMedicosServicio {

    @Autowired //Permite inyectar unas dependencias con otras
    private EspMedMedicosRepositorio espMedMedicosRepositorio;

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que estás
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución*/
    public List<EspMedMedicosDTO> findAll() {
        List<EspMedMedicosDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<EspMedMedicos> espMedMedicos = this.espMedMedicosRepositorio.findAll();
        for (EspMedMedicos em: espMedMedicos){
            EspMedMedicosDTO emDTO = MHelpers.modelMapper().map(em, EspMedMedicosDTO.class);
            dto.add(emDTO);
        }
        return dto;
    }

    @Override
    public EspMedMedicosDTO findByEmmId(Integer emmId) {
        Optional<EspMedMedicos> espMedMedicos = this.espMedMedicosRepositorio.findByEmmId(emmId);
        if (!espMedMedicos.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(espMedMedicos.get(), EspMedMedicosDTO.class);
    }

    @Override
    public void save(EspMedMedicoRequest espMedMedicosDTO) {
        EspMedMedicos espMedMedicos = MHelpers.modelMapper().map(espMedMedicosDTO, EspMedMedicos.class);
        this.espMedMedicosRepositorio.save(espMedMedicos);
    }

    @Override
    public void update(EspMedMedicoRequest request, Integer id) {
        Optional<EspMedMedicos> EspMedMedico = this.espMedMedicosRepositorio.findById(id);
    }

    @Override
    public void saveAll(List<EspMedMedicoRequest> espMedMedicos) {
        List<EspMedMedicos> emm = new ArrayList<>();
        for(EspMedMedicoRequest emmi: espMedMedicos){
            EspMedMedicos e = MHelpers.modelMapper().map(emmi,EspMedMedicos.class);
            emm.add(e);
        }
        this.espMedMedicosRepositorio.saveAll(emm);
    }

    @Override
    public void deleteByEmmId(Integer emmId) {
        this.espMedMedicosRepositorio.deleteById(emmId);
    }

    private EspMedMedicosDTO convertToEspMedMedicosDTO(final EspMedMedicos espMedMedicos){
        return MHelpers.modelMapper().map(espMedMedicos, EspMedMedicosDTO.class);
    }
}