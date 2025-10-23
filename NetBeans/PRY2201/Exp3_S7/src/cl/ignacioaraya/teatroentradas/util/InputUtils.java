package cl.ignacioaraya.teatroentradas.util;

import java.util.Scanner;

public class InputUtils {
    
    /**
     * Lee y valida un número entero desde la entrada estándar.
     * <p>
     * Muestra un mensaje al usuario y espera a que ingrese un valor.
     * Si el valor ingresado no es un número entero válido,
     * se muestra un mensaje de error y se vuelve a solicitar la entrada
     * hasta que se proporcione un número entero correcto.
     *
     * @param sc      objeto {@link Scanner} usado para leer desde la entrada estándar.
     * @param mensaje mensaje que se mostrará al usuario antes de solicitar la entrada.
     * @return el número entero ingresado por el usuario.
     */
    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor numerico.");
            }
        }
    }
}