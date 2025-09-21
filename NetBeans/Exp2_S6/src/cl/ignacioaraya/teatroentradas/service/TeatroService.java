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

    // Lista de todos los asientos del teatro
    private final List<Asiento> asientos = new ArrayList<>();

    // Lista de boletas emitidas
    private final List<Boleta> boletas = new ArrayList<>();

    // Lista de asientos que el usuario tiene en el carrito
    private final List<Asiento> carrito = new ArrayList<>();

    // Contadores para asientos y boletas
    private static int contadorAsientos = 0;
    private static int contadorBoletas = 0;
    private int asientosDisponibles = 0;
    private int asientosReservados = 0;
    private int asientosVendidos = 0;

    // Scheduler para manejar expiracion de reservas
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Mapea cada asiento con su tarea de expiracion
    private final Map<Asiento, ScheduledFuture<?>> expiraciones = new HashMap<>();

    // Constructor: genera todos los asientos segun configuracion
    public TeatroService() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            for (int fila = 1; fila <= AppConfig.FILAS_POR_ZONA; fila++) {
                for (int columna = 1; columna <= AppConfig.ASIENTOS_POR_FILA; columna++) {
                    contadorAsientos += 1;
                    asientos.add(new Asiento(contadorAsientos, zona, fila, columna));
                }
            }
        }
        
        // Todos los asientos empiezan disponibles
        asientosDisponibles = asientos.size();
    }

    // Muestra el layout del teatro con los asientos por zona
    public String mostrar() {
        StringBuilder layoutTeatro = new StringBuilder();

        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            layoutTeatro.append("Zona ").append(zona.nombre())
                         .append(" | $").append(Math.round(zona.precio()))
                         .append("\n");

            for (Asiento asiento : asientos) {
                if (asiento.getZona().equals(zona)) {
                    layoutTeatro.append(asiento.mostrar()).append("   ");
                    if (asiento.getColumna() == AppConfig.ASIENTOS_POR_FILA) {
                        layoutTeatro.append("\n");
                    }
                }
            }
            layoutTeatro.append("\n");
        }

        return layoutTeatro.toString();
    }

    // Programa la expiracion de una reserva
    private void programarExpiracion(Asiento asiento, long segundos) {
        // Cancelar expiracion anterior si existe
        ScheduledFuture<?> tareaAnterior = expiraciones.get(asiento);
        if (tareaAnterior != null && !tareaAnterior.isDone()) {
            tareaAnterior.cancel(false);
        }

        // Programar nueva expiracion
        ScheduledFuture<?> tarea = scheduler.schedule(() -> {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
                System.out.println("\n[INFO] Expiro la reserva del asiento #" + asiento.getNumero());
            }
        }, segundos, TimeUnit.SECONDS);

        expiraciones.put(asiento, tarea);
    }

    // Reserva un asiento y programa su expiracion
    public void reservarAsientoConExpiracion(Asiento asiento) {
        asiento.setReservado();
        programarExpiracion(asiento, AppConfig.TIEMPO_DE_RESERVA_EN_SEGUNDOS);
    }

    // Verifica si hay asientos reservados
    public boolean hayAsientosReservados() {
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                return true;
            }
        }
        return false;
    }

    // Muestra los asientos en el carrito
    public String mostrarAsientosCarrito() {
        if (carrito.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (Asiento asiento : carrito) {
            sb.append(asiento.mostrar()).append(" | ");
        }
        sb.setLength(sb.length() - 3); // eliminar ultimo " | "

        return sb.toString();
    }

    // Calcula total de los asientos en el carrito
    public double calcularTotalCarrito() {
        double totalCheckout = 0;
        for (Asiento asiento : carrito) {
            totalCheckout += asiento.getPrecio();
        }
        return totalCheckout;
    }

    // Cancela la expiracion de un asiento
    private void cancelarExpiracion(Asiento asiento) {
        ScheduledFuture<?> tarea = expiraciones.remove(asiento);
        if (tarea != null && !tarea.isDone()) {
            tarea.cancel(false);
        }
    }

    // Marca los asientos del carrito como vendidos y crea boleta
    public int marcarComoVendidos() {
        for (Asiento asiento : carrito) {
            asiento.setVendido();
            cancelarExpiracion(asiento);
        }
        contadorBoletas++;
        boletas.add(new Boleta(contadorBoletas, new ArrayList<>(carrito)));
        carrito.clear();
        return contadorBoletas;
    }

    // Marca los asientos del carrito como disponibles si no se compro
    public void marcarComoDisponibles() {
        for (Asiento asiento : carrito) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
                cancelarExpiracion(asiento);
            }
        }
        carrito.clear();
    }

    // Busca una boleta por numero
    public Boleta buscarBoletaPorNumero(int numero) {
        for (Boleta boleta : boletas) {
            if (boleta.getNumeroBoleta() == numero) return boleta;
        }
        return null;
    }

    // Getters
    public List<Asiento> getAsientos() {
        return asientos;
    }

    public List<Asiento> getCarrito() {
        return carrito;
    }

    public List<Boleta> getBoletas() {
        return boletas;
    }
    
    public int getAsientosDisponibles() {
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                contador++;
            }
        }
        asientosDisponibles = contador;
        return asientosDisponibles;
    }

    public int getAsientosReservados() {
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                contador++;
            }
        }
        asientosReservados = contador;
        return asientosReservados;
    }

    public int getAsientosVendidos() {
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.VENDIDO) {
                contador++;
            }
        }
        asientosVendidos = contador;
        return asientosVendidos;
    }
    
    // Detener scheduler al cerrar aplicacion
    public void shutdown() {
        scheduler.shutdown();
    }

}
