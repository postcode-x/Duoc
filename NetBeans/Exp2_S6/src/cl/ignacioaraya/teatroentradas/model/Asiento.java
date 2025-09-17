package cl.ignacioaraya.teatroentradas.model;

public class Asiento {
    
    private int numero;
    private Estado estado;
    private double precio;
    public enum Estado { DISPONIBLE, RESERVADO, VENDIDO }

    public Asiento() {
    }

    // Getters y setters...
}