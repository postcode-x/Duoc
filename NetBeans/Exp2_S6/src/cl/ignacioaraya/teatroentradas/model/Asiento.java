package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Asiento {
    
    private final int numero;
    private final AppConfig.Zona zona;    
    private final int fila;
    private final int columna;
    private AppConfig.Estado estado;

    public Asiento(int numero, AppConfig.Zona zona, int fila, int columna) {
        this.numero = numero;
        this.zona = zona;
        this.fila = fila;
        this.columna = columna;
        this.estado = AppConfig.Estado.DISPONIBLE;
    }
    
    public String mostrar() {
        return numero + ") " + zona.nombre() + fila + "-" + columna + " (" + estado.obtenerNombre() + ")";
    }
    
    public String mostrarItemAsientoBoleta(){
        return "Asiento # " + numero + " | Ubicacion: " + zona.nombre() + fila + "-" + columna + " | Precio: $" + String.valueOf(Math.round(zona.precio()));
    }
    
    public void setDisponible() {
        estado = AppConfig.Estado.DISPONIBLE;
    }
    
    public void setReservado() {
        estado = AppConfig.Estado.RESERVADO;
    }
    
    public void setVendido() {
        estado = AppConfig.Estado.VENDIDO;
    }
    
    public AppConfig.Estado getEstado() {
        return estado;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public double getPrecio() {
        return zona.precio();
    }
    
    public AppConfig.Zona getZona(){
        return zona;
    }   
    
    public int getFila(){
        return fila;
    }
    
    public int getColumna(){
        return columna;
    }

}