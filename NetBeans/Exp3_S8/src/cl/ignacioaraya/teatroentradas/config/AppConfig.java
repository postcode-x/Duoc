package cl.ignacioaraya.teatroentradas.config;


public class AppConfig {

    // Nombre del teatro
    public static final String NOMBRE_TEATRO = "Teatro Moro";

    // Precio base 
    public static final int PRECIO_BASE = 30000;

    // Descuentos individuales por tipo de cliente
    public static final int DESCUENTO_ESTUDIANTE = 10;       // 10%
    public static final int DESCUENTO_ADULTO_MAYOR = 15;     // 15%

    // Capacidades arreglos
    public static final int MAX_CLIENTES = 100;
    public static final int MAX_VENTAS   = 100;
    public static final int MAX_ASIENTOS = 10;
    
    // Supongamos que la sala es rectangular: filas de 10 columnas
    public static final int COLUMNAS_POR_FILA = 5; // Debe ser múltiplo de MAX_ASIENTOS

    
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
    
    // Estados posibles de un asiento
    public enum Estado {
        DISPONIBLE("Disponible"),
        VENDIDO("Vendido");

        private final String nombre;

        Estado(String nombre) { 
            this.nombre = nombre; 
        }

        // Retorna el nombre legible del estado
        public String obtenerNombre() { 
            return nombre; 
        }
    }

}
