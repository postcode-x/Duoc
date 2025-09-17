package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Asiento {
    
    private final int numero;
    public AppConfig.Zona zona;    
    public int fila;
    public int columna;
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
    
    public void setDisponible() {
        this.estado = AppConfig.Estado.DISPONIBLE;
    }
    
    public void setReservado() {
        this.estado = AppConfig.Estado.RESERVADO;
    }
    
    public void setVendido() {
        this.estado = AppConfig.Estado.VENDIDO;
    }
    
    public AppConfig.Estado getEstado() {
        return this.estado;
    }
    
    public int getNumero() {
        return this.numero;
    }

}