package cl.ignacioaraya.teatroentradas.model;

import java.util.List;

public class Boleta {
    
    private final int numero;
    private final List<Asiento> asientos;
    
    public Boleta(int numero, List<Asiento> asientos) {
        this.numero = numero;
        this.asientos = asientos;
    }
    
    public double getTotal(){
        double total = 0;
        for (Asiento asiento : asientos) {
            total += asiento.getPrecio();
        }
        return total;
    }
    
    public int getNumeroBoleta(){
        return numero;
    }
    
    public List<Asiento> getAsientos() {
        return asientos;
    }
    
    
}
