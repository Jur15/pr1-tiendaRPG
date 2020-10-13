package control;

import modelo.*;
import interfaz.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author moral
 */
public class TiendaContralador implements ListSelectionListener {

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
        
        //Inicializa la lista en pantalla con los articulos para comprar
        JList listaComprar = ventana.getListaComprar();
        //listaComprar.setVisibleRowCount(5);
        DefaultListModel<Articulo> listaComprarModel = new DefaultListModel<>();
        listaComprarModel.addAll(articulos);
        listaComprar.setModel(listaComprarModel);
        listaComprar.setCellRenderer(new ListaArticulosRenderer());
        listaComprar.addListSelectionListener(this);
        
        //Inicializa la lista en pantalla con los articulos para vender
        JList listaVender = ventana.getListaVender();
        DefaultListModel<Articulo> listaVenderModel = new DefaultListModel<>();
        listaVenderModel.addAll(jugador.getInventario());
        listaVender.setModel(listaVenderModel);
        listaVender.setCellRenderer(new ListaArticulosRenderer());
        listaVender.addListSelectionListener(this);
        
        //Asocia metodos con acciones (clicks,etc) en la interfaz
        //ventana.getBotonMover().addActionListener(e -> moverJugador());
    }

    //Metodo llamado cuando se selecciona un articulo en la lista Comprar/Vender
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList listaArt = (JList) e.getSource();
        Articulo art = (Articulo) listaArt.getModel().getElementAt(listaArt.getSelectedIndex());
        ventana.getLabelNombreArt().setText(art.getNombre());
        ventana.getTextoDescrip().setText(art.getDescripcion());
        String valorEquipado = "No";
        switch(art.getTipo()) {
            case "Arma":
                if(jugador.getArmaEquipada()!=null && jugador.getArmaEquipada().equals(art)) {
                    valorEquipado = "Si";
                }
                break;
            case "Armadura":
                if(jugador.getArmaduraEquipada()!=null && jugador.getArmaduraEquipada().equals(art)) {
                    valorEquipado = "Si";
                }
                break;
        }
        ventana.getLabelEquipadoValor().setText(valorEquipado);
        int cantidadInventario = Collections.frequency(jugador.getInventario(), art);
        ventana.getLabelCantidadValor().setText(Integer.toString(cantidadInventario));
    }
}
