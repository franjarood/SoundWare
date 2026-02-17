package modelo.contenido;

import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

// CLASE ABSTRACTA - Contenido base para canciones y podcasts
public abstract class Contenido {

    // ATRIBUTOS

    protected String id;
    protected String titulo;
    protected int reproducciones;
    protected int likes;
    protected int duracionSegundos;
    protected ArrayList<String> tags;
    protected boolean disponible;
    protected Date fechaPublicacion;

    // CONSTRUCTORES

    // Crea contenido con título y duración, generando ID único y valores por defecto
    public Contenido(String titulo, int duracionSegundos) throws DuracionInvalidaException {

        this.duracionSegundos = duracionSegundos;
        validarDuracion();

        // Generar ID único automáticamente
        this.id = java.util.UUID.randomUUID().toString();
        this.titulo = titulo;

        // Inicializar valores por defecto
        this.reproducciones = 0;
        this.likes = 0;
        this.disponible = true;
        this.tags = new ArrayList<>();
        this.fechaPublicacion = new Date();
    }

    // MÉTODOS ABSTRACTOS

    // Cada tipo de contenido implementa su propia forma de reproducirse
    public abstract void reproducir() throws ContenidoNoDisponibleException;

    // MÉTODOS PÚBLICOS

    // Incrementa el contador de reproducciones
    public void aumentarReproducciones() {
        this.reproducciones++;
    }

    // Incrementa el contador de likes
    public void agregarLike(){
        this.likes++;
    }

    // Verifica si el contenido es popular (más de 100K reproducciones)
    public boolean esPopular() {
        return this.reproducciones > 100000;
    }

    // Valida que la duración sea válida
    public void validarDuracion() throws DuracionInvalidaException {
        if (this.duracionSegundos <= 0) {
            throw new DuracionInvalidaException("La duración debe ser mayor que 0 segundos");
        }
    }

    // Añade una etiqueta al contenido
    public void agregarTag(String tag) {
        if (tag != null && !tags.isEmpty() && !tags.contains(tag)) {
            tags.add(tag.toLowerCase());
        }
    }

    // Verifica si el contenido tiene una etiqueta específica
    public boolean tieneTag(String tag) {
        return tag != null && tags.contains(tag.toLowerCase());
    }

    // Marca el contenido como no disponible
    public void marcarNoDisponible() {
        this.disponible = false;
    }

    // Marca el contenido como disponible
    public void marcarDisponible() {
        this.disponible = true;
    }

    // Formatea la duración en formato "M:SS"
    public String getDuracionFormateada() {
        int minutos = duracionSegundos / 60;
        int segundos = duracionSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }

    // GETTERS Y SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getReproducciones() {
        return reproducciones;
    }

    public void setReproducciones(int reproducciones) {
        this.reproducciones = reproducciones;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    // COPIA DEFENSIVA - devuelve una copia para evitar modificaciones externas
    public ArrayList<String> getTags() {
        return new ArrayList<>(tags);
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    // =====================================================
    // OVERRIDES
    // =====================================================

    @Override
    public String toString() {
        return titulo + " [" + getDuracionFormateada() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Contenido oontenido = (Contenido) obj;

        // Comparación segura de IDs (evita NullPointerException)
        return Objects.equals(id, oontenido.id);
    }

    @Override
    public int hashCode() {
        // Hash seguro (evita NullPointerException si id es null)
        return Objects.hashCode(id);
    }



}
