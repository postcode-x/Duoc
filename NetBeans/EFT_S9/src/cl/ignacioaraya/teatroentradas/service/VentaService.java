package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import java.util.ArrayList;
import java.util.List;

public class VentaService {
    
    // Contador para asientos
    private static int contadorAsientos = 0;
    
    // Contados para boletas
    private static int contadorBoletas = 0;
    
    // Lista de todos los asientos del teatro
    private final List<Asiento> asientos = new ArrayList<>();

    // Lista de boletas emitidas
    private final List<Boleta> boletas = new ArrayList<>();

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
    
    public int calculaDescuentoPorEdad(int edad){
        if (edad <= AppConfig.EDAD_MAX_NINO){
            return AppConfig.DESCUENTO_NINOS;
        }
        if (AppConfig.EDAD_MAX_NINO < edad && edad <= AppConfig.EDAD_MAX_ESTUDIANTE) {
            return AppConfig.DESCUENTO_ESTUDIANTE;
        }
        if (edad >= AppConfig.EDAD_MIN_ADULTO_MAYOR) {
            return AppConfig.DESCUENTO_ADULTO_MAYOR;
        }
        return 0;
    }
    
    public int calculaDescuentoPorGenero(boolean esMujer){
        return esMujer ? AppConfig.DESCUENTO_MUJER : 0;
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
    
    // Marca los asientos del carrito como vendidos y crea boleta
    public int marcarComoVendidos(int descuento) {
        for (Asiento asiento : carrito) {
            asiento.setVendido();
        }
        contadorBoletas++;
        boletas.add(new Boleta(contadorBoletas, new ArrayList<>(carrito), calcularTotalCarrito(), descuento));
        carrito.clear();
        return contadorBoletas;
    }
    
    // Marca los asientos del carrito como disponibles si no se compro
    public void marcarComoDisponibles() {
        for (Asiento asiento : carrito) {
            if (asiento.getEstado() == AppConfig.Estado.SELECCIONADO) {
                asiento.setDisponible();
            }
        }
        carrito.clear();
    }
    
    // Calcula total de los asientos en el carrito
    public double calcularTotalCarrito() {
        double totalCheckout = 0;
        for (Asiento asiento : carrito) {
            totalCheckout += asiento.getPrecio();
        }
        return totalCheckout;
    }
    
    // Calcula total de los asientos en el carrito con descuento
    public double calcularTotalCarritoConDescuento(int descuentoFinal) {
        double totalCheckout = 0;
        for (Asiento asiento : carrito) {
            totalCheckout += asiento.getPrecio() * (1 - descuentoFinal / 100.0);
        }
        return totalCheckout;
    }
    
    // Muestra los asientos en el carrito
    public String mostrarAsientosCarrito() {
        if (carrito.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (Asiento asiento : carrito) {
            sb.append(asiento.mostrar()).append(" | ");
        }
        sb.setLength(sb.length() - 3); // eliminar ultimo " | "

        return sb.toString();
    }
    
    // Getters
    public List<Asiento> getAsientos() {
        return asientos;
    }
    
    public List<Boleta> getBoletas() {
        return boletas;
    }
    
    public List<Asiento> getCarrito() {
        return carrito;
    }
    
    public int getNumeroBoletas() {
        return boletas.size();
    }
    
    public int getAsientosVendidos() {
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.VENDIDO) {
                contador++;
            }
        }
        return contador;
    }
    
    // Elimina una boleta existente por número
    public boolean eliminarVenta(int numeroBoleta) {
        for (int i = 0; i < boletas.size(); i++) {
            Boleta boleta = boletas.get(i);
            if (boleta.getNumero() == numeroBoleta) {
                // Liberar los asientos vendidos
                for (Asiento asiento : boleta.getAsientos()) {
                    asiento.setDisponible();
                }
                // Eliminar la boleta del registro
                boletas.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // Genera un resumen general del estado del teatro
    public String generarReporteGeneral() {
        int totalAsientos = asientos.size();
        int asientosVendidos = 0;
        double ingresosTotales = 0;

        for (Asiento asiento : asientos) {
            if (asiento.getEstado() == AppConfig.Estado.VENDIDO) {
                asientosVendidos++;
            }
        }

        for (Boleta boleta : boletas) {
            ingresosTotales += boleta.getTotal();
        }

        int asientosDisponibles = totalAsientos - asientosVendidos;
        int totalBoletas = boletas.size();
        double promedioPorVenta = totalBoletas > 0 ? ingresosTotales / totalBoletas : 0;

        StringBuilder sb = new StringBuilder();
        sb.append("\n--- REPORTE GENERAL ---\n\n");
        sb.append("Total de asientos: ").append(totalAsientos).append("\n");
        sb.append("Asientos vendidos: ").append(asientosVendidos).append("\n");
        sb.append("Asientos disponibles: ").append(asientosDisponibles).append("\n");
        sb.append("Boletas emitidas: ").append(totalBoletas).append("\n");
        sb.append("Ingresos totales: $").append(Math.round(ingresosTotales)).append("\n");
        sb.append("Promedio por venta: $").append(Math.round(promedioPorVenta)).append("\n");

        return sb.toString();
    }

}
