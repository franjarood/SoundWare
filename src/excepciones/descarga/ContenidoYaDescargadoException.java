package excepciones.descarga;

// ContenidoYaDescargadoException - Se lanza cuando se intenta descargar contenido que ya está descargado
// Evita descargas duplicadas del mismo contenido
public class ContenidoYaDescargadoException extends Exception {

    public ContenidoYaDescargadoException() {
    }

    public ContenidoYaDescargadoException(String message) {
        super(message);
    }
}
