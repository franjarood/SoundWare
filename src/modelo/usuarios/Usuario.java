package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;
import modelo.plataforma.Playlist;

import java.util.ArrayList;
import java.util.Date;

public abstract class Usuario {

    protected String id;
    protected String nombre;
    protected String email;
    protected String password;
    protected TipoSuscripcion suscripcion;
    protected ArrayList<Playlist> misPlaylists;
    protected ArrayList<Contenido> historial;
    protected Date fechaRegistro;
    protected ArrayList<Playlist> playlistsSeguidas;
    protected ArrayList<Contenido> contenidosLiked;

    public Usuario(String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException {

        // validar email
        if (email == null || !email.contains("@") || !email.substring(email.indexOf("@")).contains(".")) {
            throw new EmailInvalidoException("Formato de email inválido");
        }
        // validar password
        if (password == null || password.length() < 8) {
            throw new PasswordDebilException("La contraseña debe tener al menos 8 caracteres");
        }

        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.suscripcion = suscripcion;

        this.fechaRegistro = new Date();

        this.misPlaylists = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.playlistsSeguidas = new ArrayList<>();
        this.contenidosLiked = new ArrayList<>();
    }



    public abstract void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException;





    // Crear playlist privada del usuario
    public Playlist crearPlaylist(String nombrePlaylist) {

        if (nombrePlaylist == null || nombrePlaylist.isEmpty()) {
            return null;
        }

        Playlist nueva = new Playlist(nombrePlaylist, this);

        // privada por defecto
        nueva.setEsPublica(false);

        misPlaylists.add(nueva);

        return nueva;
    }


    // Seguir playlist pública
    public void seguirPlaylist(Playlist playlist) {

        if (playlist == null) return;

        if (playlist.isEsPublica() && !playlistsSeguidas.contains(playlist)) {
            playlistsSeguidas.add(playlist);
            playlist.incrementarSeguidores(); // coherente con Playlist
        }
    }


    // Dejar de seguir playlist
    public void dejarDeSeguirPlaylist(Playlist playlist) {

        if (playlist == null) return;

        if (playlistsSeguidas.remove(playlist)) {
            playlist.decrementarSeguidores(); // solo si realmente la dejaste de seguir
        }
    }


    // Dar like a contenido
    public void darLike(Contenido contenido) {

        if (contenido == null) return;

        if (!contenidosLiked.contains(contenido)) {
            contenidosLiked.add(contenido);
            contenido.agregarLike(); // existe en Contenido según tu README
        }
    }


    // Quitar like
    public void quitarLike(Contenido contenido) {

        if (contenido == null) return;

        // Quitamos de favoritos. No bajamos el contador global porque Contenido no tiene "quitarLike"
        contenidosLiked.remove(contenido);
    }


    // Validar email
    public boolean validarEmail() throws EmailInvalidoException {

        if (email == null
                || !email.contains("@")
                || !email.substring(email.indexOf("@")).contains(".")) {

            throw new EmailInvalidoException("Formato de email inválido");
        }

        return true;
    }


    // Validar password
    public boolean validarPassword() throws PasswordDebilException {

        if (password == null || password.length() < 8) {

            throw new PasswordDebilException("La contraseña debe tener al menos 8 caracteres");
        }

        return true;
    }


    // Agregar contenido al historial (con límite)
    public void agregarAlHistorial(Contenido contenido) {

        if (contenido == null) return;

        final int LIMITE = 50;

        historial.add(contenido);

        if (historial.size() > LIMITE) {

            historial.remove(0); // elimina el más antiguo
        }
    }


    // Limpiar historial
    public void limpiarHistorial() {

        historial.clear();
    }


    // Indica si el usuario es premium (no gratuito)
    public boolean esPremium() {

        return suscripcion != TipoSuscripcion.GRATUITO;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailInvalidoException {
        if (email == null
                || !email.contains("@")
                || !email.substring(email.indexOf("@")).contains(".")) {
            throw new EmailInvalidoException("Formato de email inválido");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws PasswordDebilException {
        if (password == null || password.length() < 8) {
            throw new PasswordDebilException("La contraseña debe tener al menos 8 caracteres");
        }
        this.password = password;
    }

    public TipoSuscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(TipoSuscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    // COPIA DEFENSIVA
    public ArrayList<Playlist> getMisPlaylists() {
        return new ArrayList<>(misPlaylists);
    }

    // COPIA DEFENSIVA
    public ArrayList<Contenido> getHistorial() {
        return new ArrayList<>(historial);
    }

    // COPIA DEFENSIVA (Date es mutable)
    public Date getFechaRegistro() {
        return new Date(fechaRegistro.getTime());
    }

    // COPIA DEFENSIVA
    public ArrayList<Playlist> getPlaylistsSeguidas() {
        return new ArrayList<>(playlistsSeguidas);
    }

    // COPIA DEFENSIVA
    public ArrayList<Contenido> getContenidosLiked() {
        return new ArrayList<>(contenidosLiked);
    }


    // OVERRIDES


    @Override
    public String toString() {

        return "Usuario: " + nombre +
                " | Email: " + email +
                " | Suscripción: " + suscripcion +
                " | Playlists: " + misPlaylists.size() +
                " | Historial: " + historial.size();
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Usuario otro = (Usuario) obj;

        return id != null && id.equals(otro.id);
    }


    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }




















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
