package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TeatroService {

    private final List<Asiento> asientos = new ArrayList<>();
    private final List<Boleta> boletas = new ArrayList<>();
    private final List<Asiento> carrito = new ArrayList<>();
    private static int contadorAsientos = 0;
    private static int contadorBoletas = 0;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<Asiento, ScheduledFuture<?>> expiraciones = new HashMap<>();

    public TeatroService() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            for (int fila = 1; fila <= AppConfig.FILAS_POR_ZONA; fila++) {
                for (int columna = 1; columna <= AppConfig.ASIENTOS_POR_FILA; columna++) {
                    contadorAsientos += 1;
                    asientos.add(new Asiento(contadorAsientos, zona, fila, columna));
                }
            }
        }
    }

    public String mostrar() {
        String layoutTeatro = "";
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            layoutTeatro += ("Zona " + zona.nombre() + " | $" + Math.round(zona.precio())) + "\n";
            for (Asiento asiento : asientos) {
                if (asiento.getZona().equals(zona)) {
                    layoutTeatro += (asiento.mostrar() + "   ");
                    if (asiento.getColumna() == AppConfig.ASIENTOS_POR_FILA) {
                        layoutTeatro += "\n";
                    }
                }
            }
            layoutTeatro += "\n";
        }
        return layoutTeatro;
    }

    private void programarExpiracion(Asiento asiento, long segundos) {
        // Cancelar expiración anterior si existe
        ScheduledFuture<?> tareaAnterior = expiraciones.get(asiento);
        if (tareaAnterior != null && !tareaAnterior.isDone()) {
            tareaAnterior.cancel(false);
        }

        // Programar nueva expiración
        ScheduledFuture<?> tarea = scheduler.schedule(() -> {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
            }
        }, segundos, TimeUnit.SECONDS);

        expiraciones.put(asiento, tarea);
    }
    
    public void reservarAsientoConExpiracion(Asiento asiento) {
        asiento.setReservado();
        programarExpiracion(asiento, AppConfig.TIEMPO_DE_RESERVA_EN_SEGUNDOS);
    }

    public boolean hayAsientosReservados(){
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                return true;
            }
        }
        return false;
    }
    
    public String mostrarAsientosCarrito(){
        String listaAsientos = "";
        for (Asiento asiento : carrito) {
            listaAsientos += asiento.mostrar() + " | ";
        }
        if (listaAsientos.isEmpty()) return "";
        return listaAsientos.substring(0, listaAsientos.length() - 2);
    }
    
    public double calcularTotalCarrito(){
        double totalCheckout = 0;
        for (Asiento asiento : carrito) {
            totalCheckout += asiento.getPrecio();
        }
        return totalCheckout;
    }
    
    private void cancelarExpiracion(Asiento asiento) {
        ScheduledFuture<?> tarea = expiraciones.remove(asiento);
        if (tarea != null && !tarea.isDone()) {
            tarea.cancel(false);
        }
    }
    
    public int marcarComoVendidos(){
        for (Asiento asiento : carrito) {
            asiento.setVendido();
            cancelarExpiracion(asiento);
        }
        contadorBoletas ++;
        boletas.add(new Boleta(contadorBoletas, new ArrayList<>(carrito)));
        carrito.clear();
        return contadorBoletas;
    }
    
    public void marcarComoDisponibles(){
        for (Asiento asiento : carrito) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
            }
        }
        carrito.clear();
    }
    
    public Boleta buscarBoletaPorNumero(int numero) {
        for (Boleta boleta : boletas) {
            if (boleta.getNumeroBoleta() == numero) return boleta;
        }
        return null;
    }
    
    public List<Asiento> getAsientos(){
        return asientos;
    }
    
    public List<Asiento> getCarrito(){
        return carrito;
    }
    
    public List<Boleta> getBoletas(){
        return boletas;
    }
    
    public void shutdown() {
        scheduler.shutdown();
    }
}
