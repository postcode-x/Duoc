package cl.salmontt.gestionpersonas.config;

/**
 * Clase de configuración general de la aplicación.
 * <p>
 * Contiene definiciones y enumeraciones utilizadas en el sistema.
 * </p>
 */
public class AppConfig {

    /**
     * Enumeración que representa los tipos de empleados dentro del sistema.
     */
    public enum TipoEmpleado {
        ADMINISTRATIVO("Administrativo"),
        TECNICO("Técnico"),
        VENTAS("Ventas");

        private final String nombre;

        /**
         * Constructor que asigna el nombre legible al tipo de empleado.
         *
         * @param nombre Nombre descriptivo del tipo de empleado.
         */
        TipoEmpleado(String nombre) {
            this.nombre = nombre;
        }

        /**
         * Retorna el nombre legible del tipo de empleado.
         *
         * @return Nombre del tipo de empleado.
         */
        public String obtenerNombre() {
            return nombre;
        }
    }

}
