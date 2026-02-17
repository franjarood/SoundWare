package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;
import modelo.plataforma.Anuncio;

import java.util.Date;

// UsuarioGratuito - Usuario con límites y anuncios
public class UsuarioGratuito extends Usuario {

    // ATRIBUTOS

    private int anunciosEscuchados;
    private Date ultimoAnuncio;
    private int reproduccionesHoy;
    private int limiteReproducciones;
    private int cancionesSinAnuncio;
    private Date fechaUltimaReproduccion;

    // CONSTANTES

    private static final int LIMITE_DIARIO = 50;
    private static final int CANCIONES_ENTRE_ANUNCIOS = 3;

    // CONSTRUCTORES

    // Crea un usuario gratuito con límites (50 reproducciones/día, anuncios cada 3 canciones)
    public UsuarioGratuito(String nombre, String email, String password)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, TipoSuscripcion.GRATUITO);
    }

    // OVERRIDES

    @Override
    public void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException {

        if (contenido == null) return;

        // --- Validación de disponibilidad ---
        if (!contenido.isDisponible()) {
            throw new ContenidoNoDisponibleException();
        }

        // --- Validación de límite diario ---
        if (reproduccionesHoy >= LIMITE_DIARIO) {
            throw new LimiteDiarioAlcanzadoException();
        }

        // --- Validación de anuncios ---
        if (cancionesSinAnuncio >= CANCIONES_ENTRE_ANUNCIOS) {
            throw new AnuncioRequeridoException();
        }

        // --- Reproducción ---
        contenido.reproducir();

        // --- Registro de reproducción ---
        agregarAlHistorial(contenido);

        reproduccionesHoy++;
        cancionesSinAnuncio++;
        fechaUltimaReproduccion = new Date();
    }

    // MÉTODOS PROPIOS - USUARIO GRATUITO

    // Reproduce un anuncio genérico
    public void verAnuncio() {

        System.out.println("Reproduciendo anuncio genérico...");

        anunciosEscuchados++;
        ultimoAnuncio = new Date();

        cancionesSinAnuncio = 0;
    }

    // Reproduce un anuncio específico
    public void verAnuncio(Anuncio anuncio) {

        if (anuncio == null) {
            verAnuncio();
            return;
        }

        anuncio.reproducir();

        anunciosEscuchados++;
        ultimoAnuncio = new Date();

        cancionesSinAnuncio = 0;
    }

    // Verifica si puede reproducir más contenido hoy
    public boolean puedeReproducir() {

        return reproduccionesHoy < LIMITE_DIARIO;
    }

    // Verifica si debe ver un anuncio antes de seguir
    public boolean debeVerAnuncio() {

        return cancionesSinAnuncio >= CANCIONES_ENTRE_ANUNCIOS;
    }

    // Reinicia el contador diario de reproducciones
    public void reiniciarContadorDiario() {

        reproduccionesHoy = 0;
        cancionesSinAnuncio = 0;
    }

    // Calcula cuántas reproducciones quedan disponibles hoy
    public int getReproduccionesRestantes() {

        return LIMITE_DIARIO - reproduccionesHoy;
    }

    // Calcula cuántas canciones faltan para el siguiente anuncio
    public int getCancionesHastaAnuncio() {

        return CANCIONES_ENTRE_ANUNCIOS - cancionesSinAnuncio;
    }


    // GETTERS Y SETTERS


    public int getAnunciosEscuchados() {
        return anunciosEscuchados;
    }


    public Date getUltimoAnuncio() {
        return ultimoAnuncio;
    }


    public int getReproduccionesHoy() {
        return reproduccionesHoy;
    }

    public void setReproduccionesHoy(int reproduccionesHoy) {
        this.reproduccionesHoy = reproduccionesHoy;
    }


    public int getLimiteReproducciones() {
        return limiteReproducciones;
    }


    public int getCancionesSinAnuncio() {
        return cancionesSinAnuncio;
    }

    public void setCancionesSinAnuncio(int cancionesSinAnuncio) {
        this.cancionesSinAnuncio = cancionesSinAnuncio;
    }




    @Override
    public String toString() {

        return super.toString()
                + " | Gratuito"
                + " | Reproducciones hoy: " + reproduccionesHoy + "/" + limiteReproducciones
                + " | Anuncios escuchados: " + anunciosEscuchados
                + " | Canciones sin anuncio: " + cancionesSinAnuncio;
    }












}
