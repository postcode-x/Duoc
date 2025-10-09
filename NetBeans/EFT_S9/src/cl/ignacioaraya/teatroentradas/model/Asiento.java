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

    // Constructor
    public Asiento(int numero, AppConfig.Zona zona, int fila, int columna) {
        this.numero = numero;
        this.zona = zona;
        this.fila = fila;
        this.columna = columna;
        this.estado = AppConfig.Estado.DISPONIBLE; // por defecto disponible
    }

    // Muestra el asiento en formato legible para menus
    public String mostrar() {
        return numero + ") " + zona.nombre() + " " + fila + "-" + columna;
    }

    // Muestra el asiento en formato reducido para vista de layout
    public String mostrarSimple() {
        return numero + (numero < 10 ? ".  " : ". ") + fila + "-" + columna + " [" + (getEstado() == AppConfig.Estado.DISPONIBLE ? "D" : getEstado() == AppConfig.Estado.SELECCIONADO ? "S" : "X") + "]";
    }

    // Muestra el asiento en formato para boleta
    public String mostrarItemAsientoBoleta() {
        return "Asiento # " + numero
                + " | Ubicacion: " + zona.nombre() + fila + "-" + columna
                + " | Precio: $" + Math.round(zona.precio());
    }

    // Cambia estado a disponible
    public void setDisponible() {
        estado = AppConfig.Estado.DISPONIBLE;
    }

    // Cambia estado a seleccionado
    public void setSeleccionado() {
        estado = AppConfig.Estado.SELECCIONADO;
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
