package excepciones.usuario;

// PasswordDebilException - Se lanza cuando la contraseña no cumple requisitos
// La contraseña debe tener al menos 8 caracteres
// Se valida al registrar usuarios o cambiar la contraseña
public class PasswordDebilException extends Exception {

    // Constructor sin mensaje
    public PasswordDebilException() {
    }

    // Constructor con mensaje personalizado
    public PasswordDebilException(String message) {
        super(message);
    }
}
