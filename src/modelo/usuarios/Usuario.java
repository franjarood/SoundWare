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

// Usuario - Clase base abstracta para todos los usuarios (gratuitos y premium)
// Define la funcionalidad común: playlists, historial, likes, seguir playlists
public abstract class Usuario {

    // ATRIBUTOS

    protected String id; // Identificador único generado automáticamente
    protected String nombre; // Nombre del usuario
    protected String email; // Email (debe contener @ y dominio válido)
    protected String password; // Contraseña (mínimo 8 caracteres)
    protected TipoSuscripcion suscripcion; // Tipo de cuenta (gratuito, premium, familiar, estudiante)
    protected ArrayList<Playlist> misPlaylists; // Playlists creadas por el usuario
    protected ArrayList<Contenido> historial; // Últimas 50 reproducciones
    protected Date fechaRegistro; // Cuándo se registró en la plataforma
    protected ArrayList<Playlist> playlistsSeguidas; // Playlists públicas que sigue
    protected ArrayList<Contenido> contenidosLiked; // Contenido al que dio like

    // CONSTRUCTORES

    // Crea un usuario validando email y contraseña, inicializando colecciones vacías
    public Usuario(String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException {

        // --- Validaciones ---
        if (email == null || !email.contains("@") || !email.substring(email.indexOf("@")).contains(".")) {
            throw new EmailInvalidoException("Formato de email inválido");
        }
        if (password == null || password.length() < 8) {
            throw new PasswordDebilException("La contraseña debe tener al menos 8 caracteres");
        }

        // --- Inicialización de datos básicos ---
        this.id = java.util.UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.suscripcion = suscripcion;
        this.fechaRegistro = new Date();

        // --- Inicialización de colecciones ---
        this.misPlaylists = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.playlistsSeguidas = new ArrayList<>();
        this.contenidosLiked = new ArrayList<>();
    }

    // MÉTODOS ABSTRACTOS

    // Reproduce contenido (cada tipo de usuario lo implementa diferente)
    // Gratuitos: con anuncios y límite diario / Premium: sin restricciones
    public abstract void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException;

    // MÉTODOS PÚBLICOS

    // Crea una nueva playlist privada y la añade a las playlists del usuario
    public Playlist crearPlaylist(String nombrePlaylist) {

        if (nombrePlaylist == null || nombrePlaylist.isEmpty()) {
            return null;
        }

        Playlist nueva = new Playlist(nombrePlaylist, this);
        nueva.setEsPublica(false); // Privada por defecto

        misPlaylists.add(nueva);

        return nueva;
    }

    // Permite seguir una playlist pública de otro usuario
    public void seguirPlaylist(Playlist playlist) {

        if (playlist == null) return;

        // Solo se pueden seguir playlists públicas y evitar duplicados
        if (playlist.isEsPublica() && !playlistsSeguidas.contains(playlist)) {
            playlistsSeguidas.add(playlist);
            playlist.incrementarSeguidores();
        }
    }

    // Deja de seguir una playlist (reduce contador de seguidores)
    public void dejarDeSeguirPlaylist(Playlist playlist) {

        if (playlist == null) return;

        if (playlistsSeguidas.remove(playlist)) {
            playlist.decrementarSeguidores();
        }
    }

    // Marca contenido como favorito y aumenta su contador de likes
    public void darLike(Contenido contenido) {

        if (contenido == null) return;

        // Evitar dar like múltiples veces al mismo contenido
        if (!contenidosLiked.contains(contenido)) {
            contenidosLiked.add(contenido);
            contenido.agregarLike();
        }
    }

    // Quita un contenido de favoritos (no reduce el like global)
    public void quitarLike(Contenido contenido) {

        if (contenido == null) return;

        contenidosLiked.remove(contenido);
    }

    // MÉTODOS DE VALIDACIÓN

    // Verifica que el email tenga formato válido (contiene @ y dominio)
    public boolean validarEmail() throws EmailInvalidoException {

        if (email == null
                || !email.contains("@")
                || !email.substring(email.indexOf("@")).contains(".")) {

            throw new EmailInvalidoException("Formato de email inválido");
        }

        return true;
    }

    // Verifica que la contraseña cumpla requisitos mínimos de seguridad
    public boolean validarPassword() throws PasswordDebilException {

        if (password == null || password.length() < 8) {

            throw new PasswordDebilException("La contraseña debe tener al menos 8 caracteres");
        }

        return true;
    }

    // MÉTODOS PROPIOS

    // Añade contenido al historial con límite de 50 elementos (FIFO)
    public void agregarAlHistorial(Contenido contenido) {

        if (contenido == null) return;

        final int LIMITE = 50;

        historial.add(contenido);

        // Si excede el límite, eliminar el más antiguo (primero)
        if (historial.size() > LIMITE) {
            historial.remove(0);
        }
    }

    // Elimina todo el historial de reproducciones
    public void limpiarHistorial() {
        historial.clear();
    }

    // Verifica si el usuario tiene cualquier tipo de suscripción de pago
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

    // Cambia el email validando su formato
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

    // Cambia la contraseña validando longitud mínima
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

    // Devuelve copia de las playlists para evitar modificaciones externas
    public ArrayList<Playlist> getMisPlaylists() {
        return new ArrayList<>(misPlaylists);
    }

    // Devuelve copia del historial para evitar modificaciones externas
    public ArrayList<Contenido> getHistorial() {
        return new ArrayList<>(historial);
    }

    // Devuelve copia de la fecha para evitar modificaciones (Date es mutable)
    public Date getFechaRegistro() {
        return new Date(fechaRegistro.getTime());
    }

    // Devuelve copia de playlists seguidas para evitar modificaciones externas
    public ArrayList<Playlist> getPlaylistsSeguidas() {
        return new ArrayList<>(playlistsSeguidas);
    }

    // Devuelve copia de contenidos con like para evitar modificaciones externas
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


}
