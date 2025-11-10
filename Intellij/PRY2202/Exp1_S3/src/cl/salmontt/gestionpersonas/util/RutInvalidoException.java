package cl.salmontt.gestionpersonas.util;

/**
 * Excepción personalizada que se lanza cuando un RUT no cumple con el formato esperado.
 */
public class RutInvalidoException extends Exception {

    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message Descripción del error.
     */
    public RutInvalidoException(String message) {
        super(message);
    }
}
