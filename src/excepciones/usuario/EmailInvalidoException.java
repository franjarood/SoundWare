package excepciones.usuario;

// EmailInvalidoException - Se lanza cuando el email no tiene formato válido
// El email debe contener @ y un dominio con punto (ejemplo@dominio.com)
// Se valida al registrar usuarios o cambiar el email
public class EmailInvalidoException extends Exception {

    // Constructor sin mensaje
    public EmailInvalidoException() {
    }

    // Constructor con mensaje personalizado
    public EmailInvalidoException(String message) {
        super(message);
    }
}
