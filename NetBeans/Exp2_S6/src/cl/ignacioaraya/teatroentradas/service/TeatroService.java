package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TeatroService {

    private final List<Asiento> asientos = new ArrayList<>();
    private final List<Boleta> boletas = new ArrayList<>();
    private final List<Asiento> reservados = new ArrayList<>();
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

    private void mostrarAsientos() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            System.out.println("Zona " + zona.nombre() + " | $" + Math.round(zona.precio()));
            for (Asiento asiento : asientos) {
                if (asiento.getZona().equals(zona)) {
                    System.out.print(asiento.mostrar() + "   ");
                    if (asiento.getColumna() == AppConfig.ASIENTOS_POR_FILA) {
                        System.out.println();
                    }
                }
            }
            System.out.println();
        }
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

    public void reservarAsiento(Scanner sc) {
        boolean seguirReservando = false;
        do {
            System.out.println("\n=== RESERVAR ASIENTO ===");
            this.mostrarAsientos();

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");

            if (opcion < 1 || opcion > asientos.size()) {
                System.out.println("Numero de asiento no valido.");
            } else {
                Asiento asiento = asientos.get(opcion - 1);
                if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                    reservarAsientoConExpiracion(asiento);
                    System.out.println("\nAsiento reservado con exito.");
                } else {
                    System.out.println("\nEl asiento seleccionado no esta disponible.");
                }
            }
            
            seguirReservando = preguntaSeguirReservando(sc);

        } while (seguirReservando);

    }
    
    // Pregunta si el usuario desea seguir reservando
    private static boolean preguntaSeguirReservando(Scanner sc){
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, "Seguir reservando? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }

    public void modificarReserva(Scanner sc) {
        if (!hayAsientosReservados()) {
            System.out.println("\nNo existen asientos reservados actualmente.");
            return;
        }

        boolean modificacionFinalizada = false;

        do {
            System.out.println("\n=== MODIFICAR RESERVA ===");
            this.mostrarAsientos();

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento reservado a modificar (0 para volver): ");

            if (opcion == 0) {
                break; // salir del menú
            }
            if (opcion < 1 || opcion > asientos.size()) {
                System.out.println("Numero de asiento no valido.");
                continue;
            }

            Asiento asiento = asientos.get(opcion - 1);

            if (asiento.getEstado() != AppConfig.Estado.RESERVADO) {
                System.out.println("\nEse asiento no esta reservado actualmente.");
                continue;
            }

            System.out.println("\nQue desea hacer?");
            System.out.println("1) Cancelar la reserva");
            System.out.println("2) Cambiar por otro asiento");
            System.out.println("3) Volver");

            int accion = InputUtils.leerEntero(sc, "Seleccione una opcion: ");

            switch (accion) {
                case 1 -> {
                    asiento.setDisponible();
                    System.out.println("\nReserva cancelada. El asiento #" + asiento.getNumero() + " ahora esta disponible.");
                    modificacionFinalizada = true;
                }
                case 2 -> {
                    asiento.setDisponible();
                    System.out.println("\nSeleccione el nuevo asiento a reservar:");
                    this.mostrarAsientos();

                    int nuevo = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");
                    if (nuevo < 1 || nuevo > asientos.size()) {
                        System.out.println("Numero de asiento no valido.");
                        reservarAsientoConExpiracion(asiento); // restaurar reserva original
                        continue;
                    }

                    Asiento nuevoAsiento = asientos.get(nuevo - 1);
                    if (nuevoAsiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                        reservarAsientoConExpiracion(nuevoAsiento);
                        System.out.println("\nReserva cambiada con exito al asiento #" + nuevoAsiento.getNumero());
                        modificacionFinalizada = true;
                    } else {
                        System.out.println("\nEl asiento seleccionado no esta disponible.");
                        reservarAsientoConExpiracion(asiento); // restaurar reserva original
                    }
                }
                case 3 -> {
                    System.out.println("Volviendo al menu de reservas...");
                    modificacionFinalizada = true;
                }
                default -> System.out.println("Opcion no valida.");
            }

        } while (!modificacionFinalizada);
    }


    public void comprarEntrada(Scanner sc) {
        
        if (!hayAsientosReservados()) {
            System.out.println("\nNo existen asientos reservados para compra actualmente.");
            return;
        }
        boolean opcionValida = false; 
        reservados.clear();

        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                reservados.add(asiento);
            }
        }

        do {
            System.out.println("\n=== COMPRAR ENTRADA(S) ===");
            System.out.println("Numero de asientos reservados: " + reservados.size());
            System.out.println("Detalle: " + mostrarAsientosReservados());
            System.out.println("Total a pagar: $" + calcularTotalCheckout());

            int opcion = InputUtils.leerEntero(sc, "1 = Pagar / 0 = Cancelar: ");
            
            switch (opcion) {
                case 1 -> {
                    marcarComoVendidos();
                    System.out.println("\nCompra realizada con exito. Gracias!");
                    opcionValida = true;
                }
                case 0 -> {
                    marcarComoDisponibles();
                    System.out.println("\nOperacion cancelada. No se compraron asientos.");
                    opcionValida = true;
                }
                default -> System.out.println("Opcion no valida.");
            }
            
        } while (!opcionValida);
    }
    
    private void reservarAsientoConExpiracion(Asiento asiento) {
        asiento.setReservado();
        programarExpiracion(asiento, AppConfig.TIEMPO_DE_RESERVA_EN_SEGUNDOS);
    }

    private boolean hayAsientosReservados(){
        for (Asiento a : asientos) {
            if (a.getEstado() == AppConfig.Estado.RESERVADO) {
                return true;
            }
        }
        return false;
    }
    
    private String mostrarAsientosReservados(){
        String listaAsientos = "";
        for (Asiento asiento : reservados) {
            listaAsientos += asiento.mostrar() + " | ";
        }
        if (listaAsientos.isEmpty()) return "";
        return listaAsientos.substring(0, listaAsientos.length() - 2);
    }
    
    private double calcularTotalCheckout(){
        double totalCheckout = 0;
        for (Asiento asiento : reservados) {
            totalCheckout += asiento.getPrecio();
        }
        return totalCheckout;
    }
    
    private void marcarComoVendidos(){
        for (Asiento asiento : reservados) {
            asiento.setVendido();
        }
        contadorBoletas ++;
        boletas.add(new Boleta(contadorBoletas, new ArrayList<>(reservados)));
        reservados.clear();
        
    }
    
    private void marcarComoDisponibles(){
        for (Asiento asiento : reservados) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                asiento.setDisponible();
            }
        }
        reservados.clear();
    }
    
    /** Mostrar boleta por número */
    public void imprimirBoleta(Scanner sc) {
        if (boletas.isEmpty()) {
            System.out.println("\nNo existen boletas emitidas actualmente.");
            return;
        }

        int numero = InputUtils.leerEntero(sc, "Ingrese numero de boleta a mostrar: ");
        Boleta boleta = buscarBoletaPorNumero(numero);
                
        if (boleta == null) {
            System.out.println("\nBoleta no encontrada.");
        } else {
            System.out.println("\n=== BOLETA #" + boleta.getNumeroBoleta() + " ===");
            for (Asiento a : boleta.getAsientos()) {
                System.out.println(a.mostrarItemAsientoBoleta());
            }
            System.out.println(boleta.getTotal());
            System.out.println("=== FIN BOLETA ===");
        }
    }
    
    private Boleta buscarBoletaPorNumero(int numero) {
        for (Boleta b : boletas) {
            if (b.getNumeroBoleta() == numero) return b;
        }
        return null;
    }
}
