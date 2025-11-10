package cl.salmontt.gestionpersonas.model;

import cl.salmontt.gestionpersonas.util.RutInvalidoException;

    /**
     * Representa un RUT chileno. Contiene validación simple.
     */
    public class Rut {

    // Número del RUT en formato String
    private String numero;

    /**
      * Constructor que recibe el RUT y valida su formato.
      * @param numeroRUT el RUT ingresado por el usvario
      */
    public Rut(String numeroRUT) throws RutInvalidoException {
        // Validación simple: solo acepta dígitos y la letra final (puede ser k o K)
        if (!numeroRUT.matches("[0-9]+-[0-9kK]")) {
            throw new RutInvalidoException("Formato de RUT no valido.");
            }
            this.numero = numeroRUT;

    }

    /**
      * Retorna el número de RUT
      */
    public String getNumero() {
        return numero;
    }

    /**
      * Devuelve una representación textual del RUT.
      *
      * @return Cadena con RUT.
      */
    @Override
    public String toString() {
        return "RUT: " + numero;
    }}

