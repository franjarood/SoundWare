package excepciones.contenido;

// DuracionInvalidaException - Se lanza cuando la duración del contenido no es válida
// La duración debe ser mayor a 0 segundos
// Se valida al crear canciones o podcasts
public class DuracionInvalidaException extends Exception {

    public DuracionInvalidaException() {
    }

    public DuracionInvalidaException(String message) {
        super(message);
    }
}
