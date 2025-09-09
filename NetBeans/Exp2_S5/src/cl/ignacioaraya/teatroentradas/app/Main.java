package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        // Menú principal con opciones
        String[] menu = {"Venta de entradas", "Promociones", "Busqueda de entradas", "Eliminacion de entradas", "Salir"};
        int opcionMenuPrincipal = -1;
        
         // Loop principal de la aplicación
        do {

            System.out.println("\n=== MENU PRINCIPAL ===");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ") " + menu[i]);
            }
            
            opcionMenuPrincipal = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            switch (opcionMenuPrincipal) {
                case 1: // Flujo venta de entradas
                    System.out.println(opcionMenuPrincipal);
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

}
