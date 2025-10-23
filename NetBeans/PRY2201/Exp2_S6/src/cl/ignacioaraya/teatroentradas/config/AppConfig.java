package cl.ignacioaraya.teatroentradas.config;

public class AppConfig {

    // Nombre del teatro
    public static final String NOMBRE_TEATRO = "Teatro Moro";

    // Tiempo de reserva de un asiento en segundos
    public static final int TIEMPO_DE_RESERVA_EN_SEGUNDOS = 120;

    // Representa una zona del teatro con nombre y precio
    public record Zona(String nombre, double precio) {}

    // Definicion de zonas del teatro
    public static final Zona[] ZONAS = {
        new Zona("A", 20000.0),
        new Zona("B", 15000.0)
    };

    // Configuracion de asientos
    public static final int FILAS_POR_ZONA = 2;        // Filas por cada zona
    public static final int ASIENTOS_POR_FILA = 5;     // Asientos por fila
    public static final int CAPACIDAD = FILAS_POR_ZONA * ASIENTOS_POR_FILA * ZONAS.length; // Capacidad total

    // Estados posibles de un asiento
    public enum Estado {
        DISPONIBLE("Disponible"),
        RESERVADO("Reservado"),
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
