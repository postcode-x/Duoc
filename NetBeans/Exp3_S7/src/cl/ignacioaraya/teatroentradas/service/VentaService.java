package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import java.util.ArrayList;
import java.util.List;
import cl.ignacioaraya.teatroentradas.model.Entrada;

public class VentaService {
    // Lista global con todas las entradas vendidas (persisten después de confirmar)
    private static final List<Entrada> entradasVendidas = new ArrayList<>();
    // Canasta temporal de entradas (compra en curso, antes de confirmar)
    private final List<Entrada> canasta = new ArrayList<>();
    // Contador incremental para asignar número único a cada entrada
    private static int contadorEntradas = 0;
    // Ingresos totales
    private static int ingresosTotales = 0;

    // Agregar nueva entrada a la canasta
    public void agregarEntrada(AppConfig.Ubicacion ubicacion, AppConfig.TipoCliente tipoCliente, int precio) {
        contadorEntradas ++;
        canasta.add(new Entrada(contadorEntradas, ubicacion, tipoCliente, precio));
    }

    // Calcula el precio de una entrada según ubicación y tipo de cliente (con descuento individual)
    public int calcularPrecioEntrada(AppConfig.Ubicacion ubicacion, AppConfig.TipoCliente tipoCliente) {
        int precioBase = AppConfig.PRECIO_BASE_GENERAL;
        int descuento = 0;
        
        if (ubicacion == AppConfig.Ubicacion.PLATEA) {
            precioBase = AppConfig.PRECIO_BASE_PLATEA;
        } else if (ubicacion == AppConfig.Ubicacion.VIP){
            precioBase = AppConfig.PRECIO_BASE_VIP;
        }

        if (tipoCliente == AppConfig.TipoCliente.ESTUDIANTE) {
            descuento = AppConfig.DESCUENTO_ESTUDIANTE;
        } else if (tipoCliente == AppConfig.TipoCliente.ADULTO_MAYOR) {
            descuento = AppConfig.DESCUENTO_ADULTO_MAYOR;
        }

        return Math.round(precioBase * (1 -  descuento / 100.0f));
    }

    // Calcula el total de la canasta
    public int calcularTotal() {
        int total = 0;
        for (Entrada entrada : canasta) {
            total += entrada.getPrecio();
        }

        return total;
    }

    // Confirma la compra: mueve entradas de canasta a vendidas y actualiza ingresos totales
    public void confirmarCompra() {
        entradasVendidas.addAll(canasta);
        ingresosTotales += canasta.get(0).getPrecio();
        canasta.clear();
    }

    // Cancela la compra: vacía la canasta sin guardar
    public void cancelarCompra() {
        contadorEntradas--;
        canasta.clear();
    }
    
    // Busca una entrada por numero
    public Entrada buscarEntradaPorNumero(int numero) {
        for (Entrada entrada : entradasVendidas) {
            if (entrada.getNumero() == numero) return entrada;
        }
        return null;
    }

    // Getters
    public List<Entrada> getCanasta() { return canasta; }
    
    public List<Entrada> getEntradasVendidas() { return entradasVendidas; }
    
    public int getAsientosDisponibles(){
        return AppConfig.CAPACIDAD - entradasVendidas.size();
    }
    
    public int getIngresosTotales(){
        return ingresosTotales; 
    }
    
    
}
