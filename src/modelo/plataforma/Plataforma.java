package modelo.plataforma;

import enums.CategoriaPodcast;
import enums.GeneroMusical;
import enums.TipoAnuncio;
import enums.TipoSuscripcion;
import excepciones.artista.AlbumCompletoException;
import excepciones.artista.AlbumYaExisteException;
import excepciones.artista.ArtistaNoVerificadoException;
import excepciones.artista.LimiteEpisodiosException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.plataforma.ArtistaNoEncontradoException;
import excepciones.plataforma.ContenidoNoEncontradoException;
import excepciones.plataforma.UsuarioYaExisteException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.PasswordDebilException;
import modelo.artistas.Album;
import modelo.artistas.Artista;
import modelo.artistas.Creador;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.Podcast;
import modelo.usuarios.Usuario;
import modelo.usuarios.UsuarioGratuito;
import modelo.usuarios.UsuarioPremium;
import utilidades.RecomendadorIA;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

// Plataforma - Singleton que gestiona toda la aplicación (usuarios, contenido, artistas, creadores)
// Es el punto central que coordina todas las operaciones del sistema
public class Plataforma {

    // ATRIBUTOS

    private static Plataforma instancia; // Instancia única (Singleton)
    private String nombre; // Nombre de la plataforma
    private HashMap<String, Usuario> usuarios; // Usuarios por ID
    private HashMap<String, Usuario> usuariosPorEmail; // Usuarios por email
    private ArrayList<Contenido> catalogo; // Todo el contenido disponible
    private ArrayList<Playlist> playlistsPublicas; // Playlists públicas
    private HashMap<String, Artista> artistas; // Artistas registrados
    private HashMap<String, Creador> creadores; // Creadores de podcasts
    private ArrayList<Album> albumes; // Álbumes publicados
    private ArrayList<Anuncio> anuncios; // Anuncios disponibles
    private RecomendadorIA recomendador; // Sistema de recomendaciones
    private int totalAnunciosReproducidos; // Contador de anuncios reproducidos

    // CONSTRUCTOR PRIVADO (Singleton)

    // Crea la plataforma e inicializa todas las colecciones con anuncios por defecto
    private Plataforma(String nombre) {

        this.nombre = nombre;

        usuarios = new HashMap<>();
        usuariosPorEmail = new HashMap<>();

        catalogo = new ArrayList<>();
        playlistsPublicas = new ArrayList<>();

        artistas = new HashMap<>();
        creadores = new HashMap<>();

        albumes = new ArrayList<>();
        anuncios = new ArrayList<>();

        // Agregar anuncios iniciales para que haya contenido publicitario
        anuncios.add(new Anuncio("Spotify", TipoAnuncio.AUDIO, 1000.0));
        anuncios.add(new Anuncio("Nike", TipoAnuncio.BANNER, 500.0));

        recomendador = new RecomendadorIA();
    }



    // MÉTODOS SINGLETON

    // Devuelve o crea la instancia única con nombre personalizado
    public static synchronized Plataforma getInstancia(String nombre) {

        if (instancia == null) {
            instancia = new Plataforma(nombre);
        }

        return instancia;
    }

    // Devuelve la instancia con nombre por defecto "Plataforma"
    public static synchronized Plataforma getInstancia() {

        if (instancia == null) {
            instancia = new Plataforma("Plataforma");
        }

        return instancia;
    }

    // Reinicia la instancia (útil para tests)
    public static synchronized void reiniciarInstancia() {

        instancia = null;
    }

    // MÉTODOS PÚBLICOS - GESTIÓN DE USUARIOS

    // Registra un nuevo usuario premium con tipo de suscripción personalizado
    public UsuarioPremium registrarUsuarioPremium(String nombre, String email, String password, TipoSuscripcion tipo)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        // --- Validar que el email no esté en uso ---
        if (usuariosPorEmail.containsKey(email)) {
            throw new UsuarioYaExisteException();
        }

        // --- Crear y registrar usuario ---
        UsuarioPremium u = new UsuarioPremium(nombre, email, password, tipo);

