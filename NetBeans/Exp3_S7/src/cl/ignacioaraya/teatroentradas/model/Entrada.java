package cl.ignacioaraya.teatroentradas.model;


import cl.ignacioaraya.teatroentradas.config.AppConfig;


/**
 * Representa una entrada (ticket) dentro del teatro
 */
public class Entrada {
    // Número único de la entrada
    private int numero;
    // Ubicación dentro del teatro (VIP, Platea, General, etc.)
    private final AppConfig.Ubicacion ubicacion;
    // Tipo de cliente (Estudiante, Adulto Mayor, Normal, etc.)
    private final AppConfig.TipoCliente tipoCliente;
    // Precio final de la entrada
    private final int precio;

    // Constructor que inicializa todos los atributos de la entrada
    public Entrada(int numero, AppConfig.Ubicacion ubicacion, AppConfig.TipoCliente tipoCliente, int precio) {
        this.numero = numero;
        this.ubicacion = ubicacion;
        this.tipoCliente = tipoCliente;
        this.precio = precio;
    }
    
    // Setter del número
    public void setNumero(int numero) {
        this.numero = numero;
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
    public AppConfig.Ubicacion getUbicacion(){
        return ubicacion;
    }
    
    // Getter del tipo de cliente
    public AppConfig.TipoCliente getTipoCliente(){
        return tipoCliente;
    }
    
    // Devuelve un String con los datos de la entrada para mostrar por consola
    public String mostrar() {
        return ubicacion + " - " + tipoCliente.obtenerNombre() + " - $" + precio;
    }
    
    // Devuelve un String con dato de entrada a mostrar en resumen de ventas
    public String mostrarResumen() {
        return "Boleta #" + numero + " - Ubicacion: " + ubicacion + 
                " - Costo final: $" + precio + 
                " - Descuento aplicado: " + getDescuento() + "%" + " (" + tipoCliente.obtenerNombre() + ")";
    }
    
    // Devuelve el descuento aplicado
    public int getDescuento() {
        return  getTipoCliente() == AppConfig.TipoCliente.ADULTO_MAYOR 
                    ? AppConfig.DESCUENTO_ADULTO_MAYOR 
                    : getTipoCliente() == AppConfig.TipoCliente.ESTUDIANTE 
                        ? AppConfig.DESCUENTO_ESTUDIANTE 
                        : 0;
    }
    
    public int obtenerPrecioBase(){
        return ubicacion == AppConfig.Ubicacion.GENERAL 
                ? AppConfig.PRECIO_BASE_GENERAL 
                : ubicacion == AppConfig.Ubicacion.PLATEA 
                    ? AppConfig.PRECIO_BASE_PLATEA 
                    : AppConfig.PRECIO_BASE_VIP;
    } 
    
}