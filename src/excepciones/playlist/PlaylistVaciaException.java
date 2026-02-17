package excepciones.playlist;

// PlaylistVaciaException - Se lanza cuando se intenta reproducir o manipular una playlist sin contenido
// Ocurre al intentar acciones sobre una playlist que no tiene ningún contenido
public class PlaylistVaciaException extends Exception {

    public PlaylistVaciaException() {
    }

    public PlaylistVaciaException(String message) {
        super(message);
    }
}
