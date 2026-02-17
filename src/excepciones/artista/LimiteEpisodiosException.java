package excepciones.artista;

// LimiteEpisodiosException - Se lanza cuando un creador intenta publicar más episodios del límite permitido
// Los creadores de podcasts tienen un límite de 500 episodios
// Se valida al intentar publicar nuevos episodios
public class LimiteEpisodiosException extends Exception {

    public LimiteEpisodiosException() {
    }

    public LimiteEpisodiosException(String message) {
        super(message);
    }
}
