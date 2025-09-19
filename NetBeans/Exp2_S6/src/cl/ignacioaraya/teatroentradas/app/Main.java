package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.service.TeatroService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);
        TeatroService teatro = new TeatroService();
        int opcion;

        do {
            System.out.println("\n--- Menu de Venta ---");
            System.out.println("1. Reservar asiento");
            System.out.println("2. Modificar reserva");
            System.out.println("3. Comprar entradas");
            System.out.println("4. Imprimir boleta");
            System.out.println("5. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> teatro.reservarAsiento(sc);
                case 2 -> teatro.modificarReserva(sc);
                case 3 -> teatro.comprarEntrada(sc);
                case 4 -> teatro.imprimirBoleta(sc);
                case 5 -> System.out.println("\nHasta luego!");
                default -> System.out.println("Opcion invalida.");
            }
        } while (opcion != 5);
        
        sc.close();
        
    }
    
}
