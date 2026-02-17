package excepciones.artista;

// AlbumCompletoException - Se lanza cuando se intenta agregar una canción a un álbum que ya está lleno
// Los álbumes tienen un límite de 20 canciones
// Se valida al crear o agregar canciones al álbum
public class AlbumCompletoException extends Exception {

    public AlbumCompletoException() {
    }

    public AlbumCompletoException(String message) {
        super(message);
    }
}
