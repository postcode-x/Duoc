package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import java.util.ArrayList;
import java.util.List;
import cl.ignacioaraya.teatroentradas.model.Entrada;

public class VentaService {
    // Lista global con todas las entradas vendidas (persisten después de confirmar)
    private static final List<Entrada> entradasVendidas = new ArrayList<>();
    // Canasta temporal de entradas (compra en curso, antes de confirmar)
    private final List<Entrada> canasta = new ArrayList<>();
    // Flags de promociones activas/inactivas
    private static boolean promocionA = true;
    private static boolean promocionB = true;
    // Contador incremental para asignar número único a cada entrada
    private static int contadorEntradas = 0;

    // Agregar nueva entrada a la canasta
    public void agregarEntrada(AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente, int precio) {
        contadorEntradas ++;
        canasta.add(new Entrada(contadorEntradas, ubicacion, tipoCliente, precio));
    }

    // Calcula el precio de una entrada según ubicación y tipo de cliente (con descuento individual)
    public int calcularPrecioEntrada(AppConstants.Ubicacion ubicacion, AppConstants.TipoCliente tipoCliente) {
        int precioBase;
        int descuento = 0;
        
        // Precio según ubicación
        precioBase = AppConstants.PRECIO_BASE_GENERAL;
        if (ubicacion == AppConstants.Ubicacion.PLATEA) {
            precioBase = AppConstants.PRECIO_BASE_PLATEA;
        } else if (ubicacion == AppConstants.Ubicacion.VIP){
            precioBase = AppConstants.PRECIO_BASE_VIP;
        }

        // Descuento según tipo de cliente
        if (tipoCliente == AppConstants.TipoCliente.ESTUDIANTE) {
            descuento = AppConstants.DESCUENTO_ESTUDIANTE;
        } else if (tipoCliente == AppConstants.TipoCliente.ADULTO_MAYOR) {
            descuento = AppConstants.DESCUENTO_ADULTO_MAYOR;
        }

        return Math.round(precioBase * (1 -  descuento / 100.0f));
    }

    // Calcula el total de la canasta, aplicando descuentos por cantidad si corresponden
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
    
    // Aplica descuento adicional si se cumplen condiciones de cantidad y promoción
    private int calcularDescuentoPorCantidad(int cantidad) {
        if (cantidad == AppConstants.CANTIDAD_MINIMA_PARA_DESCUENTO_2 && promocionA) 
            return AppConstants.DESCUENTO_POR_2_ENTRADAS;
        if (cantidad >= AppConstants.CANTIDAD_MINIMA_PARA_DESCUENTO_3_O_MAS && promocionB) 
            return AppConstants.DESCUENTO_POR_3_O_MAS_ENTRADAS;
        return 0;
    }

    // Confirma la compra: mueve entradas de canasta a vendidas
    public void confirmarCompra() {
        entradasVendidas.addAll(canasta);
        canasta.clear();
    }

    // Cancela la compra: vacía la canasta sin guardar
    public void cancelarCompra() {
        canasta.clear();
    }
    
    // Activa/desactiva la promoción A
    public void switchPromocionA(){
        promocionA = !promocionA;
    }
    
    // Activa/desactiva la promoción B
    public void switchPromocionB(){
        promocionB = !promocionB;
    }

    // Getters
    public List<Entrada> getCanasta() { return canasta; }
    public boolean getPromocionA() { return promocionA; }
    public boolean getPromocionB() { return promocionB; }
    
    // Busca todas las entradas vendidas por ubicación
    public List<Entrada> getEntradasVendidasPorUbicacion(AppConstants.Ubicacion ubicacion) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getUbicacion() == ubicacion) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }

    // Busca todas las entradas vendidas por tipo de cliente
    public List<Entrada> getEntradasVendidasPorTipoCliente(AppConstants.TipoCliente tipoCliente) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getTipoCliente() == tipoCliente) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }
    
    // Busca una entrada vendida por número único
    public String getEntradaVendidaPorNumero(int numero) {
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getNumero() == numero) {
                return entrada.mostrar();
            }
        }
        return "\nSin resultados.";
    }
    
    // Elimina una entrada vendida por número
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
