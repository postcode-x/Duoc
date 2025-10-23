package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConstants;


/**
 * Representa una entrada (ticket) dentro del teatro
 */
public class Entrada {
    // Número único de la entrada
    private final int numero;
    // Ubicación dentro del teatro (VIP, Platea, General, etc.)
    private final AppConstants.Ubicacion ubicacion;
    // Tipo de cliente (Estudiante, Adulto Mayor, Normal, etc.)
    private final AppConstants.TipoCliente tipoCliente;
    // Precio final de la entrada
    private int precio;

    // Constructor que inicializa todos los atributos de la entrada
    public Entrada(int numero, AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente, int precio) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.tipoCliente = tipoCliente;
        this.precio = precio;
    }
    
    // Getter del precio
    public int getPrecio() {
        return precio;
    }
    
    // Getter del número único
    public int getNumero() {
        return numero;
    }
    
    // Getter de la ubicación
    public AppConstants.Ubicacion getUbicacion(){
        return ubicacion;
    }
    
    // Getter del tipo de cliente
    public AppConstants.TipoCliente getTipoCliente(){
        return tipoCliente;
    }
    
    // Devuelve un String con los datos de la entrada para mostrar por consola
    public String mostrar() {
        return numero + ") " + ubicacion + " - " + tipoCliente.obtenerNombre() + " - $" + precio;
    }
}
