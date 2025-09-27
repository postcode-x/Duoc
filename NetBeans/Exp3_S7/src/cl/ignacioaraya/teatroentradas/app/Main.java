package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
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
            System.out.println("\n--- Menu de Venta | " + AppConfig.NOMBRE_TEATRO + " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Resumen de ventas");
            System.out.println("3. Generar boleta");
            System.out.println("4. Calcular ingresos totales");
            System.out.println("5. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> venderEntradasUI(sc, ventaService);
                case 2 -> resumirVentasUI(sc, ventaService);
                case 3 -> generarBoletaUI(sc, ventaService);
                case 4 -> calcularIngresosUI(sc, ventaService);
                case 5 -> System.out.println("\nHasta luego!");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 5);

        // Cerrar recursos
        sc.close();
    
    }
    
    // UI para vender entradas
    private static void venderEntradasUI(Scanner sc, VentaService ventaService) {
        if(ventaService.getAsientosDisponibles() == 0){
            System.out.println("\nNo quedan entradas disponibles.");
            return;
        }
        
        // Elegir ubicación y tipo de cliente
        AppConfig.Ubicacion ubicacion = seleccionarUbicacion(sc);
        AppConfig.TipoCliente tipoCliente = seleccionarTipoCliente(sc);
        int precio = ventaService.calcularPrecioEntrada(ubicacion, tipoCliente);

        // Agregar entrada al carrito
        ventaService.agregarEntrada(ubicacion, tipoCliente, precio);
        
        // Calcular total final
        int totalFinal = ventaService.calcularTotal();
        
        System.out.println("Entrada agregada. Precio: $" + totalFinal);
        
        boolean opcionValida = false; 
        
        // Checkout: pagar o cancelar
        do {
            System.out.println("\n=== CHECKOUT ===");
            System.out.println("Entrada seleccionada: " + ventaService.getCanasta().get(0).mostrar());
            System.out.println("Total a pagar (con descuento aplicado si corresponde): $" + totalFinal);

            int opcion = InputUtils.leerEntero(sc, "1 = Pagar / 0 = Cancelar: ");
            
            switch (opcion) {
                case 1 -> {
                    ventaService.confirmarCompra();
                    System.out.println("\nCompra realizada con exito. Gracias!");
                    opcionValida = true;
                }
                case 2 -> {
                    ventaService.cancelarCompra();
                    System.out.println("\nOperacion cancelada. No se guardaron entradas.");
                    opcionValida = true;
                }
                default -> System.out.println("\nOpcion no valida, intente nuevamente.");
            }
            
        } while (!opcionValida);
        
    }
    
    // UI para seleccionar ubicación del asiento
    private static AppConfig.Ubicacion seleccionarUbicacion(Scanner sc) {
        boolean ubicacionSeleccionada = false;
        int opcionUbicacion = -1;
        
        while(!ubicacionSeleccionada){
            System.out.println("\n=== ELEGIR UBICACION ===");
            int i = 1;
            for (AppConfig.Ubicacion u : AppConfig.Ubicacion.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionUbicacion = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionUbicacion < 1 || opcionUbicacion > AppConfig.Ubicacion.values().length) {
                System.out.println("Ubicacion no valida.");
                continue;
            } else {
                ubicacionSeleccionada = true;
            }
        }
        
        return AppConfig.Ubicacion.values()[opcionUbicacion -1];
    }
    
    // UI para seleccionar tipo de cliente
    private static AppConfig.TipoCliente seleccionarTipoCliente(Scanner sc) {
        boolean tipoClienteSeleccionado = false;
        int opcionTipoCliente = -1;
        
        while(!tipoClienteSeleccionado){
            System.out.println("\n=== ELEGIR TIPO CLIENTE ===");
            int i = 1;
            for (AppConfig.TipoCliente u : AppConfig.TipoCliente.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionTipoCliente = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionTipoCliente < 1 || opcionTipoCliente > AppConfig.TipoCliente.values().length) {
                System.out.println("Tipo de cliente no valido.");
                continue;
            } else {
                tipoClienteSeleccionado = true;
            }
        }
        
        return AppConfig.TipoCliente.values()[opcionTipoCliente -1];
    }
    
     // UI para resumir ventas
    private static void resumirVentasUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getEntradasVendidas().isEmpty()) {
            System.out.println("\nNo existen entradas vendidas.");
            return;
        }
    
    }
    
     // UI para generar boletas
    private static void generarBoletaUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getEntradasVendidas().isEmpty()) {
            System.out.println("\nNo existen entradas vendidas.");
            return;
        }
    }
    
     // UI para calcular ingresos
    private static void calcularIngresosUI(Scanner sc, VentaService ventaService) {
        if (ventaService.getEntradasVendidas().isEmpty()) {
            System.out.println("\nNo existen entradas vendidas.");
            return;
        }
        System.out.println("\nLos ingresos totales son de: $" + ventaService.getIngresosTotales());
    }
    
}
