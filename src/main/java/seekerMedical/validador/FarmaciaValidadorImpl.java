package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.FarmaciaRequest;
import seekerMedical.dto.FarmaciasDTO;
import seekerMedical.servicio.FarmaciasServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import java.util.Optional;

@Component //Identifica la clase como componente y especifica metadatos
public class FarmaciaValidadorImpl implements FarmaciaValidador {
    @Autowired //Permite inyectar unas dependencias con otras dentro de Spring Boot
    FarmaciasServicioImp farmaciasServicioImp;

    @Autowired // Agrega la inyección de Validaciones
    Validaciones validaciones;

    //Override asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    @Override
    public void validator(FarmaciaRequest request) throws ApiUnprocessableEntity {
        if(validaExistenciaFarRuc(request.getFarRuc())){
            this.message("La farmacia ya existe");
        }

        //Validación del ruc para personal natural y jurídica
        boolean rucNatural= validaciones.validaRucNatural(request.getFarRuc());
        boolean rucJur= validaciones.validaRucJur(request.getFarRuc());
        if (!(rucNatural || rucJur)) {
            this.message("El ruc no es válido");
        }

        if (!validaciones.validaNombreNegocio(request.getFarNombreNegocio())) {
            this.message("El nombre del negocio es obligatorio, debe tener mínimo 3 caracteres");
        }

        if(validaExistenciaFarCorreoElec(request.getFarCorreoElec())){
            this.message("El correo ya existe");
        }
        if(request.getFarCorreoElec() == null || request.getFarCorreoElec().isEmpty()){
            this.message("El correo electrónico es obligatorio");
        }
        if (!validaciones.validaCorreoElec(request.getFarCorreoElec())) {
            this.message("El correo electrónico no tiene un formato válido");
        }

        if(request.getFarDireccionCalles() == null || request.getFarDireccionCalles().isEmpty()){
            this.message("La dirección de la farmacia es obligatoria");
        }

        if(request.getFarDireccionCom() == null || request.getFarDireccionCom().isEmpty()){
            this.message("El edificio, oficina o piso del consultorio es obligatorio");
        }

        if (!validaciones.validaTelefonoMov(request.getFarTelefonoMov())) {
            this.message("El número del móvil es obligatorio, debe tener 10 dígitos");
        }

        if (!validaciones.validaTelefonoCon(request.getFarTelefonoCon())) {
            this.message("El teléfono fijo no es obligatorio, en caso de ingresarlo debe tener 9 dígitos");
        }

        if(request.getFarUbicacionLat() == null ){
            this.message("La ubicación del consultorio es obligatoria");
        }
        if(request.getFarUbicacionLon() == null ){
            this.message("La ubicación del consultorio es obligatoria");
        }

        if(request.getFarClave().length()<8){
            this.message("La clave es muy corta debe tener mínimo 8 caracteres");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }

    //Validación de existencia de la farmacia registrada previamente
    public boolean validaExistenciaFarRuc(String id){
        Optional<FarmaciasDTO> far = Optional.ofNullable(farmaciasServicioImp.findByFarRuc(id));
        return far.isPresent();
    }

    //Validación de existencia del correo de la farmacia registrada previamente
    public boolean validaExistenciaFarCorreoElec(String farCorreoElec){
        Optional<FarmaciasDTO> far = Optional.ofNullable(farmaciasServicioImp.findByFarCorreoElec(farCorreoElec));
        return far.isPresent();
    }
}
