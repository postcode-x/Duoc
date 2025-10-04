package cl.ignacioaraya.teatroentradas.model;

import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Descuento {

    // Tipo de cliente al que aplica
    private AppConfig.TipoCliente tipoCliente;

    // Porcentaje de descuento en formato decimal (0.10 = 10%)
    private double porcentaje;
    
    // Status indica si está habilitado el descuento o no
    private boolean estado = true;

    // Constructor
    public Descuento(AppConfig.TipoCliente tipoCliente, double porcentaje) {
        this.tipoCliente = tipoCliente;
        this.porcentaje = porcentaje;
    }

    // Getters y setters
    public AppConfig.TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(AppConfig.TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    public void setEstado(boolean estado){
        this.estado = estado;
    }
    
    public boolean getEstado(){
        return estado;
    }

    // Método para mostrar información del descuento
    @Override
    public String toString() {
        return " Descuento { " +
                "tipoCliente: " + tipoCliente.obtenerNombre() +
                ", porcentaje: " + (porcentaje * 100) + "%" +
                " }";
    }
}
