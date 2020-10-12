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
        Jugador j = new Jugador();
        TiendaVentana v = new TiendaVentana();
        try {
            TiendaContralador control = new TiendaContralador(v, j);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
