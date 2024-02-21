package seekerMedical.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.HorariosAtencionRequest;
import seekerMedical.dto.HorariosAtencionDTO;
import seekerMedical.entidad.HorariosAtencion;
import seekerMedical.repositorio.HorariosAtencionRepositorio;
import seekerMedical.utilidades.MHelpers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component // Utilizado para indicar una clase como componente
public class HorariosAtencionServicioImp implements HorariosAtencionServicio {

    @Autowired // Permite inyectar unas dependencias con otras
    private HorariosAtencionRepositorio horariosAtencionRepositorio;

    @Override /* Utilizado forzar al compilador a comprobar en tiempo de compilación que estás
    sobreescribiendo correctamente un método y de este modo se evita errores en tiempo de ejecución */
   public List<HorariosAtencionDTO> findAll() {
        List<HorariosAtencionDTO> dto = new ArrayList<>();
        //Iterable sirve para recorrer la colección o lista de los elementos
        Iterable<HorariosAtencion> horariosAtencion = this.horariosAtencionRepositorio.findAll();
        for (HorariosAtencion hm: horariosAtencion){
            HorariosAtencionDTO hmDTO = MHelpers.modelMapper().map(hm, HorariosAtencionDTO.class);
            dto.add(hmDTO);
        }
        return dto;
    }

    @Override
    public HorariosAtencionDTO findByHmId(Integer hmId) {
        Optional<HorariosAtencion> horariosAtencion = this.horariosAtencionRepositorio.findByHmId(hmId);
        if (!horariosAtencion.isPresent()){
            return null;
        }
        return MHelpers.modelMapper().map(horariosAtencion.get(), HorariosAtencionDTO.class);
    }

    @Override
    public void saveAll(List<HorariosAtencionRequest> horariosAtencion) {
        List<HorariosAtencion> hm = new ArrayList<>();
        for(HorariosAtencionRequest hmi: horariosAtencion){
            HorariosAtencion m = MHelpers.modelMapper().map(hmi,HorariosAtencion.class);
            hm.add(m);
        }
        this.horariosAtencionRepositorio.saveAll(hm);
    }

    @Override
    public void save(HorariosAtencionRequest horariosAtencionDTO) {
        HorariosAtencion horariosAtencion = MHelpers.modelMapper().map(horariosAtencionDTO, HorariosAtencion.class);
        this.horariosAtencionRepositorio.save(horariosAtencion);
    }

    @Override
    public void update(HorariosAtencionRequest request, Integer id) {
        Optional<HorariosAtencion> horariosAtencion = this.horariosAtencionRepositorio.findById(id);
        HorariosAtencion hmi = horariosAtencion.get();
        hmi.setHm1Inicio(request.getHm1Inicio());
        hmi.setHm1Fin(request.getHm1Fin());
        hmi.setHm2Inicio(request.getHm2Inicio());
        hmi.setHm2Fin(request.getHm2Fin());
        this.horariosAtencionRepositorio.save(hmi);
    }

    @Override
    public void deleteByHmId(Integer hmId) {
        this.horariosAtencionRepositorio.deleteById(hmId);
    }

    private HorariosAtencionDTO convertToHorariosAtencionDTO(final HorariosAtencion horariosAtencion){
        return MHelpers.modelMapper().map(horariosAtencion, HorariosAtencionDTO.class);
    }
}