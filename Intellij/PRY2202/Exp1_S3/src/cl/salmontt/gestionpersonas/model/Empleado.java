package cl.salmontt.gestionpersonas.model;

import java.time.LocalDate;
import java.util.Random;
import cl.salmontt.gestionpersonas.config.AppConfig;

/**
 * Representa a un empleado dentro del sistema.
 * <p>
 * Extiende la clase {@link Persona} y agrega información específica
 * relacionada con su cargo, fecha de ingreso e identificación interna.
 * </p>
 */
public class Empleado extends Persona {
    private int id;
    private LocalDate fechaIngreso;
    private AppConfig.TipoEmpleado tipo;

    /**
     * Constructor vacío.
     */
    public Empleado() {
    }

    /**
     * Constructor parametrizado.
     * <p>
     * Genera un identificador aleatorio y asigna la fecha de ingreso actual.
     * </p>
     *
     * @param nombre    Nombre del empleado.
     * @param correo    Correo electrónico del empleado.
     * @param telefono  Teléfono de contacto del empleado.
     * @param direccion Dirección física del empleado.
     * @param rut       RUT del empleado.
     * @param tipo      Tipo o categoría del empleado según {@link AppConfig.TipoEmpleado}.
     */
    public Empleado(String nombre, String correo, String telefono, Direccion direccion, Rut rut, AppConfig.TipoEmpleado tipo) {
        super(nombre, correo, telefono, direccion, rut);
        Random random = new Random();
        LocalDate today = LocalDate.now();

        this.id = random.nextInt(1_000_000);
        this.fechaIngreso = today;
        this.tipo = tipo;
    }

    /** Getters */

    public int getId() {
        return id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public AppConfig.TipoEmpleado getTipo() {
        return tipo;
    }

    /** Setters **/

    public void setTipo(AppConfig.TipoEmpleado tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve una representación textual del empleado,
     * incluyendo sus datos personales, dirección, RUT, fecha de ingreso, identificador de sistema y tipo.
     *
     * @return Cadena con la información del empleado.
     */
    @Override
    public String toString() {
        return "Empleado: " + getNombre() + "\n" +
                "Correo: " + getCorreo() + "\n" +
                "Teléfono: " + getTelefono() + "\n" +
                "Dirección: " + getDireccion() + "\n" +
                "RUT: " + getRut() + "\n" +
                "Fecha ingreso: " + fechaIngreso + "\n" +
                "ID: " + id + "\n" +
                "Tipo: " + tipo + "\n";
    }
}
