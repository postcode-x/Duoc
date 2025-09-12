package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import java.util.ArrayList;
import java.util.List;
import cl.ignacioaraya.teatroentradas.model.Entrada;

public class VentaService {
    private final List<Entrada> entradasVendidas = new ArrayList<>();
    private final List<Entrada> canasta = new ArrayList<>();
    private boolean promocionA = true;
    private boolean promocionB = true;

    public void agregarEntrada(AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente, int precio) {
        canasta.add(new Entrada(canasta.size() + 1, ubicacion, tipoCliente, precio));
    }

    public int calcularPrecioEntrada(AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente) {
        int precioBase;
        int descuento = 0;
        
        precioBase = AppConstants.PRECIO_BASE_GENERAL;

        if (ubicacion == AppConstants.Ubicacion.PLATEA) {
            precioBase = AppConstants.PRECIO_BASE_PLATEA;
        } else if (ubicacion == AppConstants.Ubicacion.VIP){
            precioBase = AppConstants.PRECIO_BASE_VIP;
        }

        if (tipoCliente == AppConstants.TipoCliente.ESTUDIANTE) {
            descuento = AppConstants.DESCUENTO_ESTUDIANTE;
        } else if (tipoCliente == AppConstants.TipoCliente.ADULTO_MAYOR) {
            descuento = AppConstants.DESCUENTO_ADULTO_MAYOR;
        }

        return Math.round(precioBase - precioBase * descuento / 100);
    }

    public int calcularTotalConDescuento() {
        int total = 0;
        for (Entrada entrada : canasta) {
            total += entrada.getPrecio();
        }
        
        int cantidad = canasta.size();

        int descuentoExtra = calcularDescuentoPorCantidad(cantidad);

        if (descuentoExtra > 0) {
            total = Math.round(total - (total * descuentoExtra / 100.0f));
        }

        return total;
    }
    
    private int calcularDescuentoPorCantidad(int cantidad) {
        if (cantidad == AppConstants.CANTIDAD_MINIMA_PARA_DESCUENTO_2 && promocionA) return AppConstants.DESCUENTO_POR_2_ENTRADAS;
        if (cantidad >= AppConstants.CANTIDAD_MINIMA_PARA_DESCUENTO_3_O_MAS && promocionB) return AppConstants.DESCUENTO_POR_3_O_MAS_ENTRADAS;
        return 0;
    }

    public void confirmarCompra() {
        entradasVendidas.addAll(canasta);
        canasta.clear();
    }

    public void cancelarCompra() {
        canasta.clear();
    }
    
    public void switchPromocionA(){
        promocionA = !promocionA;
    }
    
    public void switchPromocionB(){
        promocionB = !promocionB;
    }

    public List<Entrada> getEntradasVendidas() { return entradasVendidas; }
    public List<Entrada> getCanasta() { return canasta; }
    public boolean getPromocionA() { return promocionA; }
    public boolean getPromocionB() { return promocionB; }
}