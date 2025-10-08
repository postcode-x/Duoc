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
                //case 2 -> venderEntradaUI();
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
        System.out.println("[X] : Vendido\n");
        System.out.print(ventaService.mostrar());
    }
    
}
