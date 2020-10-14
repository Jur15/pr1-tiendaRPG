package modelo;

/**
 *
 * @author moral
 */
public class Articulo {

    private String nombre;
    private String descripcion;
    private int precio;
    private TipoArticulo tipo;
    //Stats
    private int ataque;
    private int defensa;
    private int velocidad;
    private int ataqueMagico;
    private int defensaMagica;

    public Articulo(String nombre, String descripcion, int precio, TipoArticulo tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
    }

    //Datos
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public TipoArticulo getTipo() {
        return tipo;
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
