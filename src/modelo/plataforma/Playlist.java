package modelo.plataforma;

import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class Playlist {

    private String id;
    private String nombre;
    private Usuario creador;
    private ArrayList<Contenido> contenidos;
    private boolean esPublica;
    private int seguidores;
    private String descripcion;
    private String portadaURL;
    private Date fechaCreacion;
    private int maxContenidos;

    private static final int MAX_CONTENIDOS_DEFAULT = 500;



}
