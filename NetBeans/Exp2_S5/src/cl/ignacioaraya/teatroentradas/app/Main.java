package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import cl.ignacioaraya.teatroentradas.service.VentaService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        VentaService ventaService = new VentaService();
        
        // Menú principal con opciones
        String[] menu = {"Venta de entradas", "Promociones", "Busqueda de entradas", "Eliminacion de entradas", "Salir"};
        int opcionMenuPrincipal = -1;
        
         // Loop principal de la aplicación
        do {

            System.out.println("\n=== MENU PRINCIPAL ===" + ventaService.getEntradasVendidas().size());
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ") " + menu[i]);
            }
            
            opcionMenuPrincipal = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            switch (opcionMenuPrincipal) {
                case 1: flujoVentaEntradas(sc, ventaService);
                    break;

                case 2: // Promociones
                    System.out.println(opcionMenuPrincipal);
                    break;
                    
                case 3: // Busqueda
                    System.out.println(opcionMenuPrincipal);
                    break;
                    
                case 4: // Eliminacion
                    System.out.println(opcionMenuPrincipal);
                    break;
                    
                case 5: // Salir
                    break;

                default: // Ingresa entero fuera de rango
                    System.out.println("Opcion no valida.");
            }
            
        } while (opcionMenuPrincipal != 5);
        
        System.out.println("\nHasta luego!");
        sc.close();
    
    }
    
    // Flujo de venta de entradas
    private static void flujoVentaEntradas(Scanner sc, VentaService ventaService) {
        boolean seguirComprando;
        do {
            AppConstants.Ubicacion ubicacion = seleccionarUbicacion(sc);
            AppConstants.TipoCliente tipoCliente = seleccionarTipoCliente(sc);
            int precio = ventaService.calcularPrecioEntrada(ubicacion, tipoCliente);
            ventaService.agregarEntrada(ubicacion, tipoCliente, precio);
            System.out.println("Entrada agregada. Precio: $" + precio);

            seguirComprando = preguntaSeguirComprando(sc);
        } while (seguirComprando);

        int totalFinal = ventaService.calcularTotalConDescuento();
        
        boolean opcionValida = false; 
        do {
            System.out.println("\n=== CHECKOUT ===");
            System.out.println("Entradas seleccionadas: " + ventaService.getCanasta().size());
            System.out.println("Total a pagar (con descuento aplicado si corresponde): $" + totalFinal);

            int opcion = InputUtils.leerEntero(sc, "1 = Pagar / 0 = Cancelar: ");
            if (opcion == 1) {
                ventaService.confirmarCompra();
                System.out.println("\nCompra realizada con exito. Gracias!");
                opcionValida = true; // salimos
            } else if (opcion == 0){
                ventaService.cancelarCompra();
                System.out.println("\nOperacion cancelada. No se guardaron entradas.");
                opcionValida = true; // salimos
            }else {
                System.out.println("\nOpcion no valida, intente nuevamente.");
            }
            
        } while (!opcionValida);
    }
    
    private static AppConstants.Ubicacion seleccionarUbicacion(Scanner sc) {
        boolean ubicacionSeleccionada = false;
        int opcionUbicacion = -1;
        
        while(!ubicacionSeleccionada){
            System.out.println("\n=== ELEGIR UBICACION ===");
            int i = 1;
            for (AppConstants.Ubicacion u : AppConstants.Ubicacion.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionUbicacion = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionUbicacion < 1 || opcionUbicacion > AppConstants.Ubicacion.values().length) {
                System.out.println("Ubicacion no valida.");
                continue;
            }else{
                ubicacionSeleccionada = true;
            }
        }
        
        return AppConstants.Ubicacion.values()[opcionUbicacion -1];
    }
    
    private static AppConstants.TipoCliente seleccionarTipoCliente(Scanner sc) {
        boolean tipoClienteSeleccionado = false;
        int opcionTipoCliente = -1;
        
        while(!tipoClienteSeleccionado){
            System.out.println("\n=== ELEGIR TIPO CLIENTE ===");
            int i = 1;
            for (AppConstants.TipoCliente u : AppConstants.TipoCliente.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionTipoCliente = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionTipoCliente < 1 || opcionTipoCliente > AppConstants.TipoCliente.values().length) {
                System.out.println("Ubicacion no valida.");
                continue;
            }else{
                tipoClienteSeleccionado = true;
            }
        }
        
        
        return AppConstants.TipoCliente.values()[opcionTipoCliente -1];
    }
    
    private static boolean preguntaSeguirComprando(Scanner sc){
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, " Seguir comprando? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }

}
