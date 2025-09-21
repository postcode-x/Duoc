package cl.ignacioaraya.teatroentradas.model;

import java.util.List;

public class Boleta {

    // Numero unico de la boleta
    private final int numero;

    // Lista de asientos asociados a esta boleta
    private final List<Asiento> asientos;

    // Constructor
    public Boleta(int numero, List<Asiento> asientos) {
        this.numero = numero;
        this.asientos = asientos;
    }

    // Calcula el total de la boleta sumando el precio de todos los asientos
    public double getTotal() {
        double total = 0;
        for (Asiento asiento : asientos) {
            total += asiento.getPrecio();
        }
        return total;
    }

    // Obtiene el numero de la boleta
    public int getNumeroBoleta() {
        return numero;
    }

    // Obtiene la lista de asientos de la boleta
    public List<Asiento> getAsientos() {
        return asientos;
    }

}
