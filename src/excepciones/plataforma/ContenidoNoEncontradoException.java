package excepciones.plataforma;

// ContenidoNoEncontradoException - Se lanza cuando no se encuentra contenido buscado
// Ocurre al buscar contenido por ID o título que no existe en el catálogo
public class ContenidoNoEncontradoException extends Exception {

    public ContenidoNoEncontradoException() {
    }

    public ContenidoNoEncontradoException(String message) {
        super(message);
    }
}
