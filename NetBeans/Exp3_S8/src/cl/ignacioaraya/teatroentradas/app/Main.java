package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.data.DataStore;
import cl.ignacioaraya.teatroentradas.data.Inicializador;
import cl.ignacioaraya.teatroentradas.model.Descuento;
import cl.ignacioaraya.teatroentradas.model.Evento;
import cl.ignacioaraya.teatroentradas.model.Venta;
import cl.ignacioaraya.teatroentradas.service.VentaService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        Inicializador.cargarDatos();
        VentaService ventaService = new VentaService();

        int opcion;

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | " + AppConfig.NOMBRE_TEATRO);
                    //+ " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Mostrar asientos");
            System.out.println("3. Vender entradas");
            System.out.println("4. Configurar descuentos");
            System.out.println("5. Gestionar Ventas");
            System.out.println("6. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                //case 1 -> gestionarClientesUI(sc);
                case 2 -> mostrarAsientosTeatroUI(ventaService);
                //case 3 -> venderEntradaUI(sc);
                case 4 -> configurarDescuentosUI(sc);
                case 5 -> gestionarVentasUI(sc);
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 6);
        
        // Cerrar recursos
        sc.close();
    
    }
    
    // UI para gestionar clientes
    
    // UI para mostrar layout con asientos del teatro
    private static void mostrarAsientosTeatroUI(VentaService ventaService){
        System.out.println("\n--- ASIENTOS ---\n");
        System.out.print(ventaService.mostrar());
    }
    
    // UI para vender entradas
    
    // UI para activar o desactivar descuentos
    private static void configurarDescuentosUI(Scanner sc){
        boolean salir = false; 
        do {
            System.out.println("\n--- CONFIGURAR DESCUENTOS ---");
            for(Descuento descuento: DataStore.descuentos){
                System.out.println( (DataStore.descuentos.indexOf(descuento) + 1)+ ")" + descuento.toString() + " - estado: " + (descuento.getEstado() == true ? "Activo" : "Inactivo"));
            }
            System.out.println("0) Salir a menu principal");
            
            int opcion = InputUtils.leerEntero(sc, "\nPara activar o desactivar un descuento, ingresa el numero correspondiente: ");
            if (opcion > 0  && opcion <= DataStore.descuentos.size()) {
                boolean estado = DataStore.descuentos.get(opcion - 1).getEstado();
                DataStore.descuentos.get(opcion - 1).setEstado(!estado);
            } else if (opcion == 0){
                salir = true;
            } else {
                System.out.println("\nOpcion no valida, intente nuevamente.");
            }
            
        } while (!salir);
    }   
    
    // UI para gestionar ventas (listar, eliminar)
    private static void gestionarVentasUI(Scanner sc) {
        boolean salir = false;
        do {
            System.out.println("\n--- GESTION DE VENTAS ---");
            System.out.println("1. Listar ventas");
            System.out.println("2. Eliminar venta");
            System.out.println("0. Volver al menu principal");

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> listarVentas();
                case 2 -> eliminarVenta(sc);
                case 0 -> salir = true;
                default -> System.out.println("Opcion no valida.");
            }
        } while (!salir);
    }
    
    // Listar ventas realizadas
    private static void listarVentas() {
        System.out.println("\n--- LISTADO DE VENTAS ---");

        if (DataStore.ventaCount == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        double total = 0;

        for (int i = 0; i < DataStore.ventaCount; i++) {
            Venta venta = DataStore.ventas[i];
            if (venta != null) {
                System.out.println("Venta #" + venta.getId() 
                    + " | Cliente: " + venta.getCliente().getNombre()
                    + " | Asiento: " + venta.getAsiento().getNumero()
                    + " (" + venta.getAsiento().getFila() + "-" + venta.getAsiento().getColumna() + ")"
                    + " | Precio: $" + venta.getPrecio());
                total += venta.getPrecio();
            }
        }

        System.out.println("\nTotal recaudado: $" + total);
    }
    
    // Eliminar venta existente
    private static void eliminarVenta(Scanner sc) {
        if (DataStore.ventaCount == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        System.out.println("\n--- Eliminar Venta ---");
        for (int i = 0; i < DataStore.ventaCount; i++) {
            Venta v = DataStore.ventas[i];
            if (v != null) {
                System.out.println((i + 1) + ". " + v.toString());
            }
        }

        int opcion = InputUtils.leerEntero(sc, "Seleccione venta a eliminar: ");
        if (opcion < 1 || opcion > DataStore.ventaCount) {
            System.out.println("Opcion invalida.");
            return;
        }

        Venta venta = DataStore.ventas[opcion - 1];
        int idVenta = venta.getId();
        
        // Elimina venta de array
        DataStore.eliminarVenta(idVenta);
        
        // Elimina venta desde la lista que habita evento
        for (Evento evento : DataStore.eventos){
            evento.eliminarVenta(idVenta);
        }
        
        // Libera Asiento TODO
                
        System.out.println("Cliente eliminado correctamente.");
    }
    
}
