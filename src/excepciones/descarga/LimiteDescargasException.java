package excepciones.descarga;

// LimiteDescargasException - Se lanza cuando un usuario premium supera el límite de descargas
// Los usuarios premium pueden descargar hasta 100 contenidos simultáneamente
// Se valida al intentar descargar contenido
public class LimiteDescargasException extends Exception {

    public LimiteDescargasException() {
    }

    public LimiteDescargasException(String message) {
        super(message);
    }
}
