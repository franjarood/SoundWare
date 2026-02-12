package modelo.plataforma;

import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
        this.nombre = nombre;
        this.creador = creador;

        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
    }


    public Playlist(String nombre, Usuario creador, boolean esPublica, String descripcion) {
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

        if (contenidos.size() >= maxContenidos) {
            throw new PlaylistLlenaException();
        }

        if (contenidos.contains(contenido)) {
            throw new ContenidoDuplicadoException();
        }

        contenidos.add(contenido);
    }


    public boolean eliminarContenido(String idContenido) {

        for (Contenido c : contenidos) {
            if (c.getId().equals(idContenido)) {
                contenidos.remove(c);
                return true;
            }
        }

        return false;
    }


    public boolean eliminarContenido(Contenido contenido) {
        return contenidos.remove(contenido);
    }


    public void ordenarPor(CriterioOrden criterio) throws PlaylistVaciaException {

        if (contenidos.isEmpty()) {
            throw new PlaylistVaciaException();
        }

        if (criterio == CriterioOrden.TITULO) {
            contenidos.sort((a, b) -> a.getTitulo().compareToIgnoreCase(b.getTitulo()));
        } else if (criterio == CriterioOrden.DURACION) {
            contenidos.sort((a, b) -> Integer.compare(a.getDuracionSegundos(), b.getDuracionSegundos()));
        } else if (criterio == CriterioOrden.POPULARIDAD) {
            contenidos.sort((a, b) -> Integer.compare(b.getReproducciones(), a.getReproducciones()));
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


    //getters y setters


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

        return id.equals(otra.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }









}
