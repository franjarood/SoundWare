package modelo.usuarios;

import java.util.Date;

public class UsuarioGratuito extends Usuario {

    private int anunciosEscuchados;
    private Date ultimoAnuncio;
    private int reproduccionesHoy;
    private int limiteReproducciones;
    private int cancionesSinAnuncio;
    private Date fechaUltimaReproduccion;


    private static final int LIMITE_DIARIO = 50;
    private static final int CANCIONES_ENTRE_ANUNCIOS = 3;




   /*



    public int getAnunciosEscuchados() {
        return anunciosEscuchados;
    }

    public void setAnunciosEscuchados(int anunciosEscuchados) {
        this.anunciosEscuchados = anunciosEscuchados;
    }

    public Date getUltimoAnuncio() {
        return ultimoAnuncio;
    }

    public void setUltimoAnuncio(Date ultimoAnuncio) {
        this.ultimoAnuncio = ultimoAnuncio;
    }

    public int getReproduccionesHoy() {
        return reproduccionesHoy;
    }

    public void setReproduccionesHoy(int reproduccionesHoy) {
        this.reproduccionesHoy = reproduccionesHoy;
    }

    public int getLimiteReproducciones() {
        return limiteReproducciones;
    }

    public void setLimiteReproducciones(int limiteReproducciones) {
        this.limiteReproducciones = limiteReproducciones;
    }

    public int getCancionesSinAnuncio() {
        return cancionesSinAnuncio;
    }

    public void setCancionesSinAnuncio(int cancionesSinAnuncio) {
        this.cancionesSinAnuncio = cancionesSinAnuncio;
    }



    public void reproducir(Contenido contenido){};

    public void verAnuncio(){};

    public boolean puedeReproducir(){
        return true; };

    public void reiniciarContadorDiario(){};

    revisar esto de arriba
    */



}
