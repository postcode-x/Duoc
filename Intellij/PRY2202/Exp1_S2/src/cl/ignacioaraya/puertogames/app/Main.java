package cl.ignacioaraya.puertogames.app;

import cl.ignacioaraya.puertogames.model.*;

/**
 * Clase principal de la app.
 * <p>
 * Crea instancias de {@link Cliente} y {@link Direccion} para demostrar
 * la composición entre ambas clases y muestra la información por consola.
 * </p>
 */
public class Main {
    public static void main(String[] args) {

        Direccion direccion1 = new Direccion(
                "Av. Los Álamos 123",
                "Santiago",
                "Región Metropolitana"
        );

        Cliente cliente1 = new Cliente(
                "Miguel Valladares",
                "miguel@geocities.com",
                "+56912345678",
                direccion1
        );

        Direccion direccion2 = new Direccion();
        Cliente cliente2 = new Cliente();

        cliente2.setNombre("Graciela Rivas");
        cliente2.setCorreo("graciela@latinmail.com");
        cliente2.setTelefono("+56987654321");

        direccion2.setCalle("Las trufas #1818");
        direccion2.setCiudad("Linares");
        direccion2.setRegion("Maule");
        cliente2.setDireccion(direccion2);

        System.out.println(cliente1 + "\n");
        System.out.println(cliente2);

    }
}
