package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Asiento {

    // Numero unico del asiento
    private final int numero;

    // Zona a la que pertenece el asiento
    private final AppConfig.Zona zona;

    // Ubicacion en la sala
    private final int fila;
    private final int columna;

    // Estado actual del asiento (disponible, vendido)
    private AppConfig.Estado estado;
    
    // Descuento actual del asiento
    private int descuento;

    // Constructor
    public Asiento(int numero, AppConfig.Zona zona, int fila, int columna) {
        this.numero = numero;
        this.zona = zona;
        this.fila = fila;
        this.columna = columna;
        this.estado = AppConfig.Estado.DISPONIBLE; // por defecto disponible
        this.descuento = 0; // por defecto 0%
    }

    // Muestra el asiento en formato legible para menus
    public String mostrar() {
        return numero + ") " + zona.nombre() + " " + fila + "-" + columna;
    }
    
    // Muestra el asiento en formato reducido para vista de layout
    public String mostrarSimple() {
        return numero + (numero < 10 ? ".  ": ". ") + fila + "-" + columna + " [" + (getEstado() == AppConfig.Estado.DISPONIBLE ? "D" : "X") + "]" ;
    }

    // Muestra el asiento en formato para boleta
    public String mostrarItemAsientoBoleta() {
        return "Asiento # " + numero +
               " | Ubicacion: " + zona.nombre() + fila + "-" + columna +
               " | Precio: $" + Math.round(zona.precio());
    }

    // Cambia estado a disponible
    public void setDisponible() {
        estado = AppConfig.Estado.DISPONIBLE;
    }
    
    // Cambia estado a pendiente
    public void setPendiente() {
        estado = AppConfig.Estado.PENDIENTE;
    }

    // Cambia estado a vendido
    public void setVendido() {
        estado = AppConfig.Estado.VENDIDO;
    }
    
    // Setea el descuento
    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    // Obtiene estado actual
    public AppConfig.Estado getEstado() {
        return estado;
    }
    
    // Obtiene descuento actual
    public int getDescuento(){
        return descuento;
    }
    
    // Obtiene texto descuento actual
    public String getDescuentoTexto(){
        return descuento == AppConfig.DESCUENTO_NINOS 
                ? "Descuento Niños" 
                : descuento == AppConfig.DESCUENTO_ESTUDIANTE 
                    ? "Descuento Estudiante" :
                    descuento == AppConfig.DESCUENTO_ADULTO_MAYOR 
                        ? "Descuento Adulto Mayor" :
                            descuento == AppConfig.DESCUENTO_MUJER 
                            ? "Descuento Mujer" : "Sin descuento";
    }
    
    // Obtiene numero de asiento
    public int getNumero() {
        return numero;
    }

    // Obtiene precio base segun zona
    public double getPrecio() {
        return zona.precio();
    }

    // Obtiene zona
    public AppConfig.Zona getZona() {
        return zona;
    }

    // Obtiene fila
    public int getFila() {
        return fila;
    }

    // Obtiene columna
    public int getColumna() {
        return columna;
    }

}
