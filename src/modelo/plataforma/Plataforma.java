package modelo.plataforma;

import enums.TipoSuscripcion;
import excepciones.plataforma.ArtistaNoEncontradoException;
import excepciones.plataforma.UsuarioYaExisteException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.PasswordDebilException;
import modelo.artistas.Album;
import modelo.artistas.Artista;
import modelo.artistas.Creador;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;
import modelo.usuarios.UsuarioGratuito;
import modelo.usuarios.UsuarioPremium;
import utilidades.RecomendadorIA;

import java.util.ArrayList;
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




















}
