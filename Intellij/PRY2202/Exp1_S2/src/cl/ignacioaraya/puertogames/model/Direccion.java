package cl.ignacioaraya.puertogames.model;

/**
 * Representa una dirección física asociada a un cliente.
 * <p>
 * Establece una relación de composición con la clase {@link Cliente}.
 * </p>
 */
public class Direccion {
    private String calle;
    private String ciudad;
    private String region;

    /**
     * Constructor vacío
     */
    public Direccion() {
    }

    /**
     * Constructor parametrizado.
     *
     * @param calle  Nombre de la calle y número.
     * @param ciudad Ciudad donde se ubica la dirección.
     * @param region Región o zona geográfica.
     */
    public Direccion(String calle, String ciudad, String region) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.region = region;
    }

    /** Getters */

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getRegion() {
        return region;
    }

    /** Setters */

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Devuelve una representación textual de la dirección.
     *
     * @return Cadena con calle, ciudad y región.
     */
    @Override
    public String toString() {
        return calle + ", " + ciudad + ", " + region;
    }
}
