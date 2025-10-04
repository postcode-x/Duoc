package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;


public class Main {
    
    public static void Main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        int opcion;

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | " + AppConfig.NOMBRE_TEATRO);
                    //+ " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Mostrar asientos");
            System.out.println("3. Vender entradas");
            System.out.println("4. Configurar descuentos");
            System.out.println("5. Listar Ventas (Reporte)");
            System.out.println("6. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                //case 1 -> gestionarClientesUI(sc);
                //case 2 -> mostrarAsientosTeatroUI(sc);
                //case 3 -> venderEntradaUI(sc);
                //case 4 -> configurarDescuentosUI(sc);
                //case 5 -> listarVentasUI();
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 5);
        
        // Cerrar recursos
        sc.close();
    
    }
    
}
