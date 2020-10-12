/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import modelo.Articulo;

/**
 *
 * @author moral
 */
public class ListaArticulosRenderer extends ElementoListaArticulo implements ListCellRenderer<Articulo> {

    public ListaArticulosRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Articulo> list, Articulo value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setNombreArt(value.getNombre());
        this.setPrecioArt(value.getPrecio());
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        return this;
    }

}
