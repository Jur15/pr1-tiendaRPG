/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author moral
 */
public class Articulo {

    private String nombre;
    private String descripcion;
    private int precio;
    //Stats
    private int ataque;
    private int defensa;
    private int velocidad;
    private int ataqueMagico;
    private int defensaMagica;

    public Articulo(String nombre, String descripcion, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

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

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getAtaqueMagico() {
        return ataqueMagico;
    }

    public void setAtaqueMagico(int ataqueMagico) {
        this.ataqueMagico = ataqueMagico;
    }

    public int getDefensaMagica() {
        return defensaMagica;
    }

    public void setDefensaMagica(int defensaMagica) {
        this.defensaMagica = defensaMagica;
    }

}
