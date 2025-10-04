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
        
        // Evento de ejemplo
        Evento evento1 = new Evento(1, "Obra: Esperando a Godot", new java.util.Date(126, 1, 15)); // year-1900
        DataStore.eventos.add(evento1);

        // Descuentos solicitados
        DataStore.descuentos.add(new Descuento(AppConfig.TipoCliente.ESTUDIANTE, AppConfig.DESCUENTO_ESTUDIANTE / 100.0));
        DataStore.descuentos.add(new Descuento(AppConfig.TipoCliente.ADULTO_MAYOR, AppConfig.DESCUENTO_ADULTO_MAYOR / 100.0));
        
    }
}
