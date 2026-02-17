package enums;

// TipoSuscripcion - Tipos de suscripción disponibles en la plataforma
// Define las características y restricciones de cada tipo de cuenta
// Los usuarios pueden ser gratuitos (con límites) o de pago (sin límites)
public enum TipoSuscripcion {

    GRATUITO(0.0, false, 50, false),        // Gratis, con anuncios, 50 reproducciones/día, sin descargas
    PREMIUM(9.99, true, -1, true),          // $9.99/mes, sin anuncios, ilimitado, con descargas
    FAMILIAR(14.99, true, -1, true),        // $14.99/mes, plan familiar, sin anuncios, ilimitado
    ESTUDIANTE(4.99, true, -1, true);       // $4.99/mes, descuento estudiantes, sin anuncios, ilimitado

    // ATRIBUTOS

    private double precioMensual;           // Costo mensual de la suscripción
    private boolean sinAnuncios;            // Si escucha anuncios o no
    private int limiteReproducciones;       // Reproducciones diarias (-1 = ilimitado)
    private boolean descargasOffline;       // Si puede descargar contenido

    // CONSTRUCTOR

    TipoSuscripcion(double precioMensual, boolean sinAnuncios, int limiteReproducciones, boolean descargasOffline) {
        this.precioMensual = precioMensual;
        this.sinAnuncios = sinAnuncios;
        this.limiteReproducciones = limiteReproducciones;
        this.descargasOffline = descargasOffline;
    }

    // GETTERS

    public double getPrecioMensual() {
        return precioMensual;
    }

    public boolean isSinAnuncios() {
        return sinAnuncios;
    }

    public int getLimiteReproducciones() {
        return limiteReproducciones;
    }

    public boolean isDescargasOffline() {
        return descargasOffline;
    }

    // MÉTODOS PROPIOS

    // Verifica si el tipo de suscripción tiene reproducciones ilimitadas
    public boolean tieneReproduccionesIlimitadas() {
        return limiteReproducciones == -1;
    }

    // OVERRIDES

    @Override
    public String toString() {
        return name() + " (Precio: $" + precioMensual + "/mes)";
    }

}
