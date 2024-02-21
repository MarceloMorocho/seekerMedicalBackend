package seekerMedical.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.DiasDTO;
import seekerMedical.dto.DiaRequest;
import seekerMedical.entidad.Dias;
import seekerMedical.repositorio.DiasRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component // Utilizado para indicar una clase como componente
public class DiasServicioImp implements DiasServicio {
    @Autowired // Permite inyectar unas dependencias con otras
    private DiasRepositorio diasRepositorio;

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que está
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución */
    public List<DiasDTO> findAll() {
        List<DiasDTO> dto = new ArrayList<>();
        // Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<Dias> dias = this.diasRepositorio.findAll();
        for (Dias di: dias){
            DiasDTO diDTO = MHelpers.modelMapper().map(di, DiasDTO.class);
            dto.add(diDTO);
        }
        return dto;
    }

    @Override
    public DiasDTO findByDiaId(Integer diaId) {
        Optional<Dias> dias = this.diasRepositorio.findById(diaId);
        if (!dias.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(dias.get(), DiasDTO.class);
    }

    @Override
    public void save(DiaRequest diasDTO) {
        Dias dias = MHelpers.modelMapper().map(diasDTO, Dias.class);
        this.diasRepositorio.save(dias);
    }

    // Método que se declara como Integer porque devuelve el diaId
    @Override
    public void update(DiaRequest request, Integer id) {
        Optional<Dias> dia = this.diasRepositorio.findById(id);
        Dias diaId = dia.get();
        diaId.setDiaDescripcion(request.getDiaDescripcion());
        this.diasRepositorio.save(diaId);
    }

    @Override
    public void saveAll(List<DiaRequest> dias) {
        List<Dias> dia = new ArrayList<>();
        for(DiaRequest diaId: dias){
            Dias d = MHelpers.modelMapper().map(diaId,Dias.class);
            dia.add(d);
        }
        this.diasRepositorio.saveAll(dia);
    }

    @Override
    public void deleteByDiaId(Integer diaId) {
        this.diasRepositorio.deleteById(diaId);
    }

    private DiasDTO convertToDiasDTO(final Dias dias){
        return MHelpers.modelMapper().map(dias, DiasDTO.class);
    }
}