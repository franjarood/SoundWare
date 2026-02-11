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

    public Creador(String nombreCanal, String nombre) {
        this.nombreCanal = nombreCanal;
        this.nombre = nombre;

        this.episodios = new ArrayList<>();
        this.redesSociales = new HashMap<>();
        this.categoriasPrincipales = new ArrayList<>();
    }

    public Creador(String nombreCanal, String nombre, String descripcion) {
        this.nombreCanal = nombreCanal;
        this.nombre = nombre;
        this.descripcion = descripcion;

        this.episodios = new ArrayList<>();
        this.redesSociales = new HashMap<>();
        this.categoriasPrincipales = new ArrayList<>();
    }

    // Publica un episodio
    public void publicarPodcast(Podcast episodio) throws LimiteEpisodiosException {

        if (episodios.size() >= MAX_EPISODIOS) {
            throw new LimiteEpisodiosException();
        }

        episodios.add(episodio);
    }


    // Genera estadísticas del creador
    public EstadisticasCreador obtenerEstadisticas() {

        int totalReproducciones = getTotalReproducciones();
        int totalEpisodios = episodios.size();
        double promedio = calcularPromedioReproducciones();

        return new EstadisticasCreador(totalReproducciones, totalEpisodios, promedio, suscriptores);
    }


    // Añade red social
    public void agregarRedSocial(String red, String usuario) {

        redesSociales.put(red, usuario);
    }


    // Promedio de reproducciones
    public double calcularPromedioReproducciones() {

        if (episodios.isEmpty()) {
            return 0;
        }

        return (double) getTotalReproducciones() / episodios.size();
    }


    // Elimina episodio por ID
    public void eliminarEpisodio(String idEpisodio) throws EpisodioNoEncontradoException {

        for (Podcast p : episodios) {

            if (p.getId().equals(idEpisodio)) {
                episodios.remove(p);
                return;
            }
        }

        throw new EpisodioNoEncontradoException();
    }


    // Total reproducciones
    public int getTotalReproducciones() {

        int total = 0;

        for (Podcast p : episodios) {
            total += p.getReproducciones();
        }

        return total;
    }


    // Incrementa suscriptores
    public void incrementarSuscriptores() {
        suscriptores++;
    }


    // Obtiene top episodios
    public ArrayList<Podcast> obtenerTopEpisodios(int cantidad) {

        ArrayList<Podcast> copia = new ArrayList<>(episodios);

        copia.sort((p1, p2) ->
                Integer.compare(p2.getReproducciones(), p1.getReproducciones()));

        if (cantidad > copia.size()) {
            cantidad = copia.size();
        }

        return new ArrayList<>(copia.subList(0, cantidad));
    }


    // Obtiene última temporada
    public int getUltimaTemporada() {

        int max = 0;

        for (Podcast p : episodios) {

            if (p.getTemporada() > max) {
                max = p.getTemporada();
            }
        }

        return max;
    }



    //getters y setters

    public String getId() {
        return id;
    }


    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    // COPIA DEFENSIVA
    public ArrayList<Podcast> getEpisodios() {
        return new ArrayList<>(episodios);
    }


    public int getSuscriptores() {
        return suscriptores;
    }

    public void setSuscriptores(int suscriptores) {
        this.suscriptores = suscriptores;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    // COPIA DEFENSIVA
    public HashMap<String, String> getRedesSociales() {
        return new HashMap<>(redesSociales);
    }


    // ✔ COPIA DEFENSIVA
    public ArrayList<CategoriaPodcast> getCategoriasPrincipales() {
        return new ArrayList<>(categoriasPrincipales);
    }


    // Número de episodios
    public int getNumEpisodios() {
        return episodios.size();
    }


    @Override
    public String toString() {
        return "Creador: " + nombreCanal +
                " | Nombre: " + nombre +
                " | Suscriptores: " + suscriptores +
                " | Episodios: " + episodios.size();
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Creador otro = (Creador) obj;

        return id.equals(otro.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }






}
