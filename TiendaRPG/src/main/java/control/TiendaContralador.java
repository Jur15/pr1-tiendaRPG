package control;

import modelo.*;
import interfaz.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author moral
 */
public class TiendaContralador {

    private TiendaVentana ventana;
    private Jugador jugador;
    private Fabrica fabrica;

    public TiendaContralador(TiendaVentana ventana, Jugador jugador) throws IOException {
        this.ventana = ventana;
        this.jugador = jugador;
        this.fabrica = new Fabrica();
        inicializar();
        ventana.setVisible(true);
    }

    private void inicializar() throws IOException {
        //Inicializa los articulos de la tienda
        //fabrica.obtenerArt("iphone"); Busca articulos con el API
        ArrayList<Articulo> articulos = fabrica.construirArticulos();
        
        JList listaComprar = ventana.getListaComprar();
        //listaComprar.setVisibleRowCount(5);
        DefaultListModel<Articulo> listaComprarModel = new DefaultListModel<>();
        listaComprar.setModel(listaComprarModel);
        listaComprarModel.addAll(articulos);
        listaComprar.setCellRenderer(new ListaArticulosRenderer());
        
        //Asocia metodos con acciones (clicks,etc) en la interfaz
        //ventana.getBotonMover().addActionListener(e -> moverJugador());
    }
}
