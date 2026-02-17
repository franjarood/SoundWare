package excepciones.recomendacion;

// RecomendacionException - Excepción base para problemas con el sistema de recomendaciones
// Se lanza cuando hay un error general al generar recomendaciones
// Es la clase padre de ModeloNoEntrenadoException e HistorialVacioException
public class RecomendacionException extends Exception {

    public RecomendacionException() {
    }

    public RecomendacionException(String message) {
        super(message);
    }
}
