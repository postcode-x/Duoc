package cl.salmontt.gestionpersonas.model;

/**
 * Representa a una persona dentro del sistema.
 * <p>
 * Contiene información básica de identificación y contacto,
 * además de información sobre su dirección física y RUT, representados
 * por los objetos {@link Direccion} y {@link Rut}.
 * </p>
 */
public class Persona {
    private String nombre;
    private String correo;
    private String telefono;
    private Direccion direccion;
    private Rut rut;

    /**
     * Constructor vacío
     */
    public Persona() {
    }

    /**
     * Constructor parametrizado.
     *
     * @param nombre    Nombre de la persona.
     * @param correo    Correo electrónico de la persona.
     * @param telefono  Teléfono de contacto de la persona.
     * @param direccion Dirección física de la persona.
     * @param rut       RUT de la persona.
     */
    public Persona(String nombre, String correo, String telefono, Direccion direccion, Rut rut) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rut = rut;
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

    public Rut getRut() { return rut; }

    /** Setters */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public void setRut(Rut rut) { this.rut = rut; }

    /**
     * Devuelve una representación textual de la persona,
     * incluyendo sus datos, dirección y RUT.
     *
     * @return Cadena con la información de la persona.
     */
    @Override
    public String toString() {
        return "Persona: " + nombre + "\n" +
                "Correo: " + correo + "\n" +
                "Teléfono: " + telefono + "\n" +
                "Dirección: " + direccion + "\n" +
                "RUT: " + rut;
    }
}
