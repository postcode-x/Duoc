package cl.salmontt.gestionpersonas.model;

import java.time.LocalDate;
import java.util.Random;

/**
 * Representa a un cliente dentro del sistema.
 * <p>
 * Extiende la clase {@link Persona} y agrega información específica
 * relacionada con su identificación en el sistema y la fecha de registro.
 * </p>
 */
public class Cliente extends Persona {
    private int id;
    private LocalDate fechaRegistro;

    /**
     * Constructor vacío.
     */
    public Cliente() {
    }

    /**
     * Constructor parametrizado.
     * <p>
     * Genera un identificador aleatorio y asigna la fecha de registro actual.
     * </p>
     *
     * @param nombre    Nombre del cliente.
     * @param correo    Correo electrónico del cliente.
     * @param telefono  Teléfono de contacto del cliente.
     * @param direccion Dirección física del cliente.
     * @param rut       RUT del cliente.
     */
    public Cliente(String nombre, String correo, String telefono, Direccion direccion, Rut rut) {
        super(nombre, correo, telefono, direccion, rut);
        Random random = new Random();
        LocalDate today = LocalDate.now();

        this.id = random.nextInt(1_000_000);
        this.fechaRegistro = today;
    }

    /** Getters */

    public int getId() {
        return id;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Devuelve una representación textual del cliente,
     * incluyendo sus datos personales, dirección, RUT, fecha de registro e identificador de sistema.
     *
     * @return Cadena con la información del cliente.
     */
    @Override
    public String toString() {
        return "Cliente: " + getNombre() + "\n" +
                "Correo: " + getCorreo() + "\n" +
                "Teléfono: " + getTelefono() + "\n" +
                "Dirección: " + getDireccion() + "\n" +
                "RUT: " + getRut() + "\n" +
                "Fecha registro: " + fechaRegistro + "\n" +
                "ID: " + id + "\n";
    }
}
