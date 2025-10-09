package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import cl.ignacioaraya.teatroentradas.service.VentaService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        VentaService ventaService = new VentaService();

        int opcion;

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | "
                    + AppConfig.NOMBRE_TEATRO
                    + " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---\n");
            System.out.println("1. Mostrar Asientos Disponibles");
            System.out.println("2. Vender Entradas");
            System.out.println("3. Listar Ventas / Imprimir Boletas");
            System.out.println("4. Eliminar Venta");
            System.out.println("5. Reporte General");
            System.out.println("6. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 ->
                    mostrarAsientosTeatroUI(ventaService);
                case 2 ->
                    venderEntradaUI(sc, ventaService);
                case 3 ->
                    mostrarVentasUI(sc, ventaService);
                case 4 ->
                    eliminarVentaUI(sc, ventaService);
                case 5 ->
                    mostrarReporteGeneralUI(ventaService);
                case 6 ->
                    System.out.println("\nHasta luego!");
                default ->
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 6);

        // Cerrar recursos
        sc.close();

    }

    // Muestra el estado actual de los asientos (D, S o X)
    private static void mostrarAsientosTeatroUI(VentaService ventaService) {
        System.out.println("\n--- Asientos ---\n");
        System.out.println("[D] : Disponible");
        System.out.println("[S] : Seleccionado");
        System.out.println("[X] : Ocupado\n");
        System.out.print(ventaService.mostrar());
    }

    // Gestiona el proceso de venta de entradas al usuario
    private static void venderEntradaUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getAsientosDisponibles() == 0) {
            System.out.println("\nNo quedan asientos disponibles.");
            return;
        }

        // Limpia el carrito antes de iniciar una nueva compra
        ventaService.getCarrito().clear();

        // Solicita datos del cliente
        int edad = preguntarEdadCliente(sc);
        boolean esMujer = preguntarGeneroMujer(sc);

        // Calcula descuentos aplicables
        int descuentoPorEdad = ventaService.calculaDescuentoPorEdad(edad);
        int descuentoPorGenero = ventaService.calculaDescuentoPorGenero(esMujer);

        // Solo se aplica el descuento mayor entre los dos
        int descuentoFinal = Math.max(descuentoPorEdad, descuentoPorGenero);

        boolean seguirComprando;

        do {
            // Selecciona un asiento disponible
            int numeroAsiento = seleccionarAsiento(sc, ventaService);

            // Marca el asiento como seleccionado y lo agrega al carrito
            for (Asiento asiento : ventaService.getAsientos()) {
                if (asiento.getNumero() == numeroAsiento) {
                    asiento.setSeleccionado();
                    ventaService.getCarrito().add(asiento);
                    break;
                }
            }

            seguirComprando = preguntaSeguirComprando(sc);

        } while (seguirComprando);

        // Muestra el resumen de compra antes de confirmar
        System.out.println("\n--- Resumen de la compra ---\n");
        System.out.println("Cantidad de asientos elegidos: " + ventaService.getCarrito().size());
        for (Asiento asiento : ventaService.getCarrito()) {
            System.out.println(asiento.getNumero() + (asiento.getNumero() < 10 ? ".  | " : ". | ") + asiento.getFila() + "-" + asiento.getColumna() + " " + asiento.getZona().nombre() + " | Precio: $" + Math.round(asiento.getZona().precio()));
        }
        System.out.println("\nPrecio: $" + Math.round(ventaService.calcularTotalCarrito()));
        System.out.println("Descuento: " + descuentoFinal + "% (" + getDescuentoTexto(descuentoFinal) + ")");
        System.out.println("Total a pagar: $" + Math.round(ventaService.calcularTotalCarritoConDescuento(descuentoFinal)));

        // Confirmar o cancelar compra
        boolean confirmaCompra = preguntaConfirmarCompra(sc);

        if (confirmaCompra) {
            int numBoleta = ventaService.marcarComoVendidos(descuentoFinal);
            System.out.println("\nCompra realizada con exito (Boleta # " + numBoleta + "). Gracias!");
        } else {
            ventaService.marcarComoDisponibles();
            System.out.println("\nOperacion cancelada. No se compraron asientos.");
        }

    }

    // Pregunta al usuario si confirma la compra
    private static boolean preguntaConfirmarCompra(Scanner sc) {
        int opcion;
        do {
            opcion = InputUtils.leerEntero(sc, "\n1 = Pagar / 0 = Cancelar: ");
            if (opcion != 0 && opcion != 1) {
                System.out.println("Opcion no valida.");
            }
        } while (opcion != 0 && opcion != 1);

        return opcion == 1;
    }

    // Pregunta si el usuario desea seguir comprando más asientos
    private static boolean preguntaSeguirComprando(Scanner sc) {
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, "\nSeguir comprando? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }

    // Permite al usuario seleccionar un asiento disponible
    private static int seleccionarAsiento(Scanner sc, VentaService ventaService) {
        boolean asientoSeleccionado = false;
        int numeroAsiento = -1;

        while (!asientoSeleccionado) {

            System.out.println("\n--- SELECCIONAR ASIENTO ---\n");
            System.out.print(ventaService.mostrar());

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");

            if (opcion < 1 || opcion > ventaService.getAsientos().size()) {
                System.out.println("Numero de asiento no valido.");
            } else {
                Asiento asiento = ventaService.getAsientos().get(opcion - 1);
                if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                    numeroAsiento = asiento.getNumero();
                    asientoSeleccionado = true;
                    System.out.println("\nAsiento seleccionado con exito.");
                } else {
                    System.out.println("\nEl asiento seleccionado no esta disponible.");
                }
            }

        }

        return numeroAsiento;
    }

    // Solicita y valida la edad del cliente
    private static int preguntarEdadCliente(Scanner sc) {
        boolean edadIngresada = false;
        int edad = -1;

        while (!edadIngresada) {
            edad = InputUtils.leerEntero(sc, "\nIngrese su edad: ");

            if (edad < 0 || edad > 120) {
                System.out.println("Edad no valida.");
                continue;
            }

            edadIngresada = true;
        }

        return edad;
    }

    // Pregunta al usuario si es de genero femenino
    private static boolean preguntarGeneroMujer(Scanner sc) {
        int opcion;
        do {
            opcion = InputUtils.leerEntero(sc, "\nEs usted de genero femenino (1 = Si / 0 = No): ");
            if (opcion != 0 && opcion != 1) {
                System.out.println("Opcion no valida.");
            }
        } while (opcion != 0 && opcion != 1);

        return opcion == 1;
    }

    // Lista todas las ventas realizadas y permite ver detalle de cada boleta
    private static void mostrarVentasUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getBoletas().isEmpty()) {
            System.out.println("\nNo existen entradas vendidas.");
            return;
        }

        int opcion;

        // Menu de boletas
        do {

            System.out.println("\n--- RESUMEN DE VENTAS ---\n");

            List< Boleta> boletas = ventaService.getBoletas();

            for (int i = 0; i < boletas.size(); i++) {
                Boleta boleta = boletas.get(i);
                System.out.println((i + 1) + ") | Boleta # "
                        + boleta.getNumero() + " | Total: $"
                        + Math.round(boleta.getTotal()));
            }

            opcion = InputUtils.leerEntero(sc, "\nEscriba numero para ver detalle (0 para salir): ");

            if (opcion == 0) {
                break;
            }

            if (opcion < 1 || opcion > ventaService.getBoletas().size()) {
                System.out.println("\nOpcion invalida.");
            } else {
                Boleta boleta = boletas.get(opcion - 1);
                System.out.println("\nBoleta # " + boleta.getNumero() + " | Cantidad de Asientos: " + boleta.getAsientos().size());
                for (Asiento asiento : boleta.getAsientos()) {
                    System.out.println(asiento.getNumero() + (asiento.getNumero() < 10 ? ".  | " : ". | ") + asiento.getFila() + "-" + asiento.getColumna() + " " + asiento.getZona().nombre() + " | Precio: $" + Math.round(asiento.getZona().precio()));
                }
                System.out.println("\nPrecio: $" + Math.round(boleta.getPrecio()));
                System.out.println("Descuento: " + boleta.getDescuento() + "% (" + getDescuentoTexto(boleta.getDescuento()) + ")");
                System.out.println("Total: $" + Math.round(boleta.getTotal()));
            }

        } while (opcion != 0);

    }

    // Permite eliminar una venta y liberar sus asientos
    private static void eliminarVentaUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getBoletas().isEmpty()) {
            System.out.println("\nNo existen ventas registradas.");
            return;
        }

        System.out.println("\n--- ELIMINAR VENTA ---\n");
        for (Boleta boleta : ventaService.getBoletas()) {
            System.out.println("Boleta # " + boleta.getNumero() + " | Total: $" + Math.round(boleta.getTotal()));
        }

        int numero = InputUtils.leerEntero(sc, "\nIngrese numero de boleta a eliminar (0 para cancelar): ");

        if (numero == 0) {
            System.out.println("\nOperacion cancelada.");
            return;
        }

        boolean eliminada = ventaService.eliminarVenta(numero);

        if (eliminada) {
            System.out.println("\nVenta eliminada correctamente. Los asientos fueron liberados.");
        } else {
            System.out.println("\nNumero de boleta no valido. Intente nuevamente.");
        }
    }

    // Muestra el reporte general del teatro (estadísticas básicas)
    private static void mostrarReporteGeneralUI(VentaService ventaService) {
        System.out.println(ventaService.generarReporteGeneral());
    }

    // Devuelve texto descriptivo del tipo de descuento aplicado
    private static String getDescuentoTexto(int descuento) {
        return descuento == AppConfig.DESCUENTO_NINOS
                ? "Descuento Niños"
                : descuento == AppConfig.DESCUENTO_ESTUDIANTE
                        ? "Descuento Estudiante"
                        : descuento == AppConfig.DESCUENTO_ADULTO_MAYOR
                                ? "Descuento Adulto Mayor"
                                : descuento == AppConfig.DESCUENTO_MUJER
                                        ? "Descuento Mujer" : "Sin descuento";
    }

}
