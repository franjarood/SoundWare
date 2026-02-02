package modelo.usuarios;

import java.util.Date;

public class UsuarioGratuito {

    private int anunciosEscuchados;
    private Date ultimoAnuncio;
    private int reproduccionesHoy;
    private int limiteReproducciones;
    private int cancionesSinAnuncio;

    public UsuarioGratuito(int anunciosEscuchados, Date ultimoAnuncio, int reproduccionesHoy, int limiteReproducciones, int cancionesSinAnuncio) {
        this.anunciosEscuchados = anunciosEscuchados;
        this.ultimoAnuncio = ultimoAnuncio;
        this.reproduccionesHoy = reproduccionesHoy;
        this.limiteReproducciones = limiteReproducciones;
        this.cancionesSinAnuncio = cancionesSinAnuncio;
    }



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






}
