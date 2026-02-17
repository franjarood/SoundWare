package excepciones.plataforma;

// ArtistaNoEncontradoException - Se lanza cuando no se encuentra un artista buscado
// Ocurre al buscar artista por ID o nombre que no existe en la plataforma
public class ArtistaNoEncontradoException extends Exception {

    public ArtistaNoEncontradoException() {
    }

    public ArtistaNoEncontradoException(String message) {
        super(message);
    }
}
