package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Asiento {

    // Numero unico del asiento
    private final int numero;

    // Ubicacion en la sala
    private final int fila;
    private final int columna;

    // Estado actual del asiento (disponible, vendido)
    private AppConfig.Estado estado;

    // Constructor
    public Asiento(int numero, int fila, int columna) {
        this.numero = numero;
        this.fila = fila;
        this.columna = columna;
        this.estado = AppConfig.Estado.DISPONIBLE; // por defecto disponible
    }

    // Muestra el asiento en formato legible para menus
    public String mostrar() {
        return numero + ") " + fila + "-" + columna + " (" + estado.obtenerNombre() + ")";
    }

    // Muestra el asiento en formato para boleta
    public String mostrarItemAsientoBoleta() {
        return "Asiento # " + numero + " | Ubicacion: " + fila + "-" + columna + " | Precio: $";
    }

    // Cambia estado a disponible
    public void setDisponible() {
        estado = AppConfig.Estado.DISPONIBLE;
    }

    // Cambia estado a vendido
    public void setVendido() {
        estado = AppConfig.Estado.VENDIDO;
    }

    // Obtiene estado actual
    public AppConfig.Estado getEstado() {
        return estado;
    }

    // Obtiene numero de asiento
    public int getNumero() {
        return numero;
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
