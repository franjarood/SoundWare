package excepciones.playlist;

// CancionNoEncontradaException - Se lanza cuando no se encuentra una canción en un álbum o playlist
// Ocurre al intentar eliminar una canción que no existe en la colección
public class CancionNoEncontradaException extends Exception {

    public CancionNoEncontradaException() {
    }

    public CancionNoEncontradaException(String message) {
        super(message);
    }
}
