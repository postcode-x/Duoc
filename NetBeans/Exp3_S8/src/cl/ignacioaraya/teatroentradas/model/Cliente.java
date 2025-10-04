package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig.TipoCliente;

public class Cliente {
    
    // ID único del cliente
    private int id;
    
    // Nombre del cliente
    private String nombre;
    
    // Tipo de cliente (NORMAL, ESTUDIANTE, ADULTO MAYOR)
    private TipoCliente tipo;

    // Constructor
    public Cliente(int id, String nombre, TipoCliente tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    // Método para mostrar información del cliente
    @Override
    public String toString() {
        return "Cliente { " +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                " }";
    }
}
