package cl.ignacioaraya.teatroentradas.config;

public class AppConfig {

    // Nombre del teatro
    public static final String NOMBRE_TEATRO = "Teatro Moro";

    // Precios base según ubicación
    public static final int PRECIO_BASE_VIP = 30000;
    public static final int PRECIO_BASE_PLATEA = 20000;
    public static final int PRECIO_BASE_GENERAL = 10000;

    // Descuentos individuales por tipo de cliente
    public static final int DESCUENTO_ESTUDIANTE = 10;       // 10%
    public static final int DESCUENTO_ADULTO_MAYOR = 15;     // 15%

    // Configuracion de asientos
    public static final int CAPACIDAD = 100; // Capacidad total
    
    // Tipos de ubicacion
    public enum Ubicacion {
        VIP("VIP"),
        PLATEA("Platea"),
        GENERAL("General");

        private final String nombre;

        Ubicacion(String nombre) { this.nombre = nombre; }

        // Retorna el nombre legible de la ubicación
        public String obtenerNombre() { return nombre; }
    }

    // Tipos de cliente. Se usa para aplicar descuentos específicos
    public enum TipoCliente {
        NORMAL("Normal"),
        ESTUDIANTE("Estudiante"),
        ADULTO_MAYOR("Adulto Mayor");
        
        private final String nombre;

        TipoCliente(String nombre) { this.nombre = nombre; }

        // Retorna el nombre legible del tipo de cliente
        public String obtenerNombre() { return nombre; }
    }

}
