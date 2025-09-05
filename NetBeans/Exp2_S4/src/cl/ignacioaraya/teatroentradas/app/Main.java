package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Teatro;
import cl.ignacioaraya.teatroentradas.utils.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] menu = {"Comprar entrada", "Salir"};
        boolean salir = false;
        int opcionMenuPrincipal = -1;
        Teatro teatro = new Teatro();

        while (!salir) {

            System.out.println("\n=== MENU PRINCIPAL ===");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ") " + menu[i]);
            }

            opcionMenuPrincipal = InputUtils.leerEntero(sc, "\nElija una opcion: ");

            switch (opcionMenuPrincipal) {
                case 1: // Comprar entrada
                    boolean seguirComprando = true;
                    do {
                        Asiento asiento = seleccionarAsiento(teatro, sc);
                        int edad = ingresarEdad(sc);
                        double precioFinal = calcularPrecioFinal(asiento, edad);
                        procesarCheckout(asiento, precioFinal, sc);
                        seguirComprando = preguntarSiContinuar(sc);
                    } while (seguirComprando);
                    break;

                case 2: // Salir
                    salir = true;
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }

        System.out.println("\nHasta luego!");
        sc.close();
    }

    static Asiento seleccionarAsiento(Teatro teatro, Scanner sc) {
        boolean asientoSeleccionado = false;
        int opcionSeleccionarAsiento = -1;

        while (!asientoSeleccionado) {
            teatro.mostrar();
            opcionSeleccionarAsiento = InputUtils.leerEntero(sc, "\nSeleccione # de asiento: ");

            if (opcionSeleccionarAsiento < 1 || opcionSeleccionarAsiento > teatro.asientos.size()) {
                System.out.println("Asiento no valido.");
                continue;
            }

            if (teatro.asientos.get(opcionSeleccionarAsiento - 1).disponible) {
                asientoSeleccionado = true;
            } else {
                System.out.println("\nEl asiento seleccionado no esta disponible.");
            }
        }

        return teatro.asientos.get(opcionSeleccionarAsiento - 1);
    }

    static int ingresarEdad(Scanner sc) {
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

    static double calcularPrecioFinal(Asiento asiento, int edad) {
        int descuento = 0;

        if (edad <= 25) {
            descuento = 10;
        } else if (edad >= 65) {
            descuento = 15;
        }
        
        asiento.setDescuento(descuento);

        return asiento.PRECIO - asiento.PRECIO * asiento.descuento / 100;
    }

    static void procesarCheckout(Asiento asiento, double precioFinal, Scanner sc) {
        boolean checkoutFinalizado = false;
        int opcionCheckout = -1;

        while (!checkoutFinalizado) {
            System.out.println("\n=== RESUMEN DE LA COMPRA ===");
            System.out.println("Asiento: " + asiento.mostrarCheckout());
            System.out.println("Precio base: $" + asiento.PRECIO);
            System.out.println("Descuento aplicado: " + asiento.descuento + "%");
            System.out.println("Precio final: $" + precioFinal);

            System.out.println("\n1) Pagar");
            System.out.println("2) Cancelar");

            opcionCheckout = InputUtils.leerEntero(sc, "\nElija una opcion: ");

            switch (opcionCheckout) {
                case 1:
                    checkoutFinalizado = true;
                    asiento.seleccionar();
                    System.out.println("\nPago exitoso!");
                    break;

                case 2:
                    checkoutFinalizado = true;
                    System.out.println("\nCompra cancelada.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    static boolean preguntarSiContinuar(Scanner sc) {
        String respuesta;

        while (true) {
            System.out.print("\nDesea comprar otra entrada? (s/n): ");
            respuesta = sc.next().trim().toLowerCase();

            if (respuesta.length() == 1 && (respuesta.equals("s") || respuesta.equals("n"))) {
                break;
            } else {
                System.out.println("Respuesta no valida. Ingrese 's' para si o 'n' para no.");
            }
        }

        sc.nextLine();
        return respuesta.equals("s");
    }
}
