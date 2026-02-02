package modelo.contenido;

import java.util.ArrayList;
import java.util.Date;

public abstract class Contenido {

    private String id;
    private String titulo;
    private int reproducciones;
    private int likes;
    private int duracionSegundos;
    private ArrayList<String> tags;
    private boolean disponible;
    private Date fechaPublicacion;

    public Contenido(String id, String titulo, int reproducciones, int likes, int duracionSegundos, tags arrayList, boolean disponible, Date fechaPublicacion) {
        this.id = id;
        this.titulo = titulo;
        this.reproducciones = reproducciones;
        this.likes = likes;
        this.duracionSegundos = duracionSegundos;
        ArrayList = arrayList;
        this.disponible = disponible;
        this.fechaPublicacion = fechaPublicacion;
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

    public tags getArrayList() {
        return ArrayList;
    }

    public void setArrayList(tags arrayList) {
        ArrayList = arrayList;
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




    public abstract void reproducir();

    public void aumentarReproducciones(){};

    public void agregarLike(){};

    public boolean esPopular(){
        return false;
    };

    public void validarDuracion(){
    };









}
