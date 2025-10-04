package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Descuento;
import cl.ignacioaraya.teatroentradas.model.Evento;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    
    public static void Main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        int opcion;
        
        // Listas dinámicas
        List<Evento> eventos = new ArrayList<>();
        List<Descuento> descuentos = new ArrayList<>();
        
        // Evento de ejemplo
        Evento evento1 = new Evento(1, "Obra: Esperando a Godot", new java.util.Date(126, 1, 15));
        eventos.add(evento1);

        // Descuentos solicitados
        descuentos.add(new Descuento(AppConfig.TipoCliente.ESTUDIANTE, AppConfig.DESCUENTO_ESTUDIANTE / 100.0));
        descuentos.add(new Descuento(AppConfig.TipoCliente.ADULTO_MAYOR, AppConfig.DESCUENTO_ADULTO_MAYOR / 100.0));

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | " + AppConfig.NOMBRE_TEATRO);
                    //+ " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Mostrar asientos");
            System.out.println("3. Vender entradas");
            System.out.println("4. Configurar descuentos");
            System.out.println("5. Listar Ventas (Reporte)");
            System.out.println("6. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                //case 1 -> gestionarClientesUI(sc);
                //case 2 -> mostrarAsientosTeatroUI(sc);
                //case 3 -> venderEntradaUI(sc);
                //case 4 -> configurarDescuentosUI(sc);
                //case 5 -> listarVentasUI();
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 5);
        
        // Cerrar recursos
        sc.close();
    
    }
    
}
