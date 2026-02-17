package interfaces;

// iReproducible - Contrato para contenido que se puede reproducir
// Implementada por Cancion y Podcast para control básico de reproducción
public interface iReproducible {

    void play();        // Inicia la reproducción del contenido
    void pause();       // Pausa la reproducción del contenido
    void stop();        // Detiene completamente la reproducción
    int getDuracion();  // Devuelve la duración del contenido en segundos


}
