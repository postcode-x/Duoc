package cl.ignacioaraya.teatroentradas.model;

import java.util.List;

public class Boleta {

    // Numero unico de la boleta
    private final int numero;

    // Lista de asientos asociados a esta boleta
    private final List<Asiento> asientos;

    // Precio sin descuento (suma de precios de los asientos)
    private final double precio;

    // Descuento aplicado en porcentaje
    private final int descuento;

    // Constructor inicializa boleta con numero, lista de asientos, precio y descuento
    public Boleta(int numero, List<Asiento> asientos, double precio, int descuento) {
        this.numero = numero;
        this.asientos = asientos;
        this.precio = precio;
        this.descuento = descuento;
    }

    // Calcula el total de la boleta aplicando el descuento correspondiente
    public double getTotal() {
        return precio * (1 - descuento / 100.0);
    }

    // Getters
    // Obtiene el numero de la boleta
    public int getNumero() {
        return numero;
    }

    // Obtiene el precio sin descuento
    public double getPrecio() {
        return precio;
    }

    // Obtiene el descuento de la boleta
    public int getDescuento() {
        return descuento;
    }

    // Obtiene la lista de asientos de la boleta
    public List<Asiento> getAsientos() {
        return asientos;
    }

}
