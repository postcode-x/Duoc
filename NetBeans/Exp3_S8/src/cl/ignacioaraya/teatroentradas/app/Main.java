package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.data.DataStore;
import cl.ignacioaraya.teatroentradas.data.Inicializador;
import cl.ignacioaraya.teatroentradas.model.Cliente;
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
        Evento evento = DataStore.eventos.get(0);
        
        int opcion;

        // Menu principal
        do {
            System.out.println("\n--- Menu de Venta | " 
                    + AppConfig.NOMBRE_TEATRO
                    + " (" + ventaService.getAsientosDisponibles() + " asientos disponibles) ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Mostrar asientos");
            System.out.println("3. Vender entradas");
            System.out.println("4. Configurar descuentos");
            System.out.println("5. Gestionar Ventas");
            System.out.println("6. Salir");

            opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> gestionarClientesUI(sc);
                case 2 -> mostrarAsientosTeatroUI(ventaService);
                case 3 -> venderEntradaUI(sc, ventaService, evento);
                case 4 -> configurarDescuentosUI(sc);
                case 5 -> gestionarVentasUI(sc);
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 6);
        
        // Cerrar recursos
        sc.close();
    
    }
    
    // UI para gestionar clientes (registrar, actualizar, eliminar)
    private static void gestionarClientesUI(Scanner sc) {
        boolean salir = false;
        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Actualizar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("0. Volver al menu principal");

            int opcion = InputUtils.leerEntero(sc, "\nSeleccione una opcion: ");

            switch (opcion) {
                case 1 -> registrarCliente(sc);
                case 2 -> actualizarCliente(sc);
                case 3 -> eliminarCliente(sc);
                case 0 -> salir = true;
                default -> System.out.println("Opcion no valida.");
            }
        } while (!salir);
    }
    
    // Registrar cliente nuevo
    private static void registrarCliente(Scanner sc) {
        System.out.println("\n--- Registro de Cliente ---");

        int id = DataStore.clienteCount + 1;

        String nombre;
        do {
            System.out.print("Ingrese nombre del cliente: ");
            nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
            }
        } while (nombre.isEmpty());

        // Mostrar opciones de tipo cliente
        System.out.println("Seleccione tipo de cliente:");
        AppConfig.TipoCliente[] tipos = AppConfig.TipoCliente.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].obtenerNombre());
        }

        int opcionTipo;
        do {
            opcionTipo = InputUtils.leerEntero(sc, "Opcion: ");
        } while (opcionTipo < 1 || opcionTipo > tipos.length);

        AppConfig.TipoCliente tipoSeleccionado = tipos[opcionTipo - 1];

        Cliente cliente = new Cliente(id, nombre, tipoSeleccionado);
        DataStore.agregarCliente(cliente);

        System.out.println("Cliente registrado: " + cliente.getNombre() + 
                           " (" + cliente.getTipo().obtenerNombre() + ")");
    }

    // Actualizar cliente existente
    private static void actualizarCliente(Scanner sc) {
        if (DataStore.clienteCount == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n--- Actualizar Cliente ---");
        for (int i = 0; i < DataStore.clienteCount; i++) {
            Cliente c = DataStore.clientes[i];
            if (c != null) {
                System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getTipo().obtenerNombre() + ")");
            }
        }

        int opcion = InputUtils.leerEntero(sc, "Seleccione cliente a actualizar: ");
        if (opcion < 1 || opcion > DataStore.clienteCount) {
            System.out.println("Opcion invalida.");
            return;
        }

        Cliente cliente = DataStore.clientes[opcion - 1];

        System.out.print("Nuevo nombre (actual: " + cliente.getNombre() + "): ");
        String nuevoNombre = sc.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }

        System.out.println("Seleccione nuevo tipo de cliente (actual: " + cliente.getTipo().obtenerNombre() + "):");
        AppConfig.TipoCliente[] tipos = AppConfig.TipoCliente.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].obtenerNombre());
        }

        int opcionTipo = InputUtils.leerEntero(sc, "Opcion: ");
        if (opcionTipo >= 1 && opcionTipo <= tipos.length) {
            cliente.setTipo(tipos[opcionTipo - 1]);
        }

        System.out.println("Cliente actualizado correctamente.");
    }

    // Eliminar cliente existente
    private static void eliminarCliente(Scanner sc) {
        if (DataStore.clienteCount == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        System.out.println("\n--- Eliminar Cliente ---");
        for (int i = 0; i < DataStore.clienteCount; i++) {
            Cliente c = DataStore.clientes[i];
            if (c != null) {
                System.out.println((i + 1) + ". " + c.getNombre() + " (" + c.getTipo().obtenerNombre() + ")");
            }
        }

        int opcion = InputUtils.leerEntero(sc, "Seleccione cliente a eliminar: ");
        if (opcion < 1 || opcion > DataStore.clienteCount) {
            System.out.println("Opción inválida.");
            return;
        }

        Cliente cliente = DataStore.clientes[opcion - 1];
        
        int idCliente = cliente.getId();
        DataStore.eliminarCliente(idCliente);
        
        // Eliminar ventas asociadas al cliente
        for (int i = 0; i < DataStore.ventaCount; i++) {
            Venta v = DataStore.ventas[i];
            if (v != null) {
                if(v.getCliente().getId() == idCliente){
                    
                    int idVenta = v.getId();
                    // Liberar asiento
                    DataStore.liberarAsiento(v.getAsiento().getNumero());

                    // Elimina venta de array
                    //DataStore.eliminarVenta(idVenta);

                    // Elimina venta desde la lista que habita evento
                    for (Evento evento : DataStore.eventos){
                        evento.eliminarVenta(idVenta);
                    }
                }
            }
        }
        
        System.out.println("Cliente eliminado correctamente.");
    }
    
    // UI para mostrar layout con asientos del teatro
    private static void mostrarAsientosTeatroUI(VentaService ventaService){
        System.out.println("\n--- ASIENTOS ---\n");
        System.out.print(ventaService.mostrar());
    }
    
    // UI para vender entradas
    private static void venderEntradaUI(Scanner sc, VentaService ventaService, Evento evento) {
        System.out.println("\n--- VENTA DE ENTRADA ---");

        // Mostrar lista de clientes disponibles
        if (DataStore.clienteCount == 0) {
            System.out.println("No hay clientes registrados. Registre un cliente primero.");
            return;
        }

        System.out.println("Seleccione un cliente:");
        for (int i = 0; i < DataStore.clienteCount; i++) {
            Cliente cliente = DataStore.clientes[i];
            if (cliente != null) {
                System.out.println((i + 1) + ". " + cliente.getNombre() + " (" + cliente.getTipo().obtenerNombre() + ")");
            }
        }

        int opcionCliente;
        do {
            opcionCliente = InputUtils.leerEntero(sc, "Opcion cliente: ");
        } while (opcionCliente < 1 || opcionCliente > DataStore.clienteCount);

        Cliente clienteSeleccionado = DataStore.clientes[opcionCliente - 1];
        
        boolean seguirComprando = true;
        do {
            
            Venta venta = ventaService.venderEntrada(clienteSeleccionado, evento);

            if (venta != null) {
                System.out.println("\nVenta realizada con exito.");
                System.out.println("Cliente: " + venta.getCliente().getNombre());
                System.out.println("Asiento: " + venta.getAsiento().getNumero() + " (" +
                                   venta.getAsiento().getFila() + "-" + venta.getAsiento().getColumna() + ")");
                System.out.println("Precio final: $" + venta.getPrecio());
                
                seguirComprando = preguntaSeguirComprando(sc);
                
            } else {
                System.out.println("\nNo se pudo realizar la venta (no hay asientos disponibles).");
                seguirComprando = false;
            }
            
        } while (seguirComprando);

    }
    
    // Pregunta al usuario si desea seguir comprando
    private static boolean preguntaSeguirComprando(Scanner sc) {
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, "Desea comprar otro asiento? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }
    
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
        
        // Liberar asiento
        DataStore.liberarAsiento(venta.getAsiento().getNumero());
        
        // Elimina venta de array
        DataStore.eliminarVenta(idVenta);
        
        // Elimina venta desde la lista que habita evento
        for (Evento evento : DataStore.eventos){
            evento.eliminarVenta(idVenta);
        }
                
        System.out.println("Venta eliminada correctamente.");
    }
    
}
