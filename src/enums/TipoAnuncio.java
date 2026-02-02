package enums;

public enum TipoAnuncio {
    AUDIO(15, 0.05),
    BANNER(0, 0.02),
    VIDEO(30, 0.10);

    private int duracionSegundos;
    private double costoPorImpresion;

}
