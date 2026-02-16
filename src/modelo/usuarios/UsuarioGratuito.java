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

public class UsuarioGratuito extends Usuario {

    private int anunciosEscuchados;
    private Date ultimoAnuncio;
    private int reproduccionesHoy;
    private int limiteReproducciones;
    private int cancionesSinAnuncio;
    private Date fechaUltimaReproduccion;


    private static final int LIMITE_DIARIO = 50;
    private static final int CANCIONES_ENTRE_ANUNCIOS = 3;

    public UsuarioGratuito(String nombre, String email, String password)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, TipoSuscripcion.GRATUITO);
    }

    @Override
    public void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException {

        if (contenido == null) return;

        // comprobar disponibilidad
        if (!contenido.isDisponible()) {
            throw new ContenidoNoDisponibleException();
        }

        // comprobar límite diario
        if (reproduccionesHoy >= LIMITE_DIARIO) {
            throw new LimiteDiarioAlcanzadoException();
        }

        // comprobar si debe escuchar anuncio
        if (cancionesSinAnuncio >= CANCIONES_ENTRE_ANUNCIOS) {
            throw new AnuncioRequeridoException();
        }

        // reproducir contenido
        contenido.reproducir();

        // registrar reproducción
        agregarAlHistorial(contenido);

        reproduccionesHoy++;
        cancionesSinAnuncio++;
        fechaUltimaReproduccion = new Date();
    }




    // MÉTODOS PROPIOS - USUARIO GRATUITO


    public void verAnuncio() {

        System.out.println("Reproduciendo anuncio genérico...");

        anunciosEscuchados++;
        ultimoAnuncio = new Date();

        cancionesSinAnuncio = 0;
    }


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


    public boolean puedeReproducir() {

        return reproduccionesHoy < LIMITE_DIARIO;
    }


    public boolean debeVerAnuncio() {

        return cancionesSinAnuncio >= CANCIONES_ENTRE_ANUNCIOS;
    }


    public void reiniciarContadorDiario() {

        reproduccionesHoy = 0;
        cancionesSinAnuncio = 0;
    }


    public int getReproduccionesRestantes() {

        return LIMITE_DIARIO - reproduccionesHoy;
    }


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
