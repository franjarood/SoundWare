package excepciones.usuario;

// LimiteDiarioAlcanzadoException - Se lanza cuando un usuario gratuito supera el límite
// Los usuarios gratuitos tienen un límite de 50 reproducciones por día
// Se valida al intentar reproducir contenido
public class LimiteDiarioAlcanzadoException extends Exception {

    public LimiteDiarioAlcanzadoException() {
    }

    public LimiteDiarioAlcanzadoException(String message) {
        super(message);
    }
}
