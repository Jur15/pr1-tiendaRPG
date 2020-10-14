package modelo;

import control.TiendaContralador;
import interfaz.TiendaVentana;
import java.io.IOException;

/**
 *
 * @author andre
 */
public class Main {

    public static void main(String[] args) {
        Jugador j = new Jugador("Hanzo Shimada");
        TiendaVentana v = new TiendaVentana();
        try {
            TiendaContralador control = new TiendaContralador(v, j);
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo con el resultado de la busqueda.");
        }
    }

}
