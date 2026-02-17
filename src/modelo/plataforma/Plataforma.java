package modelo.plataforma;

import enums.CategoriaPodcast;
import enums.GeneroMusical;
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

public class Plataforma {

    private static Plataforma instancia;
    private String nombre;
    private HashMap<String, Usuario> usuarios;
    private HashMap<String, Usuario> usuariosPorEmail;
    private ArrayList<Contenido> catalogo;
    private ArrayList<Playlist> playlistsPublicas;
    private HashMap<String, Artista> artistas;
    private HashMap<String, Creador> creadores;
    private ArrayList<Album> albumes;
    private ArrayList<Anuncio> anuncios;
    private RecomendadorIA recomendador;
    private int totalAnunciosReproducidos;



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

        recomendador = new RecomendadorIA();
    }



    // MÉTODOS SINGLETON


    // Devuelve o crea la instancia única con nombre
    public static synchronized Plataforma getInstancia(String nombre) {

        if (instancia == null) {
            instancia = new Plataforma(nombre);
        }

        return instancia;
    }


    // Devuelve la instancia con nombre por defecto
    public static synchronized Plataforma getInstancia() {

        if (instancia == null) {
            instancia = new Plataforma("Plataforma");
        }

        return instancia;
    }


    // Reinicia la instancia (útil para pruebas)
    public static synchronized void reiniciarInstancia() {

        instancia = null;
    }



    // GESTIÓN DE USUARIOS


    public UsuarioPremium registrarUsuarioPremium(String nombre, String email, String password, TipoSuscripcion tipo)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        if (usuariosPorEmail.containsKey(email)) {
            throw new UsuarioYaExisteException();
        }

        UsuarioPremium u = new UsuarioPremium(nombre, email, password, tipo);

        usuarios.put(u.getId(), u);
        usuariosPorEmail.put(email, u);

        return u;
    }


    public UsuarioPremium registrarUsuarioPremium(String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        return registrarUsuarioPremium(nombre, email, password, TipoSuscripcion.PREMIUM);
    }


    public UsuarioGratuito registrarUsuarioGratuito(String nombre, String email, String password)
            throws UsuarioYaExisteException, EmailInvalidoException, PasswordDebilException {

        if (usuariosPorEmail.containsKey(email)) {
            throw new UsuarioYaExisteException();
        }

        UsuarioGratuito u = new UsuarioGratuito(nombre, email, password);

        usuarios.put(u.getId(), u);
        usuariosPorEmail.put(email, u);

        return u;
    }


    public ArrayList<UsuarioPremium> getUsuariosPremium() {

        ArrayList<UsuarioPremium> res = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u instanceof UsuarioPremium) {
                res.add((UsuarioPremium) u);
            }
        }

        return res;
    }


    public ArrayList<UsuarioGratuito> getUsuariosGratuitos() {

        ArrayList<UsuarioGratuito> res = new ArrayList<>();

        for (Usuario u : usuarios.values()) {
            if (u instanceof UsuarioGratuito) {
                res.add((UsuarioGratuito) u);
            }
        }

        return res;
    }


    public ArrayList<Usuario> getTodosLosUsuarios() {

        return new ArrayList<>(usuarios.values());
    }


    public Usuario buscarUsuarioPorEmail(String email) {

        return usuariosPorEmail.get(email);
    }




    // GESTIÓN DE ARTISTAS


    public Artista registrarArtista(String nombreArtistico, String nombreReal, String paisOrigen, boolean verificado) {

        Artista artista = new Artista(nombreArtistico, nombreReal, paisOrigen, verificado, null);

        artistas.put(nombreArtistico, artista);

        return artista;
    }


    public void registrarArtista(Artista artista) {

        if (artista != null) {
            artistas.put(artista.getNombreArtistico(), artista);
        }
    }


    public ArrayList<Artista> getArtistasVerificados() {

        ArrayList<Artista> res = new ArrayList<>();

        for (Artista a : artistas.values()) {
            if (a.isVerificado()) {
                res.add(a);
            }
        }

        return res;
    }


    public ArrayList<Artista> getArtistasNoVerificados() {

        ArrayList<Artista> res = new ArrayList<>();

        for (Artista a : artistas.values()) {
            if (!a.isVerificado()) {
                res.add(a);
            }
        }

        return res;
    }


    public Artista buscarArtista(String nombre) throws ArtistaNoEncontradoException {

        Artista artista = artistas.get(nombre);

        if (artista == null) {
            throw new ArtistaNoEncontradoException();
        }

        return artista;
    }



    // GESTIÓN DE ÁLBUMES


    public Album crearAlbum(Artista artista, String titulo, Date fecha)
            throws ArtistaNoVerificadoException, AlbumYaExisteException {

        Album album = artista.crearAlbum(titulo, fecha);

        albumes.add(album);

        return album;
    }


    public ArrayList<Album> getAlbumes() {

        return new ArrayList<>(albumes);
    }



    // GESTIÓN DE CANCIONES


    // Crea canción independiente.
    public Cancion crearCancion(String titulo, int duracion, Artista artista, GeneroMusical genero)
            throws DuracionInvalidaException {

        Cancion c = new Cancion(titulo, duracion, artista, genero);

        agregarContenidoCatalogo(c);

        if (artista != null) {
            artista.publicarCancion(c);
        }

        return c;
    }


    // Delegación al álbum (composición).
    public Cancion crearCancionEnAlbum(String titulo, int duracion, Artista artista, GeneroMusical genero, Album album)
            throws DuracionInvalidaException, AlbumCompletoException {

        Cancion c = album.crearCancion(titulo, duracion, genero);

        agregarContenidoCatalogo(c);

        return c;
    }


    // Agrega contenido al catálogo
    public void agregarContenidoCatalogo(Contenido contenido) {

        if (contenido != null && !catalogo.contains(contenido)) {
            catalogo.add(contenido);
        }
    }


    // Devuelve todas las canciones del catálogo
    public ArrayList<Cancion> getCanciones() {

        ArrayList<Cancion> res = new ArrayList<>();

        for (Contenido c : catalogo) {
            if (c instanceof Cancion) {
                res.add((Cancion) c);
            }
        }

        return res;
    }



    // GESTIÓN DE CREADORES/PODCASTS


    public Creador registrarCreador(String nombreCanal, String nombre, String descripcion) {

        Creador c = new Creador(nombreCanal, nombre, descripcion);

        creadores.put(nombreCanal, c);

        return c;
    }


    public void registrarCreador(Creador creador) {

        if (creador != null) {
            creadores.put(creador.getNombreCanal(), creador);
        }
    }


    public Podcast crearPodcast(String titulo, int duracion, Creador creador, int numEpisodio, int temporada, CategoriaPodcast categoria)
            throws DuracionInvalidaException, LimiteEpisodiosException {

        Podcast p = new Podcast(titulo, duracion, creador, numEpisodio, temporada, categoria);

        creador.publicarPodcast(p);

        agregarContenidoCatalogo(p);

        return p;
    }


    public ArrayList<Podcast> getPodcasts() {

        ArrayList<Podcast> res = new ArrayList<>();

        for (Contenido c : catalogo) {
            if (c instanceof Podcast) {
                res.add((Podcast) c);
            }
        }

        return res;
    }


    public ArrayList<Creador> getTodosLosCreadores() {

        return new ArrayList<>(creadores.values());
    }


    // GESTIÓN DE PLAYLISTS PÚBLICAS


    public Playlist crearPlaylistPublica(String nombre, Usuario creador) {

        Playlist p = new Playlist(nombre, creador);

        p.setEsPublica(true);

        playlistsPublicas.add(p);

        return p;
    }


    public ArrayList<Playlist> getPlaylistsPublicas() {

        return new ArrayList<>(playlistsPublicas);
    }




    // BÚSQUEDAS


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


    public ArrayList<Contenido> obtenerTopContenidos(int cantidad) {

        ArrayList<Contenido> copia = new ArrayList<>(catalogo);

        copia.sort((c1, c2) ->
                Integer.compare(c2.getReproducciones(), c1.getReproducciones()));

        if (cantidad > copia.size()) {
            cantidad = copia.size();
        }

        return new ArrayList<>(copia.subList(0, cantidad));
    }




    // ANUNCIOS


    // Devuelve anuncio activo aleatorio
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


    // Incrementa contador de anuncios reproducidos
    public void incrementarAnunciosReproducidos() {

        totalAnunciosReproducidos++;
    }



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



    // GETTERS BÁSICOS


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
