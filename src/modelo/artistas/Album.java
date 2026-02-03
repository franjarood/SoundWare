package modelo.artistas;

import modelo.contenido.Cancion;

import java.util.ArrayList;
import java.util.Date;



public class Album {

    private String id;
    private String titulo;
    private Artista artista;
    private Date fechaLanzamiento;
    private ArrayList<Cancion> canciones;
    private String portadaURL;
    private String discografica;
    private String tipoAlbum;

    private static final int MAX_CANCIONES = 20;



}
