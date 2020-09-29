/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import control.TiendaContralador;
import interfaz.TiendaVentana;

/**
 *
 * @author andre
 */
public class Main {

    public static void main(String[] args) {
        Jugador j = new Jugador();
        TiendaVentana v = new TiendaVentana();
        TiendaContralador control = new TiendaContralador(v, j);

        /* 
        //Obtiene los productos con el API y los guarda en un archivo JSON.
        //No usar, solo quedan 23 usos del API. Por ahora usemos el archivo JSON.
        Fabrica fabrica = new Fabrica();
        fabrica.obtenerArt("iphone");
         */
    }

}
