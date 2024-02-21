package seekerMedical.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import seekerMedical.dto.MedicoRequest;
import seekerMedical.dto.MedicosDTO;
import seekerMedical.servicio.MedicosServicioImp;
import seekerMedical.utilidades.Excepciones.ApiUnprocessableEntity;
import java.util.Optional;

@Component // Identifica la clase como componente y especifica metadatos
public class MedicoValidadorImpl implements MedicoValidador {

    @Autowired // Permite inyectar unas dependencias con otras
    MedicosServicioImp medicosServicioImp;

    @Autowired
    Validaciones validaciones;

    @Override // Asegura que el método de la superclase esté anulado y no simplemente sobrecargado
    public void validator(MedicoRequest request) throws ApiUnprocessableEntity {
        if(validaExistenciaMedCi(request.getMedCi())){
            this.message("El usuario ya existe");
        }

        if (!validaciones.validaCedulaEcuatoriana(request.getMedCi())) {
            this.message("La cédula no es válida");
        }

        if (!validaciones.validaNomApe(request.getMedNombres())) {
            this.message("El nombre es obligatorio, debe tener mínimo 3 caracteres");
        }

        if (!validaciones.validaNomApe(request.getMedApellidos())) {
            this.message("El apellido es obligatorio, debe tener mínimo 3 caracteres");
        }

       if(validaExistenciaMedCorreoElec(request.getMedCorreoElec())){
            this.message("El correo ya existe");
        }

        if(request.getMedCorreoElec() == null || request.getMedCorreoElec().isEmpty()){
            this.message("El correo electrónico es obligatorio");
        }
        if (!validaciones.validaCorreoElec(request.getMedCorreoElec())) {
            this.message("El correo electrónico no tiene un formato válido");
        }

        if(request.getMedDenominacion() == null || request.getMedDenominacion().isEmpty()){
            this.message("El tratamiento protocolario es obligatorio");
        }

        if(request.getMedDescripcionPro() == null || request.getMedDescripcionPro().isEmpty()){
            this.message("La descripción profesional es obligatoria");
        }

        if(request.getMedDireccionCalles() == null || request.getMedDireccionCalles().isEmpty()){
            this.message("La dirección del consultorio es obligatoria");
        }

        if(request.getMedDireccionCom() == null || request.getMedDireccionCom().isEmpty()){
            this.message("El edificio, oficina o piso del consultorio es obligatorio");
        }

        if (!validaciones.validaTelefonoMov(request.getMedTelefonoMov())) {
            this.message("El número del móvil es obligatorio, debe tener 10 dígitos");
        }

        if (!validaciones.validaTelefonoCon(request.getMedTelefonoCon())) {
            this.message("El teléfono fijo no es obligatorio, en caso de ingresarlo debe tener 9 dígitos");
        }

        if(request.getMedUbicacionLat() == null ){
            this.message("La ubicación del consultorio es obligatoria");
        }
        if(request.getMedUbicacionLon() == null ){
            this.message("La ubicación del consultorio es obligatoria");
        }

        if(request.getMedClave().length()<8){
            this.message("La clave es muy corta debe tener mínimo 8 caracteres");
        }
    }

    private void message(String message) throws ApiUnprocessableEntity{
        throw new ApiUnprocessableEntity(message);
    }

    // Validación de existencia del médico registrado previamente
    public boolean validaExistenciaMedCi(String id){
        Optional<MedicosDTO> med = Optional.ofNullable(medicosServicioImp.findByMedCi(id));
        return med.isPresent();
    }

    // Validación de existencia del correo del médico registrado previamente
    public boolean validaExistenciaMedCorreoElec(String medCorreoElec){
        Optional<MedicosDTO> med = Optional.ofNullable(medicosServicioImp.findByMedCorreoElec(medCorreoElec));
        return med.isPresent();
    }
}