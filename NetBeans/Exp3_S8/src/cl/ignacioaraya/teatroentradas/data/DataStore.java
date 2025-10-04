package cl.ignacioaraya.teatroentradas.data;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.*;

public class DataStore {

    // Arreglos
    public static Cliente[] clientes = new Cliente[AppConfig.MAX_CLIENTES];
    public static Asiento[] asientos = new Asiento[AppConfig.MAX_ASIENTOS];
    public static Venta[] ventas     = new Venta[AppConfig.MAX_VENTAS];

    // Contadores para manejar índices en arreglos
    public static int clienteCount = 0;
    public static int asientoCount = 0;
    public static int ventaCount   = 0;
    
    // ---------------------------------
    // Clientes
    // ---------------------------------

    public static void agregarCliente(Cliente cliente) {
        if (clienteCount < AppConfig.MAX_CLIENTES) {
            clientes[clienteCount++] = cliente;
        } else {
            System.out.println("No se pueden registrar mas clientes.");
        }
    }
    
    
    // ---------------------------------
    // Asientos
    // ---------------------------------

    public static void agregarAsiento(Asiento asiento) {
        if (asientoCount < AppConfig.MAX_ASIENTOS) {
            asientos[asientoCount++] = asiento;
        } else {
            System.out.println("No se pueden registrar mas asientos.");
        }
    }
    
    // ---------------------------------
    // Ventas
    // ---------------------------------

    public static void agregarVenta(Venta venta) {
        if (ventaCount < AppConfig.MAX_VENTAS) {
            ventas[ventaCount++] = venta;
        } else {
            System.out.println("No se pueden registrar mas ventas.");
        }
    }
}
