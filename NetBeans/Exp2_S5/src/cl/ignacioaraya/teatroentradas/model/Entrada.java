package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConstants;


/**
 * Representa un asiento dentro del teatro
 */
public class Entrada {
    private final int numero;
    private final AppConstants.Ubicacion ubicacion;
    private final AppConstants.TipoCliente tipoCliente;
    private int precio;

    // Constructor
    public Entrada(int numero, AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente, int precio) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.tipoCliente = tipoCliente;
        this.precio = precio;
    }
    
    public int getPrecio() {
        return precio;
    }
    
    // Para mostrar asiento individual
    public String mostrar() {
        return numero + ") " + ubicacion + " - " + tipoCliente + " - $" + precio;
    }
    
}
