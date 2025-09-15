package cl.ignacioaraya.teatroentradas.config;

/**
 * Clase que almacena constantes globales de la aplicación.
 * Se utiliza para centralizar precios, descuentos y valores fijos.
 */
public final class AppConstants {
    // Constructor privado para evitar instanciación
    private AppConstants() {}

    // Precios base según ubicación
    public static final int PRECIO_BASE_VIP = 30000;
    public static final int PRECIO_BASE_PLATEA = 20000;
    public static final int PRECIO_BASE_GENERAL = 10000;

    // Descuentos individuales por tipo de cliente
    public static final int DESCUENTO_ESTUDIANTE = 10;       // 10%
    public static final int DESCUENTO_ADULTO_MAYOR = 15;     // 15%

    // Reglas para descuentos por cantidad
    public static final int CANTIDAD_MINIMA_PARA_DESCUENTO_2 = 2;
    public static final int DESCUENTO_POR_2_ENTRADAS = 5;    // 5%

    public static final int CANTIDAD_MINIMA_PARA_DESCUENTO_3_O_MAS = 3;
    public static final int DESCUENTO_POR_3_O_MAS_ENTRADAS = 10; // 10%

    /**
     * Enum que representa las ubicaciones de los asientos.
     * Incluye un nombre legible para mostrar al usuario.
     */
    public enum Ubicacion {
        VIP("VIP"),
        PLATEA("Platea"),
        GENERAL("General");

        private final String nombre;

        Ubicacion(String nombre) { this.nombre = nombre; }

        // Retorna el nombre legible de la ubicación
        public String obtenerNombre() { return nombre; }
    }

    /**
     * Enum que representa los tipos de cliente.
     * Se usa para aplicar descuentos específicos.
     */
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
