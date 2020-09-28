/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import interfaz.*;
import modelo.*;

/**
 *
 * @author moral
 */
public class TiendaContralador {
    private TiendaVentana ventana;
    private Jugador jugador;

    public TiendaContralador(TiendaVentana ventana, Jugador jugador) {
        this.ventana = ventana;
        this.jugador = jugador;
        initControlador();
    }
    
    private void initControlador() {
        ventana.getBotonMover().addActionListener(e -> moverJugador());
    }
    
    private void moverJugador() {
        jugador.moverse();
    }
}
