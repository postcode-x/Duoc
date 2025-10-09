package cl.ignacioaraya.teatroentradas.config;

public class AppConfig {

    // Nombre del teatro
    public static final String NOMBRE_TEATRO = "Teatro Moro";

    // Representa una zona del teatro con nombre y precio
    public record Zona(String nombre, double precio) {

    }

    // Definicion de zonas del teatro (vip, palco, platea baja, platea alta y galería)
    public static final Zona[] ZONAS = {
        new Zona("VIP", 20000.0),
        new Zona("PALCO", 15000.0),
        new Zona("PLATEA BAJA", 12000.0),
        new Zona("PLATEA ALTA", 10000.0),
        new Zona("GALERIA", 5000.0)
    };

    // Descuentos individuales por tipo de cliente
    public static final int DESCUENTO_NINOS = 5; // 5%
    public static final int DESCUENTO_MUJER = 7; // 7%
    public static final int DESCUENTO_ESTUDIANTE = 25; // 25%
    public static final int DESCUENTO_ADULTO_MAYOR = 30; // 30%

    // Rangos de edades para descuentos
    public static final int EDAD_MAX_NINO = 12;
    public static final int EDAD_MAX_ESTUDIANTE = 25;
    public static final int EDAD_MIN_ADULTO_MAYOR = 65;

    // Configuracion de asientos
    public static final int FILAS_POR_ZONA = 2; // Filas por cada zona
    public static final int ASIENTOS_POR_FILA = 5; // Asientos por fila
    public static final int CAPACIDAD = FILAS_POR_ZONA * ASIENTOS_POR_FILA * ZONAS.length; // Capacidad total

    // Estados posibles de un asiento
    public enum Estado {
        DISPONIBLE("Disponible"),
        SELECCIONADO("Seleccionado"),
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
