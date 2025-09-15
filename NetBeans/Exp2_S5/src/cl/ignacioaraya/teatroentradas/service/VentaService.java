package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import java.util.ArrayList;
import java.util.List;
import cl.ignacioaraya.teatroentradas.model.Entrada;

public class VentaService {
    private static final List<Entrada> entradasVendidas = new ArrayList<>();
    private final List<Entrada> canasta = new ArrayList<>();
    private static boolean promocionA = true;
    private static boolean promocionB = true;
    private static int contadorEntradas = 0;

    public void agregarEntrada(AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente, int precio) {
        contadorEntradas ++;
        canasta.add(new Entrada(contadorEntradas, ubicacion, tipoCliente, precio));
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

        return Math.round(precioBase * (1 -  descuento / 100.0f));
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

    public List<Entrada> getCanasta() { return canasta; }
    public boolean getPromocionA() { return promocionA; }
    public boolean getPromocionB() { return promocionB; }
    
    public List<Entrada> getEntradasVendidasPorUbicacion(AppConstants.Ubicacion ubicacion) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getUbicacion() == ubicacion) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }

    public List<Entrada> getEntradasVendidasPorTipoCliente(AppConstants.TipoCliente tipoCliente) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getTipoCliente() == tipoCliente) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }
    
    public String getEntradaVendidaPorNumero(int numero) {
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getNumero() == numero) {
                return entrada.mostrar();
            }
        }
        return "\nSin resultados.";
    }
    
    public boolean eliminarEntradaPorNumero(int numero) {
        Entrada encontrada = null;

        for (Entrada entrada : entradasVendidas) {
            if (entrada.getNumero() == numero) {
                encontrada = entrada;
                break;
            }
        }

        if (encontrada != null) {
            entradasVendidas.remove(encontrada);
            return true; // eliminada con éxito
        } else {
            return false; // no se encontró
        }
    }
}