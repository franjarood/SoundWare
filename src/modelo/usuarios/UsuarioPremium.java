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

// UsuarioPremium - Usuario con suscripción de pago
// Sin anuncios, sin límite de reproducciones, con descargas offline (hasta 100)
public class UsuarioPremium extends Usuario {

    // ATRIBUTOS

    private boolean descargasOffline; // Si tiene activadas las descargas
    private int maxDescargas; // Límite de descargas simultáneas
    private ArrayList<Contenido> descargados; // Contenido descargado localmente
    private String calidadAudio; // Calidad de reproducción (alta, media, baja)

    // CONSTANTES

    private static final int MAX_DESCARGAS_DEFAULT = 100; // Límite por defecto

    // CONSTRUCTORES

    // Crea usuario premium con suscripción PREMIUM por defecto
    public UsuarioPremium(String nombre, String email, String password)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, TipoSuscripcion.PREMIUM);

        descargados = new ArrayList<>();
        maxDescargas = MAX_DESCARGAS_DEFAULT;
    }

    // Crea usuario premium con tipo de suscripción personalizado (familiar, estudiante)
    public UsuarioPremium(String nombre, String email, String password, TipoSuscripcion suscripcion)
            throws EmailInvalidoException, PasswordDebilException {

        super(nombre, email, password, suscripcion);

        descargados = new ArrayList<>();
        maxDescargas = MAX_DESCARGAS_DEFAULT;
    }

    // OVERRIDES

    // Reproduce contenido sin anuncios ni límite diario
    @Override
    public void reproducir(Contenido contenido)
            throws ContenidoNoDisponibleException,
            LimiteDiarioAlcanzadoException,
            AnuncioRequeridoException {

        if (contenido == null) return;

        // --- Validación única: disponibilidad del contenido ---
        if (!contenido.isDisponible()) {
            throw new ContenidoNoDisponibleException();
        }

        // --- Reproducir y registrar ---
        contenido.reproducir();
        agregarAlHistorial(contenido);
    }

    // MÉTODOS PROPIOS - PREMIUM

    // Descarga contenido para escuchar offline (máximo 100 descargas)
    public void descargar(Contenido contenido)
            throws LimiteDescargasException, ContenidoYaDescargadoException {

        if (contenido == null) return;

        // --- Validar que no esté ya descargado ---
        if (descargados.contains(contenido)) {
            throw new ContenidoYaDescargadoException();
        }

        // --- Validar límite de descargas ---
        if (descargados.size() >= maxDescargas) {
            throw new LimiteDescargasException();
        }

        // --- Añadir a descargas ---
        descargados.add(contenido);
    }

    // Elimina una descarga existente y libera espacio
    public boolean eliminarDescarga(Contenido contenido) {

        if (contenido == null) return false;

        return descargados.remove(contenido);
    }

    // Verifica si hay espacio disponible para más descargas
    public boolean verificarEspacioDescarga() {
        return descargados.size() < maxDescargas;
    }

    // Calcula cuántas descargas más se pueden hacer
    public int getDescargasRestantes() {
        return maxDescargas - descargados.size();
    }

    // Cambia la calidad de audio para reproducciones
    public void cambiarCalidadAudio(String calidad) {
        if (calidad != null && !calidad.trim().isEmpty()) {
            this.calidadAudio = calidad;
        }
    }

    // Elimina todas las descargas
    public void limpiarDescargas() {
        descargados.clear();
    }

    // =====================================================
    // GETTERS Y SETTERS
    // =====================================================


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
