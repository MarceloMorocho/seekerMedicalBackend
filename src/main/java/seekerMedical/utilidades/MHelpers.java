package seekerMedical.utilidades;

import org.modelmapper.ModelMapper;

public class MHelpers {
    // ModelMapper permite realizar la copia de datos de un objeto origen a una nueva instancia destino de otra clase
    public static ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
