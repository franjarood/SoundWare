package excepciones.playlist;

// ContenidoDuplicadoException - Se lanza cuando se intenta agregar contenido que ya existe en la playlist
// Las playlists no permiten contenido duplicado
// Se valida al agregar canciones o podcasts
public class ContenidoDuplicadoException extends Exception {

    public ContenidoDuplicadoException() {
    }

    public ContenidoDuplicadoException(String message) {
        super(message);
    }
}
