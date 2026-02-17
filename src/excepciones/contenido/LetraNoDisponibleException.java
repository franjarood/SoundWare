package excepciones.contenido;

// LetraNoDisponibleException - Se lanza cuando se intenta obtener la letra de una canción que no la tiene
// No todas las canciones tienen letra disponible
public class LetraNoDisponibleException extends Exception {
    public LetraNoDisponibleException() {
    }

    public LetraNoDisponibleException(String message) {
        super(message);
    }
}
