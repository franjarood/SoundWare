package modelo.contenido;

import enums.CategoriaPodcast;
import interfaces.iDescargable;
import interfaces.iReproducible;
import modelo.artistas.Creador;

import java.util.ArrayList;

public class Podcast extends Contenido implements iReproducible, iDescargable {


    private Creador creador;
    private int numeroEpisodio;
    private int temporada;
    private String descripcion;
    private CategoriaPodcast categoria;
    private ArrayList<String> invitados;
    private String transcripcion;
    private boolean reproduciendo;
    private boolean pausado;
    private boolean descargado;








}
