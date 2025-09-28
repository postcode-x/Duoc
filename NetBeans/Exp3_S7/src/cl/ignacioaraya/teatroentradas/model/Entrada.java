package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;


// Representa una entrada (ticket) dentro del teatro
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
        return  tipoCliente == AppConfig.TipoCliente.ADULTO_MAYOR 
                    ? AppConfig.DESCUENTO_ADULTO_MAYOR 
                    : tipoCliente == AppConfig.TipoCliente.ESTUDIANTE 
                        ? AppConfig.DESCUENTO_ESTUDIANTE 
                        : 0;
    }
    
    // Devuelve el precio base de la entrada
    public int obtenerPrecioBase(){
        return ubicacion == AppConfig.Ubicacion.GENERAL 
                ? AppConfig.PRECIO_BASE_GENERAL 
                : ubicacion == AppConfig.Ubicacion.PLATEA 
                    ? AppConfig.PRECIO_BASE_PLATEA 
                    : AppConfig.PRECIO_BASE_VIP;
    } 
    
    // Getters y Setters
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public int getPrecio() {
        return precio;
    }
    
    public int getNumero() {
        return numero;
    }

    public AppConfig.Ubicacion getUbicacion(){
        return ubicacion;
    }
    
    public AppConfig.TipoCliente getTipoCliente(){
        return tipoCliente;
    }
    
}