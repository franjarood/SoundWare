package excepciones.playlist;

// PlaylistLlenaException - Se lanza cuando se intenta agregar contenido a una playlist que está llena
// Las playlists tienen un límite de 500 contenidos
// Se valida al intentar agregar más contenido
public class PlaylistLlenaException extends Exception {

    public PlaylistLlenaException() {
    }

    public PlaylistLlenaException(String message) {
        super(message);
    }
}
