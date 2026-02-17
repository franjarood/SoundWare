package modelo.contenido;
import enums.GeneroMusical;
import excepciones.contenido.ArchivoAudioNoEncontradoException;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.contenido.LetraNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
import interfaces.iReproducible;
import interfaces.iDescargable;
import modelo.artistas.Album;
import modelo.artistas.Artista;

// Cancion - Contenido musical de un artista
public class Cancion extends Contenido implements iReproducible, iDescargable {

    // ATRIBUTOS

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

    // CONSTRUCTORES

    public Cancion(String titulo, int duracionSegundos, Artista artista, GeneroMusical genero) throws DuracionInvalidaException {
        super(titulo, duracionSegundos);
        this.artista = artista;
        this.genero = genero;
        this.audioURL = "audio/default/" + getId();
        this.ISRC = generarISRC();
    }

    public Cancion(String titulo, int duracionSegundos, Artista artista, GeneroMusical genero,
                   String letra, boolean explicit) throws DuracionInvalidaException {

        super(titulo, duracionSegundos);

        this.artista = artista;
        this.genero = genero;
        this.letra = letra;
        this.explicit = explicit;

        this.audioURL = "audio/default/" + getId();
        this.ISRC = generarISRC();
    }

    // MÉTODOS PRIVADOS

    // Genera un código ISRC único para la canción
    private String generarISRC() {
        return "ISRC-" + getId();
    }

    // MÉTODOS PÚBLICOS

    // Obtiene la letra de la canción si está disponible
    public String obtenerLetra() throws LetraNoDisponibleException {

        if (letra == null || letra.isEmpty()) {
            throw new LetraNoDisponibleException();
        }

        return letra;
    }

    // Verifica si la canción tiene contenido explícito
    public boolean esExplicit() {
        return explicit;
    }

    // Cambia el género musical de la canción
    public void cambiarGenero(GeneroMusical nuevoGenero) {
        this.genero = nuevoGenero;
    }

    // Valida que el archivo de audio exista
    public void validarAudioURL() throws ArchivoAudioNoEncontradoException {

        if (audioURL == null || audioURL.isEmpty()) {
            throw new ArchivoAudioNoEncontradoException();
        }
    }

    // GETTERS Y SETTERS

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public String getISRC() {
        return ISRC;
    }


    public boolean isReproduciendo() {
        return reproduciendo;
    }


    public boolean isPausado() {
        return pausado;
    }


    public boolean isDescargado() {
        return descargado;
    }

    public void setDescargado(boolean descargado) {
        this.descargado = descargado;
    }

    // OVERRIDES - Contenido

    @Override
    public void reproducir() throws ContenidoNoDisponibleException {

        if (!disponible) {
            throw new ContenidoNoDisponibleException("El contenido no está disponible");
        }

        aumentarReproducciones();
        reproduciendo = true;
    }

    // IMPLEMENTACIÓN DE INTERFACES - iReproducible

    @Override
    public void play() {
        reproduciendo = true;
        pausado = false;
        System.out.println("Reproduciendo: " + getTitulo());

    }

    @Override
    public void pause() {
        pausado = true;
        reproduciendo = false;
        System.out.println("Pausado: " + getTitulo());
    }

    @Override
    public void stop() {
        reproduciendo = false;
        pausado = false;
        System.out.println("Detenido: " + getTitulo());

    }

    @Override
    public int getDuracion() {
        return getDuracionSegundos();
    }

    // IMPLEMENTACIÓN DE INTERFACES - iDescargable

    @Override
    public boolean descargar() throws LimiteDescargasException, ContenidoYaDescargadoException {
        if (descargado) {
            throw new ContenidoYaDescargadoException();
        }
        descargado = true;
        return true;
    }

    @Override
    public boolean eliminarDescarga() {
        if (descargado) {
            descargado = false;
            return true;
        }
        return false;
    }

    @Override
    public int espacioRequerido() {
        //Aproximacion: 1MB por cada 60 segundos de audio
        return (duracionSegundos / 60) + 1;
    }




    @Override
    public String toString() {
        return "Cancion: " + getTitulo() +
                " | Artista: " + artista.getNombreArtistico() +
                " | Duracion: " + getDuracionFormateada();
    }



}
