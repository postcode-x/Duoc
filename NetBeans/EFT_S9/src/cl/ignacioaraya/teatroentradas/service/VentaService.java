package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import java.util.ArrayList;
import java.util.List;

public class VentaService {
    
    // Contador para asientos
    private static int contadorAsientos = 0;
    
    // Lista de todos los asientos del teatro
    private final List<Asiento> asientos = new ArrayList<>();

    // Lista de boletas emitidas
    //private final List<Boleta> boletas = new ArrayList<>();

    // Lista de asientos que el usuario tiene en el carrito
    private final List<Asiento> carrito = new ArrayList<>();
    
    public VentaService() {
        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            for (int fila = 1; fila <= AppConfig.FILAS_POR_ZONA; fila++) {
                for (int columna = 1; columna <= AppConfig.ASIENTOS_POR_FILA; columna++) {
                    contadorAsientos += 1;
                    asientos.add(new Asiento(contadorAsientos, zona, fila, columna));
                }
            }
        }
    }
    
    // Muestra el layout del teatro con los asientos por zona
    public String mostrar() {
        StringBuilder layoutTeatro = new StringBuilder();

        for (AppConfig.Zona zona : AppConfig.ZONAS) {
            layoutTeatro.append(zona.nombre())
                         .append(" | $").append(Math.round(zona.precio()))
                         .append("\n");

            for (Asiento asiento : asientos) {
                if (asiento.getZona().equals(zona)) {
                    layoutTeatro.append(asiento.mostrarSimple()).append("  ");
                    
                    if (asiento.getColumna() == AppConfig.ASIENTOS_POR_FILA) {
                        layoutTeatro.append("\n");
                    }
                }
            }
            layoutTeatro.append("\n");
        }

        return layoutTeatro.toString();
    }
    
    // Retorna cantidad de asientos disponibles
    public int getAsientosDisponibles(){
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.DISPONIBLE) {
                contador++;
            }
        }
        return contador;
    }
    
}
