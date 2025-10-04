package cl.ignacioaraya.teatroentradas.model;

import java.util.Date;

public class Venta {

    // ID único de la venta
    private int id;

    // Cliente que realiza la compra
    private Cliente cliente;

    // Asiento asignado
    private Asiento asiento;

    // Precio final de la entrada (ya con descuento aplicado)
    private double precio;

    // Fecha de la venta
    private Date fecha;
    
    private Evento evento;

    // Constructor
    public Venta(int id, Cliente cliente, Asiento asiento, double precio, Date fecha, Evento evento) {
        this.id = id;
        this.cliente = cliente;
        this.asiento = asiento;
        this.precio = precio;
        this.fecha = fecha;
        this.evento = evento;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    // Representación legible de la venta
    @Override
    public String toString() {
        return "Venta { " +
                "id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", asiento=" + asiento.getNumero() + " (" + asiento.getFila() + "-" + asiento.getColumna() + ")" +
                ", precio=" + precio +
                ", fecha=" + fecha +
                " }";
    }
}
