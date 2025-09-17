package cl.ignacioaraya.teatroentradas.service;

import cl.ignacioaraya.teatroentradas.config.AppConfig;
import cl.ignacioaraya.teatroentradas.model.Asiento;
import cl.ignacioaraya.teatroentradas.model.Boleta;
import java.util.*;

public class TeatroService {
    // El modificador final bloquea la referencia, es decir, asientos no se puede reasignar, pero si mutar
    private final List<Asiento> asientos = new ArrayList<>();
    private Boleta ultimaBoleta;
    private static int asientosDisponibles;
    private static int asientosReservados;
    private static int asientosVendidos;

    public TeatroService() {
    }

    public void reservarAsiento() {
    }

    public void modificarReserva() {
    }

    public void comprarEntrada() {
    }

    public void imprimirBoleta() {
    }
}

