package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.data.DataStore;
import cl.ignacioaraya.teatroentradas.model.*;
import cl.ignacioaraya.teatroentradas.config.AppConfig;

public class VentaService {

    // Muestra el layout del teatro con los asientos por zona
    public String mostrar() {
         StringBuilder layoutTeatro = new StringBuilder();

        for (int i = 0; i < DataStore.asientoCount; i++) {
            Asiento asiento = DataStore.asientos[i];
            // Mostrar solo número y estado (Disponible/Vendido)
            String estado = asiento.getEstado() == AppConfig.Estado.DISPONIBLE ? "Disp." : "Vend.";
            layoutTeatro.append(String.format("%2d(%s)  ", asiento.getNumero(), estado));

            // Salto de línea al final de cada fila
            if (asiento.getColumna() == AppConfig.COLUMNAS_POR_FILA) {
                layoutTeatro.append("\n");
            }
        }

        layoutTeatro.append("\n");
        return layoutTeatro.toString();
    }

}
