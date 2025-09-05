package cl.ignacioaraya.teatroentradas.utils;

import java.util.Scanner;

public class InputUtils {
    
    /**
     * Función de utilidad para leer y validar un número entero desde la entrada estándar.
     * <p>
     * Muestra un mensaje al usuario, espera a que ingrese un valor,
     * y valida que sea un número entero. Si no lo es, vuelve a pedir la entrada.
    */
    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un valor entero.");
            }
        }
    }
}