        usuarios.put(u.getId(), u);
        usuariosPorEmail.put(email, u);

        return u;
    }

    // Registra un usuario premium con suscripción PREMIUM por defecto
    public UsuarioPremium registrarUsuarioPremium(String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        return registrarUsuarioPremium(nombre, email, password, TipoSuscripcion.PREMIUM);
    }

    // Registra un nuevo usuario gratuito en la plataforma
    public UsuarioGratuito registrarUsuarioGratuito(String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        // --- Validar que el email no esté en uso ---
        if (usuariosPorEmail.containsKey(email)) {
            throw new UsuarioYaExisteException();
        }

        // --- Crear y registrar usuario ---
        UsuarioGratuito u = new UsuarioGratuito(nombre, email, password);

        usuarios.put(u.getId(), u);
        usuariosPorEmail.put(email, u);

        return u;
    }

    // Obtiene lista de todos los usuarios premium
    public ArrayList<UsuarioPremium> getUsuariosPremium() {

        ArrayList<UsuarioPremium> res = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u instanceof UsuarioPremium) {
                res.add((UsuarioPremium) u);
            }
        }

        return res;
    }

    // Obtiene lista de todos los usuarios gratuitos
    public ArrayList<UsuarioGratuito> getUsuariosGratuitos() {

        ArrayList<UsuarioGratuito> res = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u instanceof UsuarioGratuito) {
                res.add((UsuarioGratuito) u);
            }
        }

        return res;
    }

    // Obtiene lista de todos los usuarios registrados
    public ArrayList<Usuario> getTodosLosUsuarios() {

        return new ArrayList<>(usuarios.values());
    }

    // Busca un usuario por su email
    public Usuario buscarUsuarioPorEmail(String email) {

        return usuariosPorEmail.get(email);
    }

    // MÉTODOS PÚBLICOS - GESTIÓN DE ARTISTAS

    // Registra un nuevo artista en la plataforma
    public Artista registrarArtista(String nombreArtistico, String nombreReal, String paisOrigen, boolean verificado) {

        Artista artista = new Artista(nombreArtistico, nombreReal, paisOrigen, verificado, null);

        artistas.put(nombreArtistico, artista);

        return artista;
    }

    // Registra un artista existente en la plataforma
    public void registrarArtista(Artista artista) {

        if (artista != null) {
            artistas.put(artista.getNombreArtistico(), artista);
        }
    }

    // Obtiene lista de artistas verificados
    public ArrayList<Artista> getArtistasVerificados() {

        ArrayList<Artista> res = new ArrayList<>();

        for (Artista a : artistas.values()) {
            if (a.isVerificado()) {
                res.add(a);
            }
        }

        return res;
    }


    // Obtiene lista de artistas no verificados
    public ArrayList<Artista> getArtistasNoVerificados() {

        ArrayList<Artista> res = new ArrayList<>();

        for (Artista a : artistas.values()) {
            if (!a.isVerificado()) {
                res.add(a);
            }
        }

        return res;
    }

    // Busca un artista por nombre (lanza excepción si no existe)
    public Artista buscarArtista(String nombre) throws ArtistaNoEncontradoException {

        Artista artista = artistas.get(nombre);

        if (artista == null) {
            throw new ArtistaNoEncontradoException();
        }

        return artista;
    }

    // MÉTODOS PÚBLICOS - GESTIÓN DE ÁLBUMES

    // Crea un álbum para un artista verificado
    public Album crearAlbum(Artista artista, String titulo, Date fecha)
            throws ArtistaNoVerificadoException, AlbumYaExisteException {

        Album album = artista.crearAlbum(titulo, fecha);

        albumes.add(album);

        return album;
    }

    // Obtiene lista de todos los álbumes publicados
    public ArrayList<Album> getAlbumes() {

        return new ArrayList<>(albumes);
    }

    // MÉTODOS PÚBLICOS - GESTIÓN DE CANCIONES

    // Crea una canción independiente (sin álbum) y la añade al catálogo
    public Cancion crearCancion(String titulo, int duracion, Artista artista, GeneroMusical genero)
            throws DuracionInvalidaException {

        Cancion c = new Cancion(titulo, duracion, artista, genero);

        agregarContenidoCatalogo(c);

        if (artista != null) {
            artista.publicarCancion(c);
        }

        return c;
    }

    // Crea una canción dentro de un álbum específico
    public Cancion crearCancionEnAlbum(String titulo, int duracion, Artista artista, GeneroMusical genero, Album album)
            throws DuracionInvalidaException, AlbumCompletoException {

        Cancion c = album.crearCancion(titulo, duracion, genero);

        agregarContenidoCatalogo(c);

        return c;
    }

    // Agrega contenido al catálogo general (sin duplicados)
    public void agregarContenidoCatalogo(Contenido contenido) {

        if (contenido != null && !catalogo.contains(contenido)) {
            catalogo.add(contenido);
        }
    }

    // Obtiene lista de todas las canciones del catálogo
    public ArrayList<Cancion> getCanciones() {

        ArrayList<Cancion> res = new ArrayList<>();

        for (Contenido c : catalogo) {
            if (c instanceof Cancion) {
                res.add((Cancion) c);
            }
        }

        return res;
    }



    // MÉTODOS PÚBLICOS - GESTIÓN DE CREADORES/PODCASTS

    // Registra un nuevo creador de podcasts en la plataforma
    public Creador registrarCreador(String nombreCanal, String nombre, String descripcion) {

        Creador c = new Creador(nombreCanal, nombre, descripcion);

        creadores.put(nombreCanal, c);

        return c;
    }

    // Registra un creador existente en la plataforma
    public void registrarCreador(Creador creador) {

        if (creador != null) {
            creadores.put(creador.getNombreCanal(), creador);
        }
    }

    // Crea un podcast y lo publica en el canal del creador
    public Podcast crearPodcast(String titulo, int duracion, Creador creador, int numEpisodio, int temporada, CategoriaPodcast categoria)
            throws DuracionInvalidaException, LimiteEpisodiosException {

        Podcast p = new Podcast(titulo, duracion, creador, numEpisodio, temporada, categoria);

        creador.publicarPodcast(p);

        agregarContenidoCatalogo(p);

        return p;
    }

    // Obtiene lista de todos los podcasts del catálogo
    public ArrayList<Podcast> getPodcasts() {

        ArrayList<Podcast> res = new ArrayList<>();

        for (Contenido c : catalogo) {
            if (c instanceof Podcast) {
                res.add((Podcast) c);
            }
        }

        return res;
    }

    // Obtiene lista de todos los creadores registrados
    public ArrayList<Creador> getTodosLosCreadores() {

        return new ArrayList<>(creadores.values());
    }

    // MÉTODOS PÚBLICOS - GESTIÓN DE PLAYLISTS PÚBLICAS

    // Crea una playlist pública visible para todos los usuarios
    public Playlist crearPlaylistPublica(String nombre, Usuario creador) {

        Playlist p = new Playlist(nombre, creador);

        p.setEsPublica(true);

        playlistsPublicas.add(p);

        return p;
    }

    // Obtiene lista de todas las playlists públicas
    public ArrayList<Playlist> getPlaylistsPublicas() {

        return new ArrayList<>(playlistsPublicas);
    }

    // MÉTODOS PÚBLICOS - BÚSQUEDAS

    // Busca contenido por término en el título
    public ArrayList<Contenido> buscarContenido(String termino)
            throws ContenidoNoEncontradoException {

        ArrayList<Contenido> res = new ArrayList<>();

        for (Contenido c : catalogo) {

            if (c.getTitulo().toLowerCase().contains(termino.toLowerCase())) {
                res.add(c);
            }
        }

        if (res.isEmpty()) {
            throw new ContenidoNoEncontradoException();
        }

        return res;
    }

    // Busca canciones por género musical
    public ArrayList<Cancion> buscarPorGenero(GeneroMusical genero)
            throws ContenidoNoEncontradoException {

        ArrayList<Cancion> res = new ArrayList<>();

        for (Contenido c : catalogo) {

            if (c instanceof Cancion) {

                Cancion cancion = (Cancion) c;

                if (cancion.getGenero() == genero) {
                    res.add(cancion);
                }
            }
        }

        if (res.isEmpty()) {
            throw new ContenidoNoEncontradoException();
        }

        return res;
    }

    // Busca podcasts por categoría
    public ArrayList<Podcast> buscarPorCategoria(CategoriaPodcast categoria)
            throws ContenidoNoEncontradoException {

        ArrayList<Podcast> res = new ArrayList<>();

        for (Contenido c : catalogo) {

            if (c instanceof Podcast) {

                Podcast p = (Podcast) c;

                if (p.getCategoria() == categoria) {
                    res.add(p);
                }
            }
        }

        if (res.isEmpty()) {
            throw new ContenidoNoEncontradoException();
        }

        return res;
    }

    // Obtiene el contenido más reproducido (top N)
    public ArrayList<Contenido> obtenerTopContenidos(int cantidad) {

        ArrayList<Contenido> copia = new ArrayList<>(catalogo);

        copia.sort((c1, c2) ->
                Integer.compare(c2.getReproducciones(), c1.getReproducciones()));

        if (cantidad > copia.size()) {
            cantidad = copia.size();
        }

        return new ArrayList<>(copia.subList(0, cantidad));
    }

    // MÉTODOS PÚBLICOS - ANUNCIOS

    // Devuelve un anuncio activo aleatorio
    public Anuncio obtenerAnuncioAleatorio() {

        ArrayList<Anuncio> activos = new ArrayList<>();

        for (Anuncio a : anuncios) {
            if (a.isActivo()) {
                activos.add(a);
            }
        }

        if (activos.isEmpty()) {
            return null;
        }

        int indice = (int) (Math.random() * activos.size());

        return activos.get(indice);
    }

    // Incrementa el contador de anuncios reproducidos
    public void incrementarAnunciosReproducidos() {

        totalAnunciosReproducidos++;
    }

    // Genera un reporte con estadísticas generales de la plataforma
    public String obtenerEstadisticasGenerales() {

        return "Plataforma: " + nombre +
                "\nUsuarios: " + usuarios.size() +
                "\nArtistas: " + artistas.size() +
                "\nCreadores: " + creadores.size() +
                "\nÁlbumes: " + albumes.size() +
                "\nPlaylists públicas: " + playlistsPublicas.size() +
                "\nContenidos: " + catalogo.size() +
                "\nAnuncios reproducidos: " + totalAnunciosReproducidos;
    }

    // GETTERS Y SETTERS


    public String getNombre() {
        return nombre;
    }


    // COPIA DEFENSIVA
    public ArrayList<Contenido> getCatalogo() {
        return new ArrayList<>(catalogo);
    }


    // COPIA DEFENSIVA
    public HashMap<String, Artista> getArtistas() {
        return new HashMap<>(artistas);
    }


    // COPIA DEFENSIVA
    public HashMap<String, Creador> getCreadores() {
        return new HashMap<>(creadores);
    }


    // COPIA DEFENSIVA
    public ArrayList<Anuncio> getAnuncios() {
        return new ArrayList<>(anuncios);
    }


    public RecomendadorIA getRecomendador() {
        return recomendador;
    }


    public int getTotalUsuarios() {
        return usuarios.size();
    }


    public int getTotalContenido() {
        return catalogo.size();
    }


    public int getTotalAnunciosReproducidos() {
        return totalAnunciosReproducidos;
    }




    // OVERRIDE


    @Override
    public String toString() {

        return "Plataforma{" +
                "nombre='" + nombre + '\'' +
                ", usuarios=" + usuarios.size() +
                ", artistas=" + artistas.size() +
                ", creadores=" + creadores.size() +
                ", contenidos=" + catalogo.size() +
                ", playlistsPublicas=" + playlistsPublicas.size() +
                ", anunciosReproducidos=" + totalAnunciosReproducidos +
                '}';
    }



}
