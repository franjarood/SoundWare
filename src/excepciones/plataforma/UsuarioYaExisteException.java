package excepciones.plataforma;

// UsuarioYaExisteException - Se lanza cuando se intenta registrar un email duplicado
// Cada email solo puede estar registrado una vez en la plataforma
// Se valida al registrar nuevos usuarios
public class UsuarioYaExisteException extends Exception {

    public UsuarioYaExisteException() {
    }

    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
