package modelo;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author moral
 */
public class Fabrica {

    private String nombreArchivo;
    private String[] nombreArticulos, descArticulos;
    private TipoArticulo[] tipoArticulos;
    private int[] preciobaseArticulos, bonusAtaque, bonusDefensa, bonusVelocidad, bonusAtaqueMag, bonusDefensaMag;

    public Fabrica() {
        this.nombreArchivo = "ResultadosBusqueda.json";
        inicializarListas();
    }

    public Fabrica(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        inicializarListas();
    }

    private void inicializarListas() {
        this.nombreArticulos = new String[]{
            //Katanas: 6 en total
            "Katana Regular",
            "Katana Afilada",
            "Filo Nocturno",
            "Filo Celestial",
            "Filo Demoniaco",
            "Masamune",
            //Armaduras: 6 en total
            "Traje de cuero",
            "Traje de piel de oso",
            "Traje de cuero tachonado",
            "Armadura de coraza de dragón",
            "Armadura de piel de grifo",
            "Armadura de Leviatán",
            //Consumibles: 6 en total
            "Pocion sanadora",
            "Pocion magica",
            "Elixir de vida",
            "Elixir magico",
            "Infusion de velocidad",
            "Infusion de defensa"
        };
        this.descArticulos = new String[]{
            //Katanas
            "Una katana ordinaria, utilizada para cortar granos y bambú. No es muy efectiva contra armadura.",
            "Una katana capaz de hacer cortes tan afilados como una navaja.",
            "La hoja de esta katana está hecha con acero negro, útil para pasar desapercibido en la oscuridad.",
            "Una bella katana, forjada y enfriada por la niebla en lo alto de una montaña.",
            "Una hoja brusca, forjada con sangre y llamas infernales.",
            "Un arma legendaria que solo corta a aquellos que merecen la muerte.",
            //Armaduras
            "Un traje hecho con cuero endurecido.",
            "Un traje hecho con el grueso cuero de un oso gigante.",
            "Un traje de cuero tachonado con remaches de metal para proveer más protección.",
            "Una armadura hecha con la piel escamosa de un dragón escupefuego.",
            "Una armadura superior hecha con la piel mágica de un grifo.",
            "Una armadura magnífica hecha con la piel del gran Leviatán, derrotado hace cientos de años en una violenta batalla.",
            //Consumibles
            "Una pocion que restaura 50 HP.",
            "Una pocion que restaura 30 MP.",
            "Un gran frasco de elixir curativo que restaura 250 HP.",
            "Un gran frasco de esencia magica que restaura 150 MP.",
            "Una pocion que aumenta la velocidad temporalmente.",
            "Una pocion que aumenta la defensa temporalmente."
        };
        this.tipoArticulos = new TipoArticulo[]{
            //Katanas
            TipoArticulo.Arma, TipoArticulo.Arma, TipoArticulo.Arma,
            TipoArticulo.Arma, TipoArticulo.Arma, TipoArticulo.Arma,
            //Armaduras
            TipoArticulo.Armadura, TipoArticulo.Armadura, TipoArticulo.Armadura, TipoArticulo.Armadura,
            TipoArticulo.Armadura, TipoArticulo.Armadura,
            //Consumibles
            TipoArticulo.Consumible, TipoArticulo.Consumible, TipoArticulo.Consumible,
            TipoArticulo.Consumible, TipoArticulo.Consumible, TipoArticulo.Consumible
        };

        this.preciobaseArticulos = new int[]{
            //Katanas
            100, 250, 400, 750, 1000, 1500,
            //Armaduras
            100, 150, 300, 700, 900, 1200,
            //Consumibles
            25, 25, 100, 100, 175, 175
        };
        this.bonusAtaque = new int[]{
            //Katanas
            1, 2, 4, 5, 5, 7,
            //Armaduras
            0, 0, 0, 1, 1, 2,
            //Consumibles (no suben stats)
            0, 0, 0, 0, 0, 0
        };
        this.bonusAtaqueMag = new int[]{
            //Katanas
            0, 0, 0, 1, 3, 4,
            //Armaduras
            0, 0, 0, 0, 1, 2,
            //Consumibles (no suben stats)
            0, 0, 0, 0, 0, 0
        };
        this.bonusDefensa = new int[]{
            //Katanas
            0, 0, 1, 2, 3, 4,
            //Armaduras
            1, 2, 3, 5, 6, 8,
            //Consumibles (no suben stats)
            0, 0, 0, 0, 0, 0
        };
        this.bonusDefensaMag = new int[]{
            //Katanas
            0, 0, 0, 1, 2, 3,
            //Armaduras
            0, 1, 2, 5, 7, 8,
            //Consumibles (no suben stats)
            0, 0, 0, 0, 0, 0
        };
        this.bonusVelocidad = new int[]{
            //Katanas
            0, 0, 1, 3, 4, 6,
            //Armaduras
            0, 1, 2, 5, 5, 7,
            //Consumibles (no suben stats)
            0, 0, 0, 0, 0, 0
        };
    }

