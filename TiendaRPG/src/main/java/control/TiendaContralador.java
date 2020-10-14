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
    private String[] opcionesDialogo = new String[]{"Sí", "No"};

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

        //Inicializa la informacion del jugador
        actualizarJugador();

        //Inicializa la lista de articulos para comprar
        JList listaComprar = ventana.getListaComprar();
        //listaComprar.setVisibleRowCount(5);
        DefaultListModel<Articulo> listaComprarModel = new DefaultListModel<>();
        listaComprarModel.addAll(articulos);
        listaComprar.setModel(listaComprarModel);
        listaComprar.setCellRenderer(new ListaArticulosRenderer(false));
        listaComprar.addListSelectionListener(this);

        //Inicializa la lista de articulos para vender
        JList listaVender = ventana.getListaVender();
        DefaultListModel<Articulo> listaVenderModel = new DefaultListModel<>();
        listaVenderModel.addAll(jugador.getInventario());
        listaVender.setModel(listaVenderModel);
        listaVender.setCellRenderer(new ListaArticulosRenderer(true));
        listaVender.addListSelectionListener(this);

        //Asocia metodos con acciones en la interfaz
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
                if (seleccionado.getTipo() != TipoArticulo.Consumible) {
                    int opcionEquipar = JOptionPane.showOptionDialog(ventana, "¿Desea equipar el artículo?", "Equipar artículo",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcionesDialogo, opcionesDialogo[0]);
                    if (opcionEquipar == JOptionPane.YES_OPTION) {
                        switch (seleccionado.getTipo()) {
                            case Arma:
                                jugador.equiparArma(seleccionado);
                                break;
                            case Armadura:
                                jugador.equiparArmadura(seleccionado);
                                break;
                        }
                        equipado = "Sí";
                    }
                }
                //Muestra un dialogo de exito
                JOptionPane.showMessageDialog(ventana, "Articulo comprado con éxito.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ventana, "No tienes suficiente dinero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } //Vender un articulo
        else {
            seleccionado = (Articulo) ventana.getListaVender().getSelectedValue();
            //Obtiene el tipo de articulo para desequiparlo si es necesario
            switch (seleccionado.getTipo()) {
                case Arma:
                    if (jugador.getArmaEquipada() != null && jugador.getArmaEquipada().equals(seleccionado)) {
                        //Desequipa el arma
                        //Si tiene esta arma mas de 1 vez no se desequipa
                        if (Collections.frequency(jugador.getInventario(), seleccionado) == 1) {
                            jugador.desequiparArma();
                            equipado = "No";
                        } else {
                            equipado = "Sí";
                        }
                    }
                    break;
                case Armadura:
                    if (jugador.getArmaduraEquipada() != null && jugador.getArmaduraEquipada().equals(seleccionado)) {
                        //Desequipa la armadura
                        //Si tiene esta armadura mas de 1 vez no se desequipa
                        if (Collections.frequency(jugador.getInventario(), seleccionado) == 1) {
                            jugador.desequiparArmadura();
                            equipado = "No";
                        } else {
                            equipado = "Sí";
                        }
                    }
                    break;
            }
            //Le paga el 75% del precio original
            jugador.setDinero(jugador.getDinero() + seleccionado.getPrecio() * 75 / 100);
            //Quita el objeto del inventario del jugador
            jugador.getInventario().remove(seleccionado);
            //Quita el objeto de la lista de la tienda si es el ultimo que tiene,
            //oculta/reinicia la informacion del articulo en la ventana
            if (Collections.frequency(jugador.getInventario(), seleccionado) == 0) {
                modelListaVender.removeElement(seleccionado);
                ventana.getListaVender().getSelectionModel().clearSelection();
                ventana.ocultarCambioStats();
                ventana.cambiarVisibilidadDescripcion(false);
            }
        }
        //Actualiza la informacion del jugador
        actualizarJugador();
        //Actualiza la comparacion de stats 
        if (equipado.equals("Sí")) {
            mostrarCambioStats(seleccionado, ventana.isModoComprar());
        }
        //Actualiza los valores de equipado y cantidad mientras aun tenga el articulo
        int cantArticulo = Collections.frequency(jugador.getInventario(), seleccionado);
        if (cantArticulo != 0) {
            actualizarValores(equipado, cantArticulo);
        }
    }

    //Metodo llamado cuando se selecciona un articulo en la lista Comprar/Vender
    @Override
    public void valueChanged(ListSelectionEvent e) {
        //Reinicia el cambio de stats
        ventana.ocultarCambioStats();
        JList listaArt = (JList) e.getSource();
        //Si la lista esta vacia no necesita mostrar nada
        if (listaArt.isSelectionEmpty()) {
            return;
        }
        //Obtiene el articulo seleccionado
        Articulo art = (Articulo) listaArt.getModel().getElementAt(listaArt.getSelectedIndex());
        //Muestra el cambio de stats cuando esta comprando
        if (ventana.isModoComprar()) {
            mostrarCambioStats(art, true);
        }
        //Muestra el nombre del articulo
        ventana.getLabelNombreArt().setText(art.getNombre());
        //Muestra la descripcion del artiuclo
        ventana.getTextoDescrip().setText(art.getDescripcion());
        //Muestra si esta equipado
        String valorEquipado = "No";
        switch (art.getTipo()) {
            case Arma:
                if (jugador.getArmaEquipada() != null && jugador.getArmaEquipada().equals(art)) {
                    valorEquipado = "Si";
                    //Muestra el cambio de stats si se vende el arma equipada
                    mostrarCambioStats(art, false);
                }
                break;
            case Armadura:
                if (jugador.getArmaduraEquipada() != null && jugador.getArmaduraEquipada().equals(art)) {
                    valorEquipado = "Si";
                    //Muestra el cambio de stats si se vende la armadura equipada
                    mostrarCambioStats(art, false);
                }
                break;
            case Consumible:
                valorEquipado = "N/A";
                break;

        }
        //Muestra la cantidad que posee el jugador
        int cantidadInventario = Collections.frequency(jugador.getInventario(), art);
        actualizarValores(valorEquipado, cantidadInventario);
        //Hace visible las etiquetas en la ventana
        ventana.cambiarVisibilidadDescripcion(true);
    }

    //Muestra como cambiarian los stats
    //Si el modo es vender compara los stats con los stats base del jugador
    //Si el modo es comprar le agrega los stats del articulo
    private void mostrarCambioStats(Articulo art, boolean modoComprar) {
        if (art.getTipo() != TipoArticulo.Consumible) {
            int ata = jugador.getAtaque(),
                    def = jugador.getDefensa(),
                    ataMag = jugador.getAtaqueMag(),
                    defMag = jugador.getDefensaMag(),
                    vel = jugador.getVelocidad();
            if (modoComprar) {
                ata += art.getAtaque();
                def += art.getDefensa();
                ataMag += art.getAtaqueMagico();
                defMag += art.getDefensaMagica();
                vel += art.getVelocidad();
            }
            ventana.mostrarCambioStats(ata, def, ataMag, defMag, vel);
        }
    }

    private void actualizarValores(String equipado, int cantidad) {
        ventana.getLabelEquipadoValor().setText(equipado);
        ventana.getLabelCantidadValor().setText(Integer.toString(cantidad));
    }

    private void actualizarJugador() {
        ventana.setNombreJugador(jugador.getNombre());
        ventana.setDineroJugador(jugador.getDinero());
        //Stats
        int ata = jugador.getAtaque(),
                def = jugador.getDefensa(),
                ataMag = jugador.getAtaqueMag(),
                defMag = jugador.getDefensaMag(),
                vel = jugador.getVelocidad();
        //Si tiene un arma equipada le suma los stats
        Articulo arma = jugador.getArmaEquipada();
        if (arma != null) {
            ata += arma.getAtaque();
            def += arma.getDefensa();
            ataMag += arma.getAtaqueMagico();
            defMag += arma.getDefensaMagica();
            vel += arma.getVelocidad();
        }
        //Si tiene una armadura equipada le suma los stats
        Articulo armadura = jugador.getArmaduraEquipada();
        if (armadura != null) {
            ata += armadura.getAtaque();
            def += armadura.getDefensa();
            ataMag += armadura.getAtaqueMagico();
            defMag += armadura.getDefensaMagica();
            vel += armadura.getVelocidad();
        }
        ventana.setStatAtaque(ata);
        ventana.setStatDefensa(def);
        ventana.setStatAtaqueMag(ataMag);
        ventana.setStatDefensaMag(defMag);
        ventana.setStatVelocidad(vel);
    }
}
