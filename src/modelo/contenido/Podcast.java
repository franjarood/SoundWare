package modelo.contenido;

import enums.CategoriaPodcast;
import excepciones.contenido.ContenidoNoDisponibleException;
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







    @Override
    public void reproducir() throws ContenidoNoDisponibleException {

        if (!disponible) {
            throw new ContenidoNoDisponibleException("El contenido no está disponible");
        }
        reproducciones++;
        reproduciendo = true;
    }

}
