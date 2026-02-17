package enums;

// TipoAnuncio - Tipos de publicidad disponibles en la plataforma
// Se usan para mostrar anuncios a usuarios gratuitos (cada 3 canciones)
// Cada tipo tiene duración y costo diferente por impresión
public enum TipoAnuncio {

    AUDIO(15, 0.05),    // Anuncio de audio (15 segundos, $0.05 por impresión)
    BANNER(0, 0.02),    // Banner publicitario (sin duración, $0.02 por impresión)
    VIDEO(30, 0.10);    // Anuncio de video (30 segundos, $0.10 por impresión)

    // ATRIBUTOS

    private int duracionSegundos;       // Duración del anuncio
    private double costoPorImpresion;   // Costo por cada vez que se muestra

    // CONSTRUCTOR

    TipoAnuncio(int duracionSegundos, double costoPorImpresion) {
        this.duracionSegundos = duracionSegundos;
        this.costoPorImpresion = costoPorImpresion;
    }

    // GETTERS

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public double getCostoPorImpresion() {
        return costoPorImpresion;
    }

    // OVERRIDES

    @Override
    public String toString() {
        return name() + " (Duración: " + duracionSegundos + " segundos)";
    }


}
