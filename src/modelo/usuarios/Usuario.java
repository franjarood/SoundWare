package modelo.usuarios;

import enums.TipoSuscripcion;
import modelo.contenido.Contenido;
import modelo.plataforma.Playlist;

import java.util.ArrayList;
import java.util.Date;

public abstract class Usuario {

    private String id;
    private String nombre;
    private String email;
    private String password;
    private TipoSuscripcion suscripcion;
    private ArrayList<Playlist> misPlaylists;
    private ArrayList<Contenido> historial;
    private Date fechaRegistro;
    private ArrayList<Playlist> playlistsSeguidas;
    private ArrayList<Contenido> contenidosLiked;



/*
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Suscripcion getTipoSuscripcion() {
        return TipoSuscripcion;
    }

    public void setTipoSuscripcion(Suscripcion tipoSuscripcion) {
        TipoSuscripcion = tipoSuscripcion;
    }

    public misPlaylists getArrayList() {
        return ArrayList;
    }

    public void setArrayList(historial arrayList) {
        ArrayList = arrayList;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setArrayList(misPlaylists arrayList) {
        ArrayList = arrayList;
    }

    public abstract void reproducir(Contenido contenido);

    public void crearPlaylist(String nombre){
        Playlists playlists = new Playlists(nombre);
        misplaylist.addPlaylist(playlists);
        return playlist;
    }

    public void seguirPlaylist(Playlists playlist){
        misplaylist.addPlaylist(playlist);
    }

    public boolean validarEmail(){
        return true;
    }

    public boolean validarPassword(){
        return true;
    }




estaba hecho, hacer de nuevo



*/

}
