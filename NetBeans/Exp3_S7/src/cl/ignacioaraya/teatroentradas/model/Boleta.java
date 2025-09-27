package cl.ignacioaraya.teatroentradas.model;

import java.util.List;

public class Boleta {

    // Numero unico de la boleta
    private final int numero;

    // Lista de entradas asociados a esta boleta
    private final List<Entrada> entradas;

    // Constructor
    public Boleta(int numero, List<Entrada> entradas) {
        this.numero = numero;
        this.entradas = entradas;
    }

    // Calcula el total de la boleta sumando el precio de todos los asientos
    public double getTotal() {
        double total = 0;
        for (Entrada entrada : entradas) {
            total += entrada.getPrecio();
        }
        return total;
    }

    // Obtiene el numero de la boleta
    public int getNumeroBoleta() {
        return numero;
    }

    // Obtiene la lista de entradas de la boleta
    public List<Entrada> getEntradas() {
        return entradas;
    }

}
