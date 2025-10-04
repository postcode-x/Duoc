package cl.ignacioaraya.teatroentradas.data;

import cl.ignacioaraya.teatroentradas.model.*;
import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class Inicializador {

    public static void cargarDatos() {
        
        // Cliente de prueba
        //DataStore.agregarCliente(new Cliente(1, "Juan Perez", AppConfig.TipoCliente.ESTUDIANTE));

        // Asientos: inicializar todos según CAPACIDAD
        int capacidad = AppConfig.MAX_ASIENTOS;
        int columnas = AppConfig.COLUMNAS_POR_FILA;
        int filas = (int) Math.ceil((double) capacidad / columnas);

        int numero = 1;
        for (int f = 1; f <= filas; f++) {
            for (int c = 1; c <= columnas; c++) {
                if (numero <= capacidad) {
                    DataStore.agregarAsiento(new Asiento(numero, f, c));
                    numero++;
                }
            }
        }
    }
}
