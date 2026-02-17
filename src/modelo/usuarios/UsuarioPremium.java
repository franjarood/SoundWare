package modelo.usuarios;

import enums.TipoSuscripcion;
import excepciones.contenido.ContenidoNoDisponibleException;
import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;
import excepciones.usuario.AnuncioRequeridoException;
import excepciones.usuario.EmailInvalidoException;
import excepciones.usuario.LimiteDiarioAlcanzadoException;
import excepciones.usuario.PasswordDebilException;
import modelo.contenido.Contenido;

import java.util.ArrayList;

public class UsuarioPremium extends Usuario {


    private boolean descargasOffline;
    private int maxDescargas;
    private ArrayList<Contenido> descargados;
    private String calidadAudio;


    private static final int MAX_DESCARGAS_DEFAULT = 100;

    public UsuarioPremium(String nombre, String email, String password)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, TipoSuscripcion.PREMIUM);

        descargados = new ArrayList<>();
        maxDescargas = MAX_DESCARGAS_DEFAULT;
    }

    public UsuarioPremium(String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, suscripcion);

        descargados = new ArrayList<>();
        maxDescargas = MAX_DESCARGAS_DEFAULT;
    }


    @Override
    public void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException {

        if (contenido == null) return;

        // Premium: solo valida disponibilidad (sin anuncios ni límite)
        if (!contenido.isDisponible()) {
            throw new ContenidoNoDisponibleException();
        }

        contenido.reproducir();

        agregarAlHistorial(contenido);
    }



    // MÉTODOS PROPIOS - PREMIUM


    public void descargar(Contenido contenido)
            throws LimiteDescargasException, ContenidoYaDescargadoException {

        if (contenido == null) return;

        if (descargados.contains(contenido)) {
            throw new ContenidoYaDescargadoException();
        }

        // Validar que no se supere el límite de descargas permitidas
        if (descargados.size() >= maxDescargas) {
            throw new LimiteDescargasException();
        }

        descargados.add(contenido);
    }


    public boolean eliminarDescarga(Contenido contenido) {

        if (contenido == null) return false;

        return descargados.remove(contenido);
    }


    public boolean verificarEspacioDescarga() {

        return descargados.size() < maxDescargas;
    }


    public int getDescargasRestantes() {

        return maxDescargas - descargados.size();
    }


    public void cambiarCalidadAudio(String calidad) {

        if (calidad != null && !calidad.trim().isEmpty()) {
            this.calidadAudio = calidad;
        }
    }


    public void limpiarDescargas() {

        descargados.clear();
    }



    // GETTERS Y SETTERS - USUARIO PREMIUM


    public boolean isDescargasOffline() {
        return descargasOffline;
    }

    public void setDescargasOffline(boolean descargasOffline) {
        this.descargasOffline = descargasOffline;
    }


    public int getMaxDescargas() {
        return maxDescargas;
    }


    // COPIA DEFENSIVA
    public ArrayList<Contenido> getDescargados() {
        return new ArrayList<>(descargados);
    }


    public int getNumDescargados() {
        return descargados.size();
    }


    public String getCalidadAudio() {
        return calidadAudio;
    }

    public void setCalidadAudio(String calidadAudio) {

        if (calidadAudio != null && !calidadAudio.trim().isEmpty()) {
            this.calidadAudio = calidadAudio;
        }
    }


    @Override
    public String toString() {

        return super.toString()
                + " | Premium"
                + " | Descargas: " + descargados.size() + "/" + maxDescargas
                + " | Calidad audio: " + calidadAudio
                + " | Offline: " + descargasOffline;
    }



















}
