package cl.ignacioaraya.teatroentradas.data;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    // Arreglos
    public static Cliente[] clientes = new Cliente[AppConfig.MAX_CLIENTES];
    public static Asiento[] asientos = new Asiento[AppConfig.MAX_ASIENTOS];
    public static Venta[] ventas     = new Venta[AppConfig.MAX_VENTAS];

    // Contadores para manejar índices en arreglos
    public static int clienteCount = 0;
    public static int asientoCount = 0;
    public static int ventaCount   = 0;
    
    // Listas dinámicas
    public static List<Evento> eventos = new ArrayList<>();
    public static List<Descuento> descuentos = new ArrayList<>();
    
    public static int nextClienteId = 4; // OJO: Este valor continua desde el Inicializador, línea 13
    
    // ---------------------------------
    // Clientes
    // ---------------------------------

    public static int obtenerSiguienteClienteId() {
        return nextClienteId++;
    }
    
    public static void agregarCliente(Cliente cliente) {
        if (clienteCount < AppConfig.MAX_CLIENTES) {
            clientes[clienteCount] = cliente;
            clienteCount++;
        } else {
            System.out.println("No se pueden registrar mas clientes.");
        }
    }
    
    public static void actualizarCliente(int id, String nuevoNombre, AppConfig.TipoCliente nuevoTipo) {
        for (int i = 0; i < clienteCount; i++) {
            if (clientes[i] != null && clientes[i].getId() == id) {
                clientes[i].setNombre(nuevoNombre);
                clientes[i].setTipo(nuevoTipo);
                return;
            }
        }
        System.out.println("Cliente con ID " + id + " no encontrado.");
    }
    
    public static void eliminarCliente(int id) {
        for (int i = 0; i < clienteCount; i++) {
            if (clientes[i] != null && clientes[i].getId() == id) {
                // cuando encuentra el id, desplaza todos los demas clientes a la izquierda
                for (int j = i; j < clienteCount - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                // nullificar la última posición usada y decrementar contador
                clientes[clienteCount - 1] = null;
                clienteCount--;
                return;
            }
        }
        System.out.println("Cliente con ID " + id + " no encontrado.");
    }
    
    // ---------------------------------
    // Asientos
    // ---------------------------------

    public static void agregarAsiento(Asiento asiento) {
        if (asientoCount < AppConfig.MAX_ASIENTOS) {
            asientos[asientoCount] = asiento;
            asientoCount++;
        } else {
            System.out.println("No se pueden registrar mas asientos.");
        }
    }
    
    public static void liberarAsiento(int numeroAsiento) {
        for (int i = 0; i < asientoCount; i++) {
            Asiento asiento = asientos[i];
            if (asiento != null && asiento.getNumero() == numeroAsiento) {
                asiento.setDisponible(); // lo marca como DISPONIBLE
                System.out.println("Asiento " + numeroAsiento + " liberado correctamente.");
                return;
            }
        }
        System.out.println("Asiento con número " + numeroAsiento + " no encontrado.");
    }
    
    // ---------------------------------
    // Ventas
    // ---------------------------------

    public static void agregarVenta(Venta venta) {
        if (ventaCount < AppConfig.MAX_VENTAS) {
            ventas[ventaCount] = venta;
            ventaCount++;
        } else {
            System.out.println("No se pueden registrar mas ventas.");
        }
    }
    
    // Elimina una venta singular
    public static void eliminarVentaSimple(int id) {
        for (int i = 0; i < ventaCount; i++) {
            if (ventas[i] != null && ventas[i].getId() == id) {
                // cuando encuentra el id, desplaza todas las demas ventas a la izquierda
                for (int j = i; j < ventaCount - 1; j++) {
                    ventas[j] = ventas[j + 1];
                }
                ventas[ventaCount - 1] = null;
                ventaCount--;
                return;
            }
        }
        System.out.println("Venta con ID " + id + " no encontrada.");
    }
    
    // Elimina multiples ventas por id de cliente
    public static void eliminarVentasYCompactar(int idCliente){
        int indexNuevo = 0;
        int ventasAEliminar = 0;
        
        // Mueve elementos a conservar hacia la izquierda
        for (int index = 0; index < ventaCount; index++) {
            if (ventas[index] != null && ventas[index].getCliente().getId() != idCliente) {
                ventas[indexNuevo] = ventas[index];
                indexNuevo++;
            }else {
                ventasAEliminar++;
            }        
        }
        
        ventaCount = ventaCount - ventasAEliminar;
        
        // Rellena resto del arreglo con nulls
        while (indexNuevo < ventas.length) {
            ventas[indexNuevo] = null;
            indexNuevo++;
        }
    }
    
}
