package modelo.contenido;

import enums.CategoriaPodcast;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.contenido.EpisodioNoEncontradoException;
import excepciones.contenido.TranscripcionNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
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


    public Podcast(String titulo, int duracionSegundos, Creador creador, int numeroEpisodio, int temporada, CategoriaPodcast categoria) throws DuracionInvalidaException {
        super(titulo, duracionSegundos);
        this.creador = creador;
        this.numeroEpisodio = numeroEpisodio;
        this.temporada = temporada;
        this.categoria = categoria;
    }

    public Podcast(String titulo, int duracionSegundos, Creador creador, int numeroEpisodio, int temporada, CategoriaPodcast categoria, String descripcion) throws DuracionInvalidaException {
        super(titulo, duracionSegundos);
        this.creador = creador;
        this.numeroEpisodio = numeroEpisodio;
        this.temporada = temporada;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }


    // Implementación de Contenido
    @Override
    public void reproducir() throws ContenidoNoDisponibleException {

        if (!disponible) {
            throw new ContenidoNoDisponibleException("El contenido no está disponible");
        }

        aumentarReproducciones();
        reproduciendo = true;
    }


    @Override
    public void play() {
        reproduciendo = true;
        pausado = false;
        System.out.println("Reproduciendo podcast: " + getTitulo());
    }

    @Override
    public void pause() {

        if (reproduciendo) {
            pausado = true;
            reproduciendo = false;
            System.out.println("Podcast pausado: " + getTitulo());
        }
    }

    @Override
    public void stop() {

        reproduciendo = false;
        pausado = false;
        System.out.println("Podcast detenido: " + getTitulo());
    }

    @Override
    public int getDuracion() {
        return getDuracionSegundos();
    }




    @Override
    public boolean descargar() throws LimiteDescargasException, ContenidoYaDescargadoException {

        if (descargado) {
            throw new ContenidoYaDescargadoException("El podcast ya está descargado");
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
        return getDuracionSegundos();
    }




    public String obtenerDescripcion() {

        return "Podcast: " + getTitulo() +
                " | Creador: " + creador +
                " | Temporada: " + temporada +
                " | Episodio: " + numeroEpisodio;
    }

    public void agregarInvitado(String nombre) {

        if (nombre != null && !nombre.isEmpty()) {

            if (invitados == null) {
                invitados = new ArrayList<>();
            }

            if (!invitados.contains(nombre)) {
                invitados.add(nombre);
            }
        }
    }

    public boolean esTemporadaNueva() {
        return temporada == 1;
    }

    public String obtenerTranscripcion() throws TranscripcionNoDisponibleException {

        if (transcripcion == null || transcripcion.isEmpty()) {
            throw new TranscripcionNoDisponibleException();
        }

        return transcripcion;
    }

    public void validarEpisodio() throws EpisodioNoEncontradoException {

        if (temporada <= 0 || numeroEpisodio <= 0) {
            throw new EpisodioNoEncontradoException();
        }
    }


    //getters y setters

    public Creador getCreador() {
        return creador;
    }

    public void setCreador(Creador creador) {
        this.creador = creador;
    }

    public int getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(int numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaPodcast getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPodcast categoria) {
        this.categoria = categoria;
    }

    public ArrayList<String> getInvitados() {
        if (invitados == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(invitados);
    }

    public String getTranscripcion() {
        return transcripcion;
    }

    public void setTranscripcion(String transcripcion) {
        this.transcripcion = transcripcion;
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


    @Override
    public String toString() {
        return "Podcast: " + getTitulo() +
                " | Creador: " + creador +
                " | Temporada: " + temporada +
                " | Episodio: " + numeroEpisodio +
                " | Duracion: " + getDuracionFormateada();
    }





}
