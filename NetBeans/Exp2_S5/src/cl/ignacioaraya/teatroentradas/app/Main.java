package cl.ignacioaraya.teatroentradas.app;

import cl.ignacioaraya.teatroentradas.config.AppConstants;
import cl.ignacioaraya.teatroentradas.model.Entrada;
import cl.ignacioaraya.teatroentradas.service.VentaService;
import cl.ignacioaraya.teatroentradas.util.InputUtils;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        VentaService ventaService = new VentaService();
        
        // Opciones principales del menú
        String[] menu = {"Venta de entradas", "Promociones", "Busqueda de entradas", "Eliminacion de entradas", "Salir"};
        int opcionMenuPrincipal = -1;
        
        // Loop principal de la aplicación
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ") " + menu[i]);
            }
            
            opcionMenuPrincipal = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            // Ejecutar opción seleccionada
            switch (opcionMenuPrincipal) {
                case 1: 
                    flujoVentaEntradas(sc, ventaService); // flujo de compra de entradas
                    break;

                case 2:
                    configurarPromociones(sc, ventaService); // activar/desactivar promociones
                    break;
                    
                case 3:
                    buscador(sc, ventaService); // búsqueda de entradas
                    break;
                    
                case 4:
                    eliminarEntrada(sc, ventaService); // eliminar entrada por número
                    break;
                    
                case 5: // Salir
                    break;

                default: // Número fuera de rango
                    System.out.println("Opcion no valida.");
            }
            
        } while (opcionMenuPrincipal != 5);
        
        System.out.println("\nHasta luego!");
        sc.close();
    }
    
    // Flujo de venta de entradas (selección, carrito, checkout)
    private static void flujoVentaEntradas(Scanner sc, VentaService ventaService) {
        boolean seguirComprando;
        do {
            // Elegir ubicación y tipo de cliente
            AppConstants.Ubicacion ubicacion = seleccionarUbicacion(sc);
            AppConstants.TipoCliente tipoCliente = seleccionarTipoCliente(sc);
            int precio = ventaService.calcularPrecioEntrada(ubicacion, tipoCliente);
            
            // Agregar entrada al carrito
            ventaService.agregarEntrada(ubicacion, tipoCliente, precio);
            System.out.println("Entrada agregada. Precio: $" + precio);

            seguirComprando = preguntaSeguirComprando(sc); // preguntar si continuar comprando
        } while (seguirComprando);

        // Calcular total final (incluye descuentos)
        int totalFinal = ventaService.calcularTotalConDescuento();
        
        boolean opcionValida = false; 
        // Checkout: pagar o cancelar
        do {
            System.out.println("\n=== CHECKOUT ===");
            System.out.println("Entradas seleccionadas: " + ventaService.getCanasta().size());
            System.out.println("Total a pagar (con descuento aplicado si corresponde): $" + totalFinal);

            int opcion = InputUtils.leerEntero(sc, "1 = Pagar / 0 = Cancelar: ");
            if (opcion == 1) {
                ventaService.confirmarCompra();
                System.out.println("\nCompra realizada con exito. Gracias!");
                opcionValida = true;
            } else if (opcion == 0){
                ventaService.cancelarCompra();
                System.out.println("\nOperacion cancelada. No se guardaron entradas.");
                opcionValida = true;
            } else {
                System.out.println("\nOpcion no valida, intente nuevamente.");
            }
            
        } while (!opcionValida);
    }
    
    // Menú para seleccionar ubicación del asiento
    private static AppConstants.Ubicacion seleccionarUbicacion(Scanner sc) {
        boolean ubicacionSeleccionada = false;
        int opcionUbicacion = -1;
        
        while(!ubicacionSeleccionada){
            System.out.println("\n=== ELEGIR UBICACION ===");
            int i = 1;
            for (AppConstants.Ubicacion u : AppConstants.Ubicacion.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionUbicacion = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionUbicacion < 1 || opcionUbicacion > AppConstants.Ubicacion.values().length) {
                System.out.println("Ubicacion no valida.");
                continue;
            } else {
                ubicacionSeleccionada = true;
            }
        }
        
        return AppConstants.Ubicacion.values()[opcionUbicacion -1];
    }
    
    // Menú para seleccionar tipo de cliente
    private static AppConstants.TipoCliente seleccionarTipoCliente(Scanner sc) {
        boolean tipoClienteSeleccionado = false;
        int opcionTipoCliente = -1;
        
        while(!tipoClienteSeleccionado){
            System.out.println("\n=== ELEGIR TIPO CLIENTE ===");
            int i = 1;
            for (AppConstants.TipoCliente u : AppConstants.TipoCliente.values()) {
                System.out.println(i + ") " + u.obtenerNombre());
                i++;
            }
            opcionTipoCliente = InputUtils.leerEntero(sc, "\nElija una opcion: ");
            
            if (opcionTipoCliente < 1 || opcionTipoCliente > AppConstants.TipoCliente.values().length) {
                System.out.println("Tipo de cliente no valido.");
                continue;
            } else {
                tipoClienteSeleccionado = true;
            }
        }
        
        return AppConstants.TipoCliente.values()[opcionTipoCliente -1];
    }
    
    // Pregunta si el usuario desea seguir comprando
    private static boolean preguntaSeguirComprando(Scanner sc){
        int opcionSeguir;
        do {
            opcionSeguir = InputUtils.leerEntero(sc, "Seguir comprando? 1 = Si / 0 = No: ");
            if (opcionSeguir != 0 && opcionSeguir != 1) {
                System.out.println("Opcion no valida, intente nuevamente.");
            }
        } while (opcionSeguir != 0 && opcionSeguir != 1);

        return opcionSeguir == 1;
    }
    
    // Menú para activar o desactivar promociones
    private static void configurarPromociones(Scanner sc, VentaService ventaService){
        boolean salir = false; 
        do {
            System.out.println("\n=== CONFIGURAR PROMOCIONES ===");
            System.out.println("=== Activa o desactiva promociones vigentes ===");
            System.out.println("1) Descuento por compra de dos entradas: " + (ventaService.getPromocionA() ? "ACTIVA" : "INACTIVA"));
            System.out.println("2) Descuento por compra de tres o mas entradas: " + (ventaService.getPromocionB() ? "ACTIVA" : "INACTIVA"));
            System.out.println("0) Salir a menu principal");
            
            int opcion = InputUtils.leerEntero(sc, "\nPara activar o desactivar una promocion, ingresa el numero correspondiente: ");
            if (opcion == 1) {
                ventaService.switchPromocionA();
            } else if (opcion == 2) {
                ventaService.switchPromocionB();
            } else if (opcion == 0){
                salir = true;
            } else {
                System.out.println("\nOpcion no valida, intente nuevamente.");
            }
            
        } while (!salir);
    }    
    
    // Menú de búsqueda de entradas (por número, ubicación o tipo)
    private static void buscador(Scanner sc, VentaService ventaService){
        int opcionBuscador = -1;
        boolean salir = false; 
        do {
           System.out.println("\n=== BUSCADOR ==="); 
           System.out.println("1) Buscar por numero");
           System.out.println("2) Buscar por ubicacion");
           System.out.println("3) Buscar por tipo");
           System.out.println("0) Salir a menu principal");
           
           opcionBuscador = InputUtils.leerEntero(sc, "\nElija una opcion: ");
           boolean hayResultados = false;
           
           switch (opcionBuscador) {
                case 1:  // Buscar por número de entrada
                    int numero = InputUtils.leerEntero(sc, "\nIngrese un numero: ");
                    if(numero >= 0){
                        System.out.println("\n" + ventaService.getEntradaVendidaPorNumero(numero));
                    } else {
                        System.out.println("\nNumero no valido.");
                    }
                    break;

                case 2: // Buscar por ubicación
                    AppConstants.Ubicacion ubicacion = seleccionarUbicacion(sc);
                    System.out.println();
                    for (Entrada e : ventaService.getEntradasVendidasPorUbicacion(ubicacion)) {
                        System.out.println(e.mostrar());
                        hayResultados = true;
                    }
                    if(!hayResultados) System.out.println("Sin resultados.");
                    break;
                    
                case 3: // Buscar por tipo de cliente
                    AppConstants.TipoCliente tipoCliente = seleccionarTipoCliente(sc);                    
                    System.out.println();
                    for (Entrada e : ventaService.getEntradasVendidasPorTipoCliente(tipoCliente)) {
                        System.out.println(e.mostrar());
                        hayResultados = true;
                    }
                    if(!hayResultados) System.out.println("Sin resultados.");
                    break;
                
                case 0: // salir del buscador
                    salir = true;
                    break;

                default: // Opción fuera de rango
                    System.out.println("Opcion no valida.");
           }
           
        } while (!salir);
    }
    
    // Eliminar entrada por número
    private static void eliminarEntrada(Scanner sc, VentaService ventaService){
        int opcion = -1;
        boolean salir = false; 
        do {
            System.out.println("\n=== ELIMINAR ENTRADA POR NUMERO ==="); 
            System.out.println("0) Salir a menu principal");
           
            int numero = InputUtils.leerEntero(sc, "\nIngrese numero de entrada a eliminar: ");
            if(numero > 0){
                System.out.println((ventaService.eliminarEntradaPorNumero(numero) ? "\nEntrada eliminada correctamente." : "\nNo se elimino entrada."));
            } else if (numero == 0){
                salir = true;
            }
           
        } while(!salir);
    }
}
