package excepciones.artista;

// ArtistaNoVerificadoException - Se lanza cuando un artista no verificado intenta crear un álbum
// Solo los artistas verificados pueden crear álbumes
// Se valida al intentar crear un álbum
public class ArtistaNoVerificadoException extends Exception {

    public ArtistaNoVerificadoException() {
    }

    public ArtistaNoVerificadoException(String message) {
        super(message);
    }
}
