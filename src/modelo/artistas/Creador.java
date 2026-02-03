package modelo.artistas;

import enums.CategoriaPodcast;
import modelo.contenido.Podcast;

import java.util.ArrayList;
import java.util.HashMap;

public class Creador {

    private String id;
    private String nombreCanal;
    private String nombre;
    private ArrayList<Podcast> episodios;
    private int suscriptores;
    private String descripcion;
    private HashMap<String, String> redesSociales;
    private ArrayList<CategoriaPodcast> categoriasPrincipales;

    private static final int MAX_EPISODIOS = 500;

}
