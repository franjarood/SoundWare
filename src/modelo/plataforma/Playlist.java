package modelo.plataforma;

import enums.CriterioOrden;
import excepciones.playlist.ContenidoDuplicadoException;
import excepciones.playlist.PlaylistLlenaException;
import excepciones.playlist.PlaylistVaciaException;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Playlist {

    private String id;
    private String nombre;
    private Usuario creador;
    private ArrayList<Contenido> contenidos;
    private boolean esPublica;
    private int seguidores;
    private String descripcion;
    private String portadaURL;
    private Date fechaCreacion;
    private int maxContenidos;

    private static final int MAX_CONTENIDOS_DEFAULT = 500;


    public Playlist(String nombre, Usuario creador) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.creador = creador;

        this.esPublica = false; // privada por defecto
        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
    }


    public Playlist(String nombre, Usuario creador, boolean esPublica, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.creador = creador;
        this.esPublica = esPublica;
        this.descripcion = descripcion;

        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
    }


    // MÉTODOS - PLAYLIST

    public void agregarContenido(Contenido contenido)
            throws PlaylistLlenaException, ContenidoDuplicadoException {

        if (contenido == null) return;

        if (contenidos.size() >= maxContenidos) {
            throw new PlaylistLlenaException();
        }

        if (contenidos.contains(contenido)) {
            throw new ContenidoDuplicadoException();
        }

        contenidos.add(contenido);
    }


    // CORREGIDO: no borrar dentro de for-each
    public boolean eliminarContenido(String idContenido) {

        if (idContenido == null) return false;

        for (int i = 0; i < contenidos.size(); i++) {
            if (Objects.equals(contenidos.get(i).getId(), idContenido)) {
                contenidos.remove(i);
                return true;
            }
        }

        return false;
    }


    public boolean eliminarContenido(Contenido contenido) {
        return contenidos.remove(contenido);
    }


    // CORREGIDO: solo criterios posibles con métodos de Contenido
    public void ordenarPor(CriterioOrden criterio) throws PlaylistVaciaException {

        if (contenidos.isEmpty()) {
            throw new PlaylistVaciaException();
        }

        if (criterio == null) return;

        switch (criterio) {

            case ALFABETICO:
                contenidos.sort((a, b) ->
                        a.getTitulo().compareToIgnoreCase(b.getTitulo()));
                break;

            case DURACION:
                contenidos.sort((a, b) ->
                        Integer.compare(a.getDuracionSegundos(), b.getDuracionSegundos()));
                break;

            case POPULARIDAD:
                contenidos.sort((a, b) ->
                        Integer.compare(b.getReproducciones(), a.getReproducciones()));
                break;

            case FECHA_AGREGADO:
                // En tu Contenido existe getFechaPublicacion (no getFechaAgregado)
                contenidos.sort((a, b) ->
                        a.getFechaPublicacion().compareTo(b.getFechaPublicacion()));
                break;

            case ALEATORIO:
                Collections.shuffle(contenidos);
                break;

            case ARTISTA:
                // No se puede ordenar por ARTISTA porque Playlist guarda Contenido
                // y Contenido no tiene getArtista(). (Solo Cancion lo tiene)
                // Lo dejamos sin hacer para no romper.
                break;
        }
    }


    public int getDuracionTotal() {

        int total = 0;

        for (Contenido c : contenidos) {
            total += c.getDuracionSegundos();
        }

        return total;
    }


    public String getDuracionTotalFormateada() {

        int total = getDuracionTotal();

        int horas = total / 3600;
        int resto = total % 3600;
        int minutos = resto / 60;
        int segundos = resto % 60;

        if (horas > 0) {
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        }

        return String.format("%d:%02d", minutos, segundos);
    }


    public void shuffle() {
        Collections.shuffle(contenidos);
    }


    public ArrayList<Contenido> buscarContenido(String termino) {

        ArrayList<Contenido> resultado = new ArrayList<>();

        if (termino == null) return resultado;

        for (Contenido c : contenidos) {
            if (c.getTitulo().toLowerCase().contains(termino.toLowerCase())) {
                resultado.add(c);
            }
        }

        return resultado;
    }


    public void hacerPublica() {
        esPublica = true;
    }


    public void hacerPrivada() {
        esPublica = false;
    }


    public void incrementarSeguidores() {
        seguidores++;
    }


    public void decrementarSeguidores() {
        if (seguidores > 0) {
            seguidores--;
        }
    }


    public int getNumContenidos() {
        return contenidos.size();
    }


    public boolean estaVacia() {
        return contenidos.isEmpty();
    }


    public Contenido getContenido(int posicion) {

        if (posicion < 0 || posicion >= contenidos.size()) {
            return null;
        }

        return contenidos.get(posicion);
    }


    // GETTERS Y SETTERS

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Usuario getCreador() {
        return creador;
    }


    // COPIA DEFENSIVA
    public ArrayList<Contenido> getContenidos() {
        return new ArrayList<>(contenidos);
    }


    public boolean isEsPublica() {
        return esPublica;
    }

    public void setEsPublica(boolean esPublica) {
        this.esPublica = esPublica;
    }


    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getPortadaURL() {
        return portadaURL;
    }

    public void setPortadaURL(String portadaURL) {
        this.portadaURL = portadaURL;
    }


    // COPIA DEFENSIVA
    public Date getFechaCreacion() {
        return new Date(fechaCreacion.getTime());
    }


    public int getMaxContenidos() {
        return maxContenidos;
    }


    @Override
    public String toString() {
        return "Playlist: " + nombre +
                " | Creador: " + creador.getNombre() +
                " | Contenidos: " + contenidos.size() +
                " | Pública: " + esPublica +
                " | Seguidores: " + seguidores;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Playlist otra = (Playlist) obj;

        return id != null && id.equals(otra.id);
    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }




}
