package excepciones.artista;

// AlbumYaExisteException - Se lanza cuando un artista intenta crear un álbum con un título que ya tiene
// Evita álbumes duplicados del mismo artista
// Se valida al crear nuevos álbumes
public class AlbumYaExisteException extends Exception {

    public AlbumYaExisteException() {
    }

    public AlbumYaExisteException(String message) {
        super(message);
    }
}
