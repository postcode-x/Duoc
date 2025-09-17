package cl.ignacioaraya.teatroentradas.config;

public class AppConfig {
    
    public static final String NOMBRE_TEATRO = "Teatro Moro";
    public static final int CAPACIDAD = 50;
    public static final int FILAS_POR_ZONA = 2;
    public static final int ASIENTOS_POR_FILA = 5;
    public static final int TIEMPO_DE_RESERVA_EN_SEGUNDOS = 10; 
    public record Zona(String nombre, double precio) {}
    public static final Zona[] ZONAS = {
        new Zona("A", 20000.0),
        new Zona("B", 15000.0),
        new Zona("C", 10000.0)
    };
    
    public enum Estado { 
        DISPONIBLE ("Disponible"), 
        RESERVADO ("Reservado"), 
        VENDIDO ("Vendido");
    
        private final String nombre;

        Estado(String nombre) { this.nombre = nombre; }

        // Retorna el nombre legible del estado
        public String obtenerNombre() { return nombre; }
    }
    
}
