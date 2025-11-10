package cl.salmontt.gestionpersonas.app;

import cl.salmontt.gestionpersonas.config.AppConfig;
import cl.salmontt.gestionpersonas.model.Cliente;
import cl.salmontt.gestionpersonas.model.Direccion;
import cl.salmontt.gestionpersonas.model.Empleado;
import cl.salmontt.gestionpersonas.model.Rut;
import cl.salmontt.gestionpersonas.util.RutInvalidoException;

/**
 * Clase principal de la app.
 * <p>
 * Crea instancias de {@link Direccion}, {@link Rut}, {@link Cliente} y {@link Empleado}
 * para ejemplificar el uso de composición y extensión de clases. Se envuelve la lógica
 * dentro de un bloque try / catch para capturar posibles errores futuros en ingreso de
 * valores de RUT.
 * </p>
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Direcciones de ejemplo
            Direccion dir1 = new Direccion("Av. Siempre Viva 742", "Concepcíon", "Bio bio");
            Direccion dir2 = new Direccion("Calle Falsa 123", "Santiago", "Metropolitana");
            Direccion dir3 = new Direccion("Camino del Inca 2025", "La Serena", "Coquimbo");
            Direccion dir4 = new Direccion("Calle Sur 777", "Linares", "Maule");

            // RUTs de ejemplo
            Rut rut1 = new Rut("12345678-5");
            Rut rut2 = new Rut("98765432-K");
            Rut rut3 = new Rut("19283746-3");
            Rut rut4 = new Rut("7777777-7");

            // Clientes
            Cliente cliente1 = new Cliente("Maria Gonzalez", "maria.gonzalez@email.com", "987654321", dir1, rut1);
            Cliente cliente2 = new Cliente("Pedro Ramirez", "pedro.ramirez@email.com", "912345678", dir2, rut2);

            // Empleado
            Empleado empleado1 = new Empleado("Carla Soto", "carla.soto@empresa.cl", "998877665",
                    dir3, rut3, AppConfig.TipoEmpleado.ADMINISTRATIVO);
            Empleado empleado2 = new Empleado("Raul Cortez", "raul.cortez@empresa.cl", "938877665",
                    dir4, rut4, AppConfig.TipoEmpleado.TECNICO);

            // Mostrar resultados
            System.out.println("=== CLIENTES ===");
            System.out.println(cliente1);
            System.out.println(cliente2);

            System.out.println();

            System.out.println("=== EMPLEADOS ===");
            System.out.println(empleado1);
            System.out.println(empleado2);

        } catch (RutInvalidoException e) {
            System.out.println("Error al crear el RUT: " + e.getMessage());
        }
    }
}