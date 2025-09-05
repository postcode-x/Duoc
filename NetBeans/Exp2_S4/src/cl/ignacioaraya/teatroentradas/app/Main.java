package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Teatro;
import cl.ignacioaraya.teatroentradas.utils.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Menú principal con opciones
        String[] menu = {"Comprar entrada", "Salir"};
        int opcionMenuPrincipal = -1;
        Teatro teatro = new Teatro();

        // Loop principal de la aplicación
        while (opcionMenuPrincipal != 1 && opcionMenuPrincipal != 2) {

            System.out.println("\n=== MENU PRINCIPAL ===");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ") " + menu[i]);
            }

            opcionMenuPrincipal = InputUtils.leerEntero(sc, "\nElija una opcion: ");

            switch (opcionMenuPrincipal) {
                case 1: // Flujo de compra de entrada
                    boolean seguirComprando = true;
                    do {
                        // Selección de asiento disponible
                        Asiento asiento = seleccionarAsiento(teatro, sc);
                        // Ingreso y validación de edad
                        int edad = ingresarEdad(sc);
                        // Cálculo del precio final aplicando descuentos
                        int precioFinal = calcularPrecioFinal(asiento, edad);
                        // Confirmación del pago o cancelación
                        procesarCheckout(asiento, precioFinal, sc);
                        // Preguntar si desea seguir comprando
                        seguirComprando = preguntarSiContinuar(sc);
                    } while (seguirComprando);
                    break;

                case 2: // Salir
                    break;

                default: // Ingresa entero distinto de 1 y 2
                    System.out.println("Opcion no valida.");
            }
        }

        System.out.println("\nHasta luego!");
        sc.close();
    }

    // Método para seleccionar un asiento disponible dentro del teatro
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

    // Método para ingresar y validar edad
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

    // Cálculo del precio final en base a edad (aplicando descuentos), redondeado al entero más cercano
    static int calcularPrecioFinal(Asiento asiento, int edad) {
        int descuento = 0;

        if (edad <= AppConstants.EDAD_MAX_ESTUDIANTE) {
            descuento = AppConstants.DESCUENTO_ESTUDIANTE;
        } else if (edad >= AppConstants.EDAD_MIN_ADULTO_MAYOR) {
            descuento = AppConstants.DESCUENTO_ADULTO_MAYOR;
        }
        
        asiento.setDescuento(descuento);

        return Math.round(AppConstants.PRECIO_BASE_ASIENTO - AppConstants.PRECIO_BASE_ASIENTO * asiento.descuento / 100);
    }

    // Proceso de checkout: mostrar resumen y confirmar compra
    static void procesarCheckout(Asiento asiento, int precioFinal, Scanner sc) {
        boolean checkoutFinalizado = false;
        int opcionCheckout = -1;

        while (!checkoutFinalizado) {
            System.out.println("\n=== RESUMEN DE LA COMPRA ===");
            System.out.println("Asiento: " + asiento.mostrarCheckout());
            System.out.println("Precio base: $" + AppConstants.PRECIO_BASE_ASIENTO);
            System.out.println("Descuento aplicado: " + asiento.descuento + "% " +
               (asiento.descuento == AppConstants.DESCUENTO_ESTUDIANTE ? "(Estudiante)" :
                asiento.descuento == AppConstants.DESCUENTO_ADULTO_MAYOR ? "(Adulto mayor)" : ""));
            System.out.println("Precio final: $" + precioFinal);

            System.out.println("\n1) Pagar");
            System.out.println("2) Cancelar");

            opcionCheckout = InputUtils.leerEntero(sc, "\nElija una opcion: ");

            switch (opcionCheckout) {
                case 1: // Confirmar pago
                    checkoutFinalizado = true;
                    asiento.seleccionar();
                    System.out.println("\nPago exitoso!");
                    break;

                case 2: // Cancelar compra
                    checkoutFinalizado = true;
                    System.out.println("\nCompra cancelada.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
    
    // Método para preguntar al usuario si desea seguir comprando
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
