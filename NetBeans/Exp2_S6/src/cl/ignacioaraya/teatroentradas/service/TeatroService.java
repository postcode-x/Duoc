package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TeatroService {
    
    private final List<Asiento> asientos = new ArrayList<>();
    private static int contador = 0;
    
    private static int asientosDisponibles;
    private static int asientosReservados;
    private static int asientosVendidos;
    private static Boleta ultimaBoleta;
    
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public TeatroService() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            for (int fila = 1; fila <= AppConfig.FILAS_POR_ZONA; fila++) {
                for (int columna = 1; columna <= AppConfig.ASIENTOS_POR_FILA; columna++) {
                    contador += 1;
                    asientos.add(new Asiento(contador, zona, fila, columna));
                }
            }
        }
    }
    
    private void mostrarAsientos() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            System.out.println("Zona " + zona.nombre() + " | $" + Math.round(zona.precio()));
            for (Asiento asiento : asientos) {
                if (asiento.zona.equals(zona)) {
                    System.out.print(asiento.mostrar() + "   ");
                    if (asiento.columna == AppConfig.ASIENTOS_POR_FILA) System.out.println();
                }
            }
            System.out.println();
        }
    }
    
    private void programarExpiracion(Asiento asiento, long segundos) {
        scheduler.schedule(() -> {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
                System.out.println("\nLa reserva del asiento #" + asiento.getNumero() + " ha expirado.");
            }
        }, segundos, TimeUnit.SECONDS);
    }

    public void reservarAsiento(Scanner sc) {
        boolean asientoSeleccionado = false;
        do{
            System.out.println("\n=== RESERVAR ASIENTO ===");
            this.mostrarAsientos();
            
            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");
            
            if (opcion < 1 || opcion > asientos.size()) {
                System.out.println("Numero de asiento no valido.");
            }else{
                Asiento asiento = asientos.get(opcion - 1);
                if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {  
                    asiento.setReservado();
                    System.out.println("\nAsiento reservado con exito.");
                    programarExpiracion(asiento, AppConfig.TIEMPO_DE_RESERVA_EN_SEGUNDOS);
                    asientoSeleccionado = true;
                } else {
                    System.out.println("\nEl asiento seleccionado no esta disponible.");
                }
            }
            
        }while(!asientoSeleccionado);
        
    }

    public void modificarReserva() {
        System.out.println("\n=== MODIFICAR RESERVA ===");
    }

    public void comprarEntrada() {
        System.out.println("\n=== COMPRAR ENTRADA ===");
    }

    public void imprimirBoleta() {
        System.out.println("\n=== IMPRIMIR BOLETA ===");
    }
}

