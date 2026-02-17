package modelo.contenido;

import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.contenido.DuracionInvalidaException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public abstract class Contenido {

    protected String id;
    protected String titulo;
    protected int reproducciones;
    protected int likes;
    protected int duracionSegundos;
    protected ArrayList<String> tags;
    protected boolean disponible;
    protected Date fechaPublicacion;


    public Contenido(String titulo, int duracionSegundos) throws DuracionInvalidaException {

        this.duracionSegundos = duracionSegundos;
        validarDuracion();

        this.id = java.util.UUID.randomUUID().toString();
        this.titulo = titulo;


        this.reproducciones = 0;
        this.likes = 0;
        this.disponible = true;
        this.tags = new ArrayList<>();
        this.fechaPublicacion = new Date();
    }

    public abstract void reproducir() throws ContenidoNoDisponibleException;

    public void aumentarReproducciones() {
        this.reproducciones++;
    }

    public void agregarLike(){
        this.likes++;
    }

    public boolean esPopular() {
        return this.reproducciones > 100000;
    }

    public void validarDuracion() throws DuracionInvalidaException {
        if (this.duracionSegundos <= 0) {
            throw new DuracionInvalidaException("La duración debe ser mayor que 0 segundos");
        }
    }

    public void agregarTag(String tag) {
        if (tag != null && !tags.isEmpty() && !tags.contains(tag)) {
            tags.add(tag.toLowerCase());
        }
    }

    public boolean tieneTag(String tag) {
        return tag != null && tags.contains(tag.toLowerCase());
    }

    public void marcarNoDisponible() {
        this.disponible = false;
    }

    public void marcarDisponible() {
        this.disponible = true;
    }

    // Formatear la duración en formato "M:SS"
    public String getDuracionFormateada() {
        int minutos = duracionSegundos / 60;
        int segundos = duracionSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }


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

    //COPIA DEFENSIVA
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

        return Objects.equals(id, oontenido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }




}
