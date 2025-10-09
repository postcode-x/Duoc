package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.service.VentaService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args){
        
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
                case 1 -> mostrarAsientosTeatroUI(ventaService);
                case 2 -> venderEntradaUI(sc, ventaService);
                //case 3 -> listarEntradasUI();
                //case 4 -> eliminarVenta();
                //case 5 -> mostrarReporte();
                case 6 -> System.out.println("\nHasta luego!");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 6);
        
        // Cerrar recursos
        sc.close();
        
    }
    
    // UI para mostrar layout con asientos del teatro
    private static void mostrarAsientosTeatroUI(VentaService ventaService){
        System.out.println("\n--- Asientos ---");
        System.out.println("[D] : Disponible");
        System.out.println("[X] : Ocupado\n");
        System.out.print(ventaService.mostrar());
    }
    
    // UI para venta de entradas
    private static void venderEntradaUI(Scanner sc, VentaService ventaService){
        if (ventaService.getAsientosDisponibles() == 0) {
            System.out.println("\nNo quedan asientos disponibles.");
            return;
        }
        
        ventaService.getCarrito().clear();
                
        int edad = preguntarEdadCliente(sc);
        boolean esMujer = preguntarGeneroMujer(sc);

        int descuentoPorEdad = ventaService.calculaDescuentoPorEdad(edad);
        int descuentoPorGenero = ventaService.calculaDescuentoPorGenero(esMujer);

        int descuentoFinal = Math.max(descuentoPorEdad, descuentoPorGenero);
        
        boolean seguirComprando;
        
        do {
            // Seleccionar ubicacion
            int numeroAsiento = seleccionarAsiento(sc, ventaService);
            
            for (Asiento asiento : ventaService.getAsientos()) {
                if (asiento.getNumero() == numeroAsiento) {
                    asiento.setDescuento(descuentoFinal);
                    asiento.setPendiente();
                    ventaService.getCarrito().add(asiento);
                    break;
                }
            }
                        
            seguirComprando = preguntaSeguirComprando(sc);

        } while (seguirComprando);
        
        System.out.println("\n--- Resumen de la compra ---");
        System.out.println("Cantidad de asientos elegidos: " + ventaService.getCarrito().size());
        for(Asiento asiento: ventaService.getCarrito()){
           System.out.println(asiento.getNumero() + (asiento.getNumero() < 10 ? "  | ": " | ") + asiento.getZona().nombre() + " | Precio: $" + asiento.getZona().precio() + " | " + asiento.getDescuentoTexto() + " (" + asiento.getDescuento() + "%)");
        }        
        System.out.println("\nTotal a pagar: $" + ventaService.calcularTotalCarrito());
        
        boolean confirmaCompra = preguntaConfirmarCompra(sc);
        
        if(confirmaCompra){
            int numBoleta = ventaService.marcarComoVendidos();
            System.out.println("\nCompra realizada con exito (Boleta # " + numBoleta + "). Gracias!");
        }else{
            ventaService.marcarComoDisponibles();
            System.out.println("\nOperacion cancelada. No se compraron asientos.");
        }        

    }
    
    // Pregunta al usuario si desea comprar
    private static boolean preguntaConfirmarCompra(Scanner sc) {
        int opcion;
        do {
            opcion = InputUtils.leerEntero(sc, "\n1 = Pagar / 0 = Cancelar: ");
            if (opcion != 0 && opcion != 1) {
                System.out.println("Opción no válida.");
            }
        } while (opcion != 0 && opcion != 1);

        return opcion == 1;
    }

    // Pregunta al usuario si desea seguir comprando
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
    
    // UI para seleccionar ubicacion del asiento
    private static int seleccionarAsiento(Scanner sc, VentaService ventaService) {
        boolean asientoSeleccionado = false;
        int numeroAsiento = -1;
        
        while (!asientoSeleccionado) {
        
            System.out.println("\n=== SELECCIONAR ASIENTO ===");
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
    
    // UI para preguntar por edad y validar
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
    
    // UI para preguntar al usuario por su género
    private static boolean preguntarGeneroMujer(Scanner sc) {
        String respuesta;

        while (true) {
            System.out.print("\nEs usted de genero femenino? Ingrese 's' para si o 'n' para no: ");
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
