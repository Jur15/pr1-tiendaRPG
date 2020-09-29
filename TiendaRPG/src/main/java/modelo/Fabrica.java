/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author moral
 */
public class Fabrica {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String nombreArchivo;

    public Fabrica() {
        this.nombreArchivo = "ResultadosBusqueda.json";
    }

    public Fabrica(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    //Obtiene los resultados de Amazon al buscar las palabras en el parametro "busqueda"
    public void obtenerArt(String busqueda) {
        OkHttpClient cliente = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://amazon-product-reviews-keywords.p.rapidapi.com/product/search?category=aps&country=US&keyword=" + busqueda)
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
        gson.toJson(jObject, new FileWriter(this.nombreArchivo));
        System.out.println("Resultados guardados como " + this.nombreArchivo);
    }

    //Toma los resultados del JSON y los transforma en articulos de la tienda
    public ArrayList<Articulo> construirArticulos() {
        //TODO
        return null;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}
