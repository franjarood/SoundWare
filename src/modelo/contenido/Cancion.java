package modelo.contenido;
import enums.GeneroMusical;
import interfaces.iReproducible;
import interfaces.iDescargable;
import modelo.artistas.Album;
import modelo.artistas.Artista;



public class Cancion extends Contenido implements iReproducible, iDescargable {

    private String letra;
    private Artista artista;
    private Album album;
    private GeneroMusical genero;
    private String audioURL;
    private boolean explicit;
    private String ISRC;
    private boolean reproduciendo;
    private boolean pausado;
    private boolean descargado;





}
