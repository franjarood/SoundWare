package enums;

public enum TipoSuscripcion {

    GRATUITO(0.0, false, 50, false),
    PREMIUM(9.99, true, -1, true),
    FAMILIAR(14.99, true, -1, true),
    ESTUDIANTE(4.99, true, -1, true);


    private double precioMensual;
    private boolean sinAnuncios;
    private int limiteReproducciones;
    private boolean descargasOffline;











//Copiado de sergio
    @Override
    public String toString() {
        return name() + " (Precio: $" + precioMensual + "/mes)";
    }

}
