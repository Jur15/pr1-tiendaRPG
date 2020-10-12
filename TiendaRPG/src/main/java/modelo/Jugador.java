package modelo;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Jugador {

    private int dinero, ataque, defensa, ataqueMag, defensaMag, velocidad;
    private ArrayList<Articulo> inventario;
    private Articulo armaEquipada, armaduraEquipada;

    public Jugador() {
        this.dinero = 1000;
        this.inventario = new ArrayList<>();
        this.armaEquipada = null;
        this.armaduraEquipada = null;
        this.ataque = 5;
        this.defensa = 3;
        this.ataqueMag = 3;
        this.defensaMag = 2;
        this.velocidad = 3;
    }

    public Jugador(int dinero) {
        this();
        this.dinero = dinero;
    }

    //Dinero
    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    //Stats
    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getAtaqueMag() {
        return ataqueMag;
    }

    public void setAtaqueMag(int ataqueMag) {
        this.ataqueMag = ataqueMag;
    }

    public int getDefensaMag() {
        return defensaMag;
    }

    public void setDefensaMag(int defensaMag) {
        this.defensaMag = defensaMag;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    //Inventario    
    public ArrayList<Articulo> getInventario() {
        return inventario;
    }

    //Art. equipados
    public Articulo getArmaEquipada() {
        return armaEquipada;
    }

    public void setArmaEquipada(Articulo armaEquipada) {
        this.armaEquipada = armaEquipada;
    }

    public Articulo getArmaduraEquipada() {
        return armaduraEquipada;
    }

    public void setArmaduraEquipada(Articulo armaduraEquipada) {
        this.armaduraEquipada = armaduraEquipada;
    }

}
