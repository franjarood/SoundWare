package enums;

public enum TipoAnuncio {
    AUDIO(15, 0.05),
    BANNER(0, 0.02),
    VIDEO(30, 0.10);

    private int duracionSegundos;
    private double costoPorImpresion;

    TipoAnuncio(int duracionSegundos, double costoPorImpresion) {
        this.duracionSegundos = duracionSegundos;
        this.costoPorImpresion = costoPorImpresion;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public double getCostoPorImpresion() {
        return costoPorImpresion;
    }


    @Override
    public String toString() {
        return name() + " (Duración: " + duracionSegundos + " segundos)";
    }


}
