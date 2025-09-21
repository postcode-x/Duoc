package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import cl.ignacioaraya.teatroentradas.service.TeatroService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TeatroService teatro = new TeatroService();
        int opcion;

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | " + AppConfig.NOMBRE_TEATRO + " ---");
            System.out.println("1. Reservar asiento");
            System.out.println("2. Modificar reserva");
            System.out.println("3. Comprar entradas");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> reservarAsientoUI(sc, teatro);
                case 2 -> modificarReservaUI(sc, teatro);
                case 3 -> comprarEntradaUI(sc, teatro);
                case 4 -> imprimirBoletaUI(sc, teatro);
                case 5 -> System.out.println("\nHasta luego!");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 5);

        // Cerrar recursos
        teatro.shutdown();
        sc.close();
    }

    // UI para reservar asiento
    private static void reservarAsientoUI(Scanner sc, TeatroService teatro) {
        boolean seguirReservando;
        do {
            System.out.println("\n=== RESERVAR ASIENTO ===");
            System.out.print(teatro.mostrar());

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");

            if (opcion < 1 || opcion > teatro.getAsientos().size()) {
                System.out.println("Numero de asiento no valido.");
            } else {
                Asiento asiento = teatro.getAsientos().get(opcion - 1);
                if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                    teatro.reservarAsientoConExpiracion(asiento);
                    System.out.println("\nAsiento reservado con exito.");
                } else {
                    System.out.println("\nEl asiento seleccionado no esta disponible.");
                }
            }

            seguirReservando = preguntaSeguirReservando(sc);

        } while (seguirReservando);
    }

    // Pregunta al usuario si desea seguir reservando
    private static boolean preguntaSeguirReservando(Scanner sc) {
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, "Seguir reservando? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }

    // UI para modificar reserva
    private static void modificarReservaUI(Scanner sc, TeatroService teatro) {
        if (!teatro.hayAsientosReservados()) {
            System.out.println("\nNo existen asientos reservados actualmente.");
            return;
        }

        boolean modificacionFinalizada = false;

        do {
            System.out.println("\n=== MODIFICAR RESERVA ===");
            System.out.print(teatro.mostrar());

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento reservado a modificar (0 para volver): ");

            if (opcion == 0) break;

            if (opcion < 1 || opcion > teatro.getAsientos().size()) {
                System.out.println("Numero de asiento no valido.");
                continue;
            }

            Asiento asiento = teatro.getAsientos().get(opcion - 1);

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
                    // Cancelar reserva
                    asiento.setDisponible();
                    System.out.println("\nReserva cancelada. Asiento #" + asiento.getNumero() + " disponible.");
                    modificacionFinalizada = true;
                }
                case 2 -> {
                    // Cambiar asiento
                    asiento.setDisponible();
                    System.out.println("\nSeleccione el nuevo asiento a reservar:");
                    System.out.print(teatro.mostrar());

                    int nuevo = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");

                    if (nuevo < 1 || nuevo > teatro.getAsientos().size()) {
                        System.out.println("Numero de asiento no valido.");
                        teatro.reservarAsientoConExpiracion(asiento); // restaurar original
                        continue;
                    }

                    Asiento nuevoAsiento = teatro.getAsientos().get(nuevo - 1);
                    if (nuevoAsiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                        teatro.reservarAsientoConExpiracion(nuevoAsiento);
                        System.out.println("\nReserva cambiada con exito al asiento #" + nuevoAsiento.getNumero());
                        modificacionFinalizada = true;
                    } else {
                        System.out.println("\nEl asiento seleccionado no esta disponible.");
                        teatro.reservarAsientoConExpiracion(asiento); // restaurar original
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

    // UI para comprar entradas
    private static void comprarEntradaUI(Scanner sc, TeatroService teatro) {
        if (!teatro.hayAsientosReservados()) {
            System.out.println("\nNo existen asientos reservados para compra.");
            return;
        }

        teatro.getCarrito().clear();
        for (Asiento asiento : teatro.getAsientos()) {
            if (asiento.getEstado() == AppConfig.Estado.RESERVADO) {
                teatro.getCarrito().add(asiento);
            }
        }

        boolean opcionValida = false;

        do {
            System.out.println("\n=== COMPRAR ENTRADA(S) ===");
            System.out.println("Numero de asientos reservados: " + teatro.getCarrito().size());
            System.out.println("Detalle: " + teatro.mostrarAsientosCarrito());
            System.out.println("Total a pagar: $" + teatro.calcularTotalCarrito());

            int opcion = InputUtils.leerEntero(sc, "1 = Pagar / 0 = Cancelar: ");

            switch (opcion) {
                case 1 -> {
                    int numBoleta = teatro.marcarComoVendidos();
                    System.out.println("\nCompra realizada con exito (Boleta # " + numBoleta + "). Gracias!");
                    opcionValida = true;
                }
                case 0 -> {
                    teatro.marcarComoDisponibles();
                    System.out.println("\nOperacion cancelada. No se compraron asientos.");
                    opcionValida = true;
                }
                default -> System.out.println("Opcion no valida.");
            }

        } while (!opcionValida);
    }

    // UI para imprimir boleta
    private static void imprimirBoletaUI(Scanner sc, TeatroService teatro) {
        if (teatro.getBoletas().isEmpty()) {
            System.out.println("\nNo existen boletas emitidas actualmente.");
            return;
        }

        int numero = InputUtils.leerEntero(sc, "Ingrese numero de boleta a mostrar: ");
        Boleta boleta = teatro.buscarBoletaPorNumero(numero);

        if (boleta == null) {
            System.out.println("\nBoleta no encontrada.");
        } else {
            System.out.println("\n=== BOLETA #" + boleta.getNumeroBoleta() + " ===\n");
            for (Asiento asiento : boleta.getAsientos()) {
                System.out.println(asiento.mostrarItemAsientoBoleta());
            }
            System.out.println("Precio Total: $" + Math.round(boleta.getTotal()));
            System.out.println("\n=== FIN BOLETA ===");
        }
    }

}
