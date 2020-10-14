package modelo;

import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Jugador {

    private String nombre;
    private int dinero, ataque, defensa, ataqueMag, defensaMag, velocidad;
    private ArrayList<Articulo> inventario;
    private Articulo armaEquipada, armaduraEquipada;

    public Jugador(String nombre) {
        this.nombre = nombre;
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

    public Jugador(String nombre, int dinero) {
        this(nombre);
        this.dinero = dinero;
    }

    public String getNombre() {
        return nombre;
    }

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

    public int getDefensa() {
        return defensa;
    }

    public int getAtaqueMag() {
        return ataqueMag;
    }

    public int getDefensaMag() {
        return defensaMag;
    }

    public int getVelocidad() {
        return velocidad;
    }

    //Inventario    
    public ArrayList<Articulo> getInventario() {
        return inventario;
    }

    //Art. equipados
    public Articulo getArmaEquipada() {
        return armaEquipada;
    }

    public Articulo getArmaduraEquipada() {
        return armaduraEquipada;
    }

    public void equiparArma(Articulo arma) {
        this.armaEquipada = arma;
    }

    public void desequiparArma() {
        this.armaEquipada = null;
    }

    public void equiparArmadura(Articulo armadura) {
        this.armaduraEquipada = armadura;
    }

    public void desequiparArmadura() {
        this.armaduraEquipada = null;
    }
}
