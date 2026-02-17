package excepciones.contenido;

// ArchivoAudioNoEncontradoException - Se lanza cuando no se encuentra el archivo de audio del contenido
// Ocurre cuando la URL del audio no existe o está vacía
public class ArchivoAudioNoEncontradoException extends Exception {

    public ArchivoAudioNoEncontradoException() {
    }

    public ArchivoAudioNoEncontradoException(String message) {
        super(message);
    }
}
