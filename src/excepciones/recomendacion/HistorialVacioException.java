package excepciones.recomendacion;

// HistorialVacioException - Se lanza cuando se intenta recomendar a un usuario sin historial
// El usuario debe haber escuchado contenido antes para recibir recomendaciones
// Se valida al llamar a recomendar() con un usuario sin reproducciones
public class HistorialVacioException extends RecomendacionException {

    public HistorialVacioException() {
    }

    public HistorialVacioException(String message) {
        super(message);
    }
}
