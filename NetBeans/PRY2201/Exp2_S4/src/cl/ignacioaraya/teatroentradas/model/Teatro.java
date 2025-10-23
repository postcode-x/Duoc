package cl.ignacioaraya.teatroentradas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un teatro compuesto por varias zonas, filas y asientos.
 * Se encarga de inicializar todos los asientos y de mostrarlos
 * agrupados por zona para su selección.
 */
public class Teatro {
    
    public List<Asiento> asientos = new ArrayList<>();
    String[] zonas = {"A", "B", "C"};
    int filasPorZona = 2;
    int asientosPorFila = 5;
    int contador = 0;

    // Constructor
    public Teatro() {
        for (String zona : zonas) {
            for (int fila = 1; fila <= filasPorZona; fila++) {
                for (int columna = 1; columna <= asientosPorFila; columna++) {
                    contador += 1;
                    asientos.add(new Asiento(zona, fila, columna, contador));
                }
            }
        }
    }

    // Mostrar asientos
    public void mostrar() {
        System.out.println("\n=== SELECCIONAR ASIENTO ===");
        for (String zona : zonas) {
            System.out.println("Zona " + zona + ":");
            for (Asiento s : asientos) {
                if (s.zona.equals(zona)) {
                    System.out.print(s.mostrar() + "   ");
                    if (s.columna == asientosPorFila) System.out.println();
                }
            }
            System.out.println();
        }
    }
    
}
