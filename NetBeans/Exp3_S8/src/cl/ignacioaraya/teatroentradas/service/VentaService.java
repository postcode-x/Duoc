package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.data.DataStore;
import cl.ignacioaraya.teatroentradas.model.*;
import cl.ignacioaraya.teatroentradas.config.AppConfig;
import java.util.Date;

public class VentaService {
    
    // Contador estático de ids de venta, se incrementa siempre para evitar
    // que dos ventas tengan el mismo id.
    private static int idVenta = 1;
    
    // Vende una entrada a un cliente para un evento específico
    public Venta venderEntrada(Cliente cliente, Evento evento) {
        // Buscar primer asiento disponible
        Asiento asientoDisponible = null;
        for (int i = 0; i < DataStore.asientoCount; i++) {
            if (DataStore.asientos[i].getEstado() == AppConfig.Estado.DISPONIBLE) {
                asientoDisponible = DataStore.asientos[i];
                break;
            }
        }

        if (asientoDisponible == null) {
            System.out.println("No hay asientos disponibles.");
            return null;
        }

        // Asignar asiento
        asientoDisponible.setVendido();

        // Calcular precio con descuento
        double precioBase = AppConfig.PRECIO_BASE;
        double precioFinal = aplicarDescuento(cliente, precioBase);

        // Crear objeto Venta
        //int idVenta = DataStore.ventaCount + 1; // ID secuencial
        Venta venta = new Venta(idVenta, cliente, asientoDisponible, precioFinal, new Date(), evento);
        idVenta++;
        
        // Guardar en DataStore
        DataStore.agregarVenta(venta);

        // Asociar venta al evento
        evento.agregarVenta(venta);

        return venta;
    }

    // Muestra el layout del teatro con los asientos por zona
    public String mostrar() {
         StringBuilder layoutTeatro = new StringBuilder();

        for (int i = 0; i < DataStore.asientoCount; i++) {
            Asiento asiento = DataStore.asientos[i];
            // Mostrar solo número y estado (Disponible/Vendido)
            String estado = asiento.getEstado() == AppConfig.Estado.DISPONIBLE ? "Disp." : "Vend.";
            layoutTeatro.append(String.format("%2d(%s)  ", asiento.getNumero(), estado));

            // Salto de línea al final de cada fila
            if (asiento.getColumna() == AppConfig.COLUMNAS_POR_FILA) {
                layoutTeatro.append("\n");
            }
        }

        layoutTeatro.append("\n");
        return layoutTeatro.toString();
    }
    
    //Aplica el descuento correspondiente según el tipo de cliente/
    private double aplicarDescuento(Cliente cliente, double precioBase) {
        for (Descuento d : DataStore.descuentos) {
            if (d.getTipoCliente() == cliente.getTipo() && d.getEstado()) {
                return precioBase * (1 - d.getPorcentaje());
            }
        }
        return precioBase; // Si no hay descuento, precio normal
    }
    
    public int getAsientosDisponibles(){
        int contador = 0;
        for (int i = 0; i < DataStore.asientos.length; i++) {
            if (DataStore.asientos[i] != null && DataStore.asientos[i].getEstado() == AppConfig.Estado.DISPONIBLE) {
                contador++;
            }
        }
        return contador;
    }

}
