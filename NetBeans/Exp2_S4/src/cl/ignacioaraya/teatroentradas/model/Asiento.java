package cl.ignacioaraya.teatroentradas.model;

public class Asiento {
    String zona;      
    int fila;          
    int columna;
    int numero;
    public boolean disponible;
    public static final int PRECIO = 20000;
    public int descuento = 0;

    // Constructor
    Asiento(String zona, int fila, int columna, int numero) {
        this.zona = zona;
        this.fila = fila;
        this.columna = columna;
        this.numero = numero;
        this.disponible = true;
    }
    
    public void seleccionar(){
        this.disponible = false;
    }
    
    public void setDescuento(int descuento){
        this.descuento = descuento;
    }

    // Para mostrar asiento individual
    public String mostrar() {
        return numero + ") " + zona + fila + "-" + columna + (disponible ? " (Disponible)" : " (Ocupado)");
    }
    
    // Para mostrar asiento individual en checkout
    public String mostrarCheckout() {
        return "Numero " + numero + " | " + zona + fila + "-" + columna;
    }
}
