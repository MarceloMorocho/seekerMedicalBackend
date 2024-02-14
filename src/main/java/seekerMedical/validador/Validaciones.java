package seekerMedical.validador;

import org.springframework.stereotype.Component;

@Component
public class Validaciones {

    // Validación de correo electrónico
    private static final String CORREO_REGEX = "^[A-Za-z0-9+_.-]+@(\\w+\\.\\w+)$";

    public boolean validaCorreoElec(String correo) {
        return correo.matches(CORREO_REGEX);
    }

    // Validación de cédula ecuatoriana
    public boolean validaCedulaEcuatoriana(String cedula) {
        if (cedula == null || cedula.length() != 10 || !cedula.matches("^[0-9]+$")) {
            return false;
        }
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            //El operador += suma el valor de su derecha a la variable
            //El operador ? se usa para sentencias condicionales y cuando se combina con los
            //dos puntos : funciona como una alternativa compacta if.....else
            suma += (digito > 9) ? digito - 9 : digito;
        }
        // % es el operador Módulo aritmético
        int ultimoDigitoCalculado = (suma % 10 == 0) ? 0 : 10 - (suma % 10);
        int ultimoDigitoReal = Character.getNumericValue(cedula.charAt(9));
        return ultimoDigitoCalculado == ultimoDigitoReal;
    }

    // Validación del ruc persona natural
    public boolean validaRucNatural(String ruc) {
        if (ruc == null || ruc.length() != 13 || !ruc.matches("^[0-9]+$")) {
            return false;
        }
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(ruc.charAt(i)) * coeficientes[i];
            //El operador += suma el valor de su derecha a la variable
            //El operador ? se usa para sentencias condicionales y cuando se combina con los
            //dos puntos : funciona como una alternativa compacta if.....else
            suma += (digito > 9) ? digito - 9 : digito;
        }
        // % es el operador Módulo aritmético
        int ultimoDigitoCalculado = (suma % 10 == 0) ? 0 : 10 - (suma % 10);
        int ultimoDigitoReal = Character.getNumericValue(ruc.charAt(9));
        return ultimoDigitoCalculado == ultimoDigitoReal && ruc.substring(10).equals("001");
    }

    // Validación de ruc jurídicos y extanjeros sin cédula
    public static boolean validaRucJur(String ruc) {
        if (ruc == null || ruc.length() != 13 || !ruc.matches("^[0-9]+$")) {
            return false;
        }
        int tercerDigito = Integer.parseInt(ruc.substring(2, 3));
        if (tercerDigito != 9) {
            return false;
        }
        int[] coeficientes = {4, 3, 2, 7, 6, 5, 4, 3, 2};
        int suma = 0;

        for (int i = 0; i < 9; i++) {
            int digito = Integer.parseInt(ruc.substring(i, i + 1));
            suma += digito * coeficientes[i];
        }
        int modulo = suma % 11;
        int ultimoDigitoCalculado = modulo == 0 ? 0 : 11 - modulo;
        int ultimoDigitoReal = Integer.parseInt(ruc.substring(9, 10));
        return ultimoDigitoCalculado == ultimoDigitoReal && ruc.substring(10).equals("001");
    }

    // Validación de nombres, apellidos y nombre del negocio
    public boolean validaNomApe(String nombresApe) {
        if (nombresApe == null || nombresApe.length() < 3 || !nombresApe.matches("[A-Za-zÁ-Úá-ú.\\s']+")) {
            return false;
        }
        return true;
    }

    public boolean validaNombreNegocio(String nombreNegocio) {
        if (nombreNegocio == null || nombreNegocio.length() < 3 || !nombreNegocio.matches("[A-Za-zÁ-Úá-ú0-9.\\s']+")) {
            return false;
        }
        return true;
    }

    // Validación de teléfono móvil
    public boolean validaTelefonoMov(String telefonoMov) {
        if (telefonoMov == null || telefonoMov.length() != 10 || !telefonoMov.matches("^[0-9]+$")) {
            return false;
        }
        return true;
    }

    // Validación de teléfono fijo para médicos y farmacias
    public boolean validaTelefonoCon(String telefonoCon) {
        if (telefonoCon != null) {
            if (telefonoCon.length() != 9 || !telefonoCon.matches("^[0-9]+$")) {
                return false;
            }
        }
        return true;
    }

    // Validación de horarios
    public boolean validaHorario(String horariosAtencion) {
        if (horariosAtencion == null || horariosAtencion.length() != 5 || !horariosAtencion.matches("\\d{2}:\\d{2}")) {
            return false;
        }
        return true;
    }
}