    //Obtiene los resultados de Amazon al buscar las palabras en el parametro "busqueda"
    public void obtenerArt(String busqueda) {
        OkHttpClient cliente = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://amazon-product-reviews-keywords.p.rapidapi.com/product/search?page=1&category=aps&country=US&keyword=" + busqueda)
                .get()
                .addHeader("x-rapidapi-host", "amazon-product-reviews-keywords.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "30cfcd2fccmsh7c74864d7c404adp194845jsn5b11c33bab2d")
                .build();
        try {
            Response response = cliente.newCall(request).execute();
            guardarJson(response.body().string());
        } catch (IOException ex) {
            //Imprime el error
            ex.printStackTrace();
        }
    }

    //Guarda el resultado obtenido del api en un archivo JSON
    private void guardarJson(String contenido) throws IOException {
        JsonObject jObject = JsonParser.parseString(contenido).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escritorArchivo = new FileWriter(this.nombreArchivo);
        gson.toJson(jObject, escritorArchivo);
        escritorArchivo.close();
        System.out.println("Resultados guardados como " + this.nombreArchivo);
    }

    //Toma los resultados del JSON y los transforma en articulos de la tienda
    public ArrayList<Articulo> construirArticulos() throws IOException {
        ArrayList<Articulo> articulos = new ArrayList<>();
        //Convierte el JSON en un objeto
        Reader lectorArchivo = new BufferedReader(new InputStreamReader(new FileInputStream(this.nombreArchivo), "utf-8"));
        JsonObject busqueda = (JsonObject) JsonParser.parseReader(lectorArchivo);
        lectorArchivo.close();
        //Obtiene la propiedad llamada "products" como un array
        JsonArray productos = busqueda.getAsJsonArray("products");
        //Ciclo que crea los articulos
        for (int i = 0; i < nombreArticulos.length; i++) {
            //Obtiene la propiedad del elemento i llamada "reviews" como un objeto
            JsonObject resenas = productos.get(i).getAsJsonObject().getAsJsonObject("reviews");
            //Obtiene la propiedad "rating" del objeto resenas como un double
            double puntuacionAmazon = resenas.get("rating").getAsDouble();
            //Utiliza las reseñas de Amazon del producto para modificar el precio base.
            //Hace un descuento de (5 - puntuacion)*10 % Ej: 0 estrellas = 50% de descuento
            int descuento = (int) (5 - puntuacionAmazon) * 10;
            int precioTransformado = preciobaseArticulos[i] - preciobaseArticulos[i] * descuento / 100;
            //Crea el articulo
            Articulo nuevo = new Articulo(nombreArticulos[i], descArticulos[i], precioTransformado, tipoArticulos[i]);
            nuevo.setAtaque(bonusAtaque[i]);
            nuevo.setDefensa(bonusDefensa[i]);
            nuevo.setAtaqueMagico(bonusAtaqueMag[i]);
            nuevo.setDefensaMagica(bonusDefensaMag[i]);
            nuevo.setVelocidad(bonusVelocidad[i]);
            //Lo guarda en la lista del resultado
            articulos.add(nuevo);
        }
        return articulos;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}
