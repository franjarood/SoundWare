package excepciones.recomendacion;

// ModeloNoEntrenadoException - Se lanza cuando se intenta usar el recomendador sin entrenar el modelo
// El RecomendadorIA debe entrenarse con usuarios antes de recomendar
// Se valida al llamar a recomendar() u obtenerSimilares()
public class ModeloNoEntrenadoException extends RecomendacionException {

    public ModeloNoEntrenadoException() {
    }

    public ModeloNoEntrenadoException(String message) {
        super(message);
    }
}
