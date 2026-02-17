package excepciones.contenido;

// TranscripcionNoDisponibleException - Se lanza cuando se intenta obtener la transcripción de un podcast que no la tiene
// No todos los podcasts tienen transcripción disponible
public class TranscripcionNoDisponibleException extends Exception {

    public TranscripcionNoDisponibleException() {
    }

    public TranscripcionNoDisponibleException(String message) {
        super(message);
    }
}
