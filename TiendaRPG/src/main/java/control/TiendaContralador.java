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
        listaComprar.setCellRenderer(new ListaArticulosRenderer(false));
        listaComprar.addListSelectionListener(this);

        //Inicializa la lista en pantalla con los articulos para vender
        JList listaVender = ventana.getListaVender();
        DefaultListModel<Articulo> listaVenderModel = new DefaultListModel<>();
        listaVenderModel.addAll(jugador.getInventario());
        listaVender.setModel(listaVenderModel);
        listaVender.setCellRenderer(new ListaArticulosRenderer(true));
        listaVender.addListSelectionListener(this);

        //Asocia metodos con acciones (clicks,etc) en la interfaz
        //ventana.getBotonMover().addActionListener(e -> moverJugador());
        ventana.getBotonComprarVender().addActionListener(e -> comprarVender());
    }

    private void comprarVender() {
        Articulo seleccionado;
        DefaultListModel modelListaVender = (DefaultListModel) ventana.getListaVender().getModel();
        String equipado = "No";
        //Obtiene el estado de la tienda para saber si es una compra o una venta
        //Comprar un artiuclo
        if (ventana.isModoComprar()) {
            seleccionado = (Articulo) ventana.getListaComprar().getSelectedValue();
            //Verifica que el jugador tenga suficiente dinero
            if (jugador.getDinero() >= seleccionado.getPrecio()) {
                jugador.setDinero(jugador.getDinero() - seleccionado.getPrecio());
                //Agrega el objeto al inventario
                jugador.getInventario().add(seleccionado);
                //Lo agrega a la lista en la tienda si no tiene otro (para evitar repetidos)
                if (Collections.frequency(jugador.getInventario(), seleccionado) == 1) {
                    modelListaVender.addElement(seleccionado);
                }
                //Si no es un consumible pregunta si quiere equipar el articulo
                if (!seleccionado.getTipo().equals("Consumible")) {
                    int opcionEquipar = JOptionPane.showConfirmDialog(ventana, "¿Desea equipar el artículo?", "Equipar artículo", JOptionPane.YES_NO_OPTION);
                    if (opcionEquipar == JOptionPane.YES_OPTION) {
                        switch (seleccionado.getTipo()) {
                            case "Arma":
                                jugador.setArmaEquipada(seleccionado);
                                break;
                            case "Armadura":
                                jugador.setArmaduraEquipada(seleccionado);
                                break;
                        }
                        equipado = "Si";
                    }
                }

                //Muestra un dialogo de exito
                JOptionPane.showMessageDialog(ventana, "Articulo comprado con éxito.");
            } else {
                JOptionPane.showMessageDialog(ventana, "No tienes suficiente dinero.");
            }
        } //Vender un articulo
        else {
            seleccionado = (Articulo) ventana.getListaVender().getSelectedValue();
            //Obtiene el tipo de articulo para desequiparlo si es necesario
            switch (seleccionado.getTipo()) {
                case "Arma":
                    if (jugador.getArmaEquipada().equals(seleccionado)) {
                        //Si tiene esta arma mas de 1 vez no se desequipa
                        if (Collections.frequency(jugador.getInventario(), seleccionado) == 1) {
                            jugador.setArmaEquipada(null);
                            equipado = "No";
                        }
                    }
                    break;
                case "Armadura":
                    if (jugador.getArmaduraEquipada().equals(seleccionado)) {
                        //Si tiene esta armadura mas de 1 vez no se desequipa
                        if (Collections.frequency(jugador.getInventario(), seleccionado) == 1) {
                            jugador.setArmaduraEquipada(null);
                            equipado = "No";
                        }
                    }
                    break;
            }
            //Le paga el 75% del precio original
            jugador.setDinero(jugador.getDinero() + seleccionado.getPrecio() * 75 / 100);
            //Quita el objeto del inventario del jugador
            jugador.getInventario().remove(seleccionado);
            //Quita el objeto de la lista de la tienda si es el ultimo que tiene
            //y actualiza la seleccion en pantalla
            if (Collections.frequency(jugador.getInventario(), seleccionado) == 0) {
                modelListaVender.removeElement(seleccionado);
                ventana.getListaVender().getSelectionModel().clearSelection();
                return;
            }
        }
        //Actualiza los valores de equipado y cantidad solo si aun quedan articulos
        int cantArticulo = Collections.frequency(jugador.getInventario(), seleccionado);
        if(cantArticulo != 0) {
            actualizarValores(equipado, cantArticulo);
        }
    }

    //Metodo llamado cuando se selecciona un articulo en la lista Comprar/Vender
    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList listaArt = (JList) e.getSource();
        //Si la lista esta vacia reinicia la descripcion
        if (listaArt.isSelectionEmpty()) {
            ventana.cambiarVisibilidadDescripcion(false);
            return;
        }
        Articulo art = (Articulo) listaArt.getModel().getElementAt(listaArt.getSelectedIndex());
        ventana.getLabelNombreArt().setText(art.getNombre());
        ventana.getTextoDescrip().setText(art.getDescripcion());
        String valorEquipado = "No";
        switch (art.getTipo()) {
            case "Arma":
                if (jugador.getArmaEquipada() != null && jugador.getArmaEquipada().equals(art)) {
                    valorEquipado = "Si";
                }
                break;
            case "Armadura":
                if (jugador.getArmaduraEquipada() != null && jugador.getArmaduraEquipada().equals(art)) {
                    valorEquipado = "Si";
                }
                break;
        }
        int cantidadInventario = Collections.frequency(jugador.getInventario(), art);
        actualizarValores(valorEquipado, cantidadInventario);
        ventana.cambiarVisibilidadDescripcion(true);
    }

    private void actualizarValores(String equipado, int cantidad) {
        ventana.getLabelEquipadoValor().setText(equipado);
        ventana.getLabelCantidadValor().setText(Integer.toString(cantidad));
    }
}
