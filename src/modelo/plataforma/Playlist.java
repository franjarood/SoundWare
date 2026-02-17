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

// Playlist - Lista de contenido (canciones/podcasts) creada por un usuario
// Puede ser pública (visible por todos) o privada (solo del usuario)
// Tiene límite de 500 contenidos y no permite duplicados
public class Playlist {

    // ATRIBUTOS

    private String id; // Identificador único
    private String nombre; // Nombre de la playlist
    private Usuario creador; // Usuario que la creó
    private ArrayList<Contenido> contenidos; // Contenido (canciones/podcasts)
    private boolean esPublica; // Si otros pueden verla
    private int seguidores; // Cuánta gente la sigue
    private String descripcion; // Descripción opcional
    private String portadaURL; // URL de la imagen
    private Date fechaCreacion; // Cuándo se creó
    private int maxContenidos; // Límite de contenido

    // CONSTANTES

    private static final int MAX_CONTENIDOS_DEFAULT = 500;

    // CONSTRUCTORES

    // Crea una playlist privada por defecto
    public Playlist(String nombre, Usuario creador) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.creador = creador;

        this.esPublica = false; // Privada por defecto
        this.contenidos = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.maxContenidos = MAX_CONTENIDOS_DEFAULT;
    }

    // Crea una playlist con configuración personalizada (pública/privada)
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

    // MÉTODOS PÚBLICOS

    // Añade contenido a la playlist (sin duplicados, con límite de 500)
    public void agregarContenido(Contenido contenido)
            throws PlaylistLlenaException, ContenidoDuplicadoException {

        if (contenido == null) return;

        // --- Validar límite ---
        if (contenidos.size() >= maxContenidos) {
            throw new PlaylistLlenaException();
        }

        // --- Validar duplicado ---
        if (contenidos.contains(contenido)) {
            throw new ContenidoDuplicadoException();
        }

        // --- Agregar contenido ---
        contenidos.add(contenido);
    }

    // Elimina contenido de la playlist por ID (evita ConcurrentModificationException)
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

    // Elimina contenido de la playlist por objeto
    public boolean eliminarContenido(Contenido contenido) {
        return contenidos.remove(contenido);
    }

    // Ordena el contenido según el criterio especificado
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
                // Ordena por fecha de publicación del contenido
                contenidos.sort((a, b) ->
                        a.getFechaPublicacion().compareTo(b.getFechaPublicacion()));
                break;

            case ALEATORIO:
                Collections.shuffle(contenidos);
                break;

            case ARTISTA:
                // No se puede ordenar por ARTISTA porque Contenido no tiene getArtista()
                // Solo Cancion lo tiene, se deja sin implementar
                break;
        }
    }

    // Calcula la duración total de la playlist en segundos
    public int getDuracionTotal() {

        int total = 0;

        for (Contenido c : contenidos) {
            total += c.getDuracionSegundos();
        }

        return total;
    }

    // Devuelve la duración total formateada (H:MM:SS o M:SS)
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

    // Mezcla aleatoriamente el orden del contenido
    public void shuffle() {
        Collections.shuffle(contenidos);
    }

    // Busca contenido en la playlist por término en el título
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

    // Hace la playlist visible para todos
    public void hacerPublica() {
        esPublica = true;
    }

    // Hace la playlist privada (solo para el creador)
    public void hacerPrivada() {
        esPublica = false;
    }

    // Incrementa el contador de seguidores
    public void incrementarSeguidores() {
        seguidores++;
    }

    // Decrementa el contador de seguidores (mínimo 0)
    public void decrementarSeguidores() {
        if (seguidores > 0) {
            seguidores--;
        }
    }

    // Devuelve el número de contenidos en la playlist
    public int getNumContenidos() {
        return contenidos.size();
    }

    // Verifica si la playlist está vacía
    public boolean estaVacia() {
        return contenidos.isEmpty();
    }

    // Obtiene un contenido por su posición en la playlist
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

    // Devuelve copia de los contenidos para evitar modificaciones externas
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

    // Devuelve copia de la fecha para evitar modificaciones (Date es mutable)
    public Date getFechaCreacion() {
        return new Date(fechaCreacion.getTime());
    }

    public int getMaxContenidos() {
        return maxContenidos;
    }

    // OVERRIDES

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
