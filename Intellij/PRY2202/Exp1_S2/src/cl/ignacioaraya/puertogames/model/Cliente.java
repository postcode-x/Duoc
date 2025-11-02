package cl.ignacioaraya.puertogames.model;

/**
 * Representa a un cliente dentro del sistema.
 * <p>
 * Contiene información básica de identificación y contacto,
 * además de información sobre su dirección física representada
 * por un objeto {@link Direccion}.
 * </p>
 */
public class Cliente {
    private String nombre;
    private String correo;
    private String telefono;
    private Direccion direccion;

    /**
     * Constructor vacío
     */
    public Cliente() {
    }

    /**
     * Constructor parametrizado.
     *
     * @param nombre    Nombre del cliente.
     * @param correo    Correo electrónico del cliente.
     * @param telefono  Teléfono de contacto del cliente.
     * @param direccion Dirección física del cliente.
     */
    public Cliente(String nombre, String correo, String telefono, Direccion direccion) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    /** Getters */

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    /** Setters */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve una representación textual del cliente,
     * incluyendo sus datos y dirección.
     *
     * @return Cadena con la información del cliente.
     */
    @Override
    public String toString() {
        return "Cliente: " + nombre + "\n" +
                "Correo: " + correo + "\n" +
                "Teléfono: " + telefono + "\n" +
                "Dirección: " + direccion;
    }
}
