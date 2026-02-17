package excepciones.contenido;

// EpisodioNoEncontradoException - Se lanza cuando no se encuentra un episodio de podcast buscado
// Ocurre al intentar eliminar un episodio que no existe en la lista del creador
public class EpisodioNoEncontradoException extends Exception {

    public EpisodioNoEncontradoException() {
    }

    public EpisodioNoEncontradoException(String message) {
        super(message);
    }
}
