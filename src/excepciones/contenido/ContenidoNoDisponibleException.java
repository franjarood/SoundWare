package excepciones.contenido;

// ContenidoNoDisponibleException - Se lanza cuando el contenido no está disponible
// El contenido puede estar marcado como no disponible temporalmente
// Se valida al intentar reproducir canciones o podcasts
public class ContenidoNoDisponibleException extends Exception {

    public ContenidoNoDisponibleException() {
    }

    public ContenidoNoDisponibleException(String message) {
        super(message);
    }
}
