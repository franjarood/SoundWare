package excepciones.usuario;

// AnuncioRequeridoException - Se lanza cuando un usuario gratuito debe ver un anuncio
// Los usuarios gratuitos deben ver un anuncio cada 3 canciones
// Se valida al intentar reproducir contenido
public class AnuncioRequeridoException extends Exception {

    public AnuncioRequeridoException() {
    }

    public AnuncioRequeridoException(String message) {
        super(message);
    }
}
