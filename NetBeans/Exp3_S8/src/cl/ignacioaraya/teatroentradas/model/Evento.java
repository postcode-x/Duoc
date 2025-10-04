package cl.ignacioaraya.teatroentradas.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {

    // ID único del evento
    private final int id;

    // Nombre del evento
    private final String nombre;

    // Fecha del evento
    private final Date fecha;

    // Lista de ventas asociadas al evento
    private List<Venta> ventas = new ArrayList<>();

    // Constructor
    public Evento(int id, String nombre, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }


    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    // Método para agregar una venta al evento
    public void agregarVenta(Venta venta) {
        if (venta != null) {
            ventas.add(venta);
        }
    }
    
    // Método para eliminar una venta específica del evento
    public boolean eliminarVenta(int ventaId) {
        return ventas.removeIf(v -> v.getId() == ventaId);
    }

    // Método para mostrar información del evento
    @Override
    public String toString() {
        return "Evento { " +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", ventas=" + ventas.size() +
                " }";
    }
}
