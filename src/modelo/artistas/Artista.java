package modelo.artistas;

import excepciones.artista.AlbumYaExisteException;
import excepciones.artista.ArtistaNoVerificadoException;
import modelo.contenido.Cancion;

import java.util.ArrayList;
import java.util.Date;


public class Artista {

    private String id;
    private String nombreArtistico;
    private String nombreReal;
    private String paisOrigen;
    private ArrayList<Cancion> discografia;
    private ArrayList<Album> albumes;
    private int oyentesMensuales;
    private boolean verificado;
    private String biografia;


    public Artista(String nombreArtistico, String nombreReal, String paisOrigen) {
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.paisOrigen = paisOrigen;

        this.discografia = new ArrayList<>();
        this.albumes = new ArrayList<>();
    }

    public Artista(String nombreArtistico, String nombreReal, String paisOrigen, boolean verificado, String biografia) {
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.paisOrigen = paisOrigen;
        this.verificado = verificado;
        this.biografia = biografia;

        this.discografia = new ArrayList<>();
        this.albumes = new ArrayList<>();
    }


    // MÉTODOS DE ARTISTA


    // Añade una canción a la discografía
    public void publicarCancion(Cancion cancion) {

        if (cancion != null && !discografia.contains(cancion)) {
            discografia.add(cancion);
        }
    }


    // Crea un álbum si el artista está verificado y no existe duplicado
    public Album crearAlbum(String titulo, Date fecha)
            throws ArtistaNoVerificadoException, AlbumYaExisteException {

        if (!verificado) {
            throw new ArtistaNoVerificadoException();
        }

        for (Album album : albumes) {
            if (album.getTitulo().equalsIgnoreCase(titulo)) {
                throw new AlbumYaExisteException();
            }
        }

        Album nuevoAlbum = new Album(titulo, fecha, this);

        albumes.add(nuevoAlbum);

        return nuevoAlbum;
    }


    // Devuelve las canciones más reproducidas
    public ArrayList<Cancion> obtenerTopCanciones(int cantidad) {

        ArrayList<Cancion> ordenadas = new ArrayList<>(discografia);

        ordenadas.sort((c1, c2) -> c2.getReproducciones() - c1.getReproducciones());

        if (cantidad >= ordenadas.size() ) {
            return ordenadas;
        }
        return new ArrayList<>(ordenadas.subList(0, cantidad));
    }


    // Calcula promedio de reproducciones
    public double calcularPromedioReproducciones() {

        if (discografia.isEmpty()) {
            return 0.0;
        }

        return (double) getTotalReproducciones() / discografia.size();
    }


    // Indica si está verificado
    public boolean esVerificado() {
        return verificado;
    }


    // Suma todas las reproducciones
    public int getTotalReproducciones() {

        int total = 0;

        for (Cancion cancion : discografia) {
            total += cancion.getReproducciones();
        }

        return total;
    }


    // Marca al artista como verificado
    public void verificar() {
        this.verificado = true;
    }


    // Incrementa oyentes mensuales
    public void incrementarOyentes() {
        this.oyentesMensuales++;
    }


    // GETTERS Y SETTERS


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }


    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }


    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }


    // COPIA DEFENSIVA
    public ArrayList<Cancion> getDiscografia() {
        return new ArrayList<>(discografia);
    }


    // COPIA DEFENSIVA
    public ArrayList<Album> getAlbumes() {
        return new ArrayList<>(albumes);
    }


    public int getOyentesMensuales() {
        return oyentesMensuales;
    }

    public void setOyentesMensuales(int oyentesMensuales) {
        this.oyentesMensuales = oyentesMensuales;
    }


    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }


    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }




    @Override
    public String toString() {
        return "Artista: " + nombreArtistico +
                " | Nombre real: " + nombreReal +
                " | País: " + paisOrigen +
                " | Verificado: " + verificado;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Artista otro = (Artista) obj;

        return id.equals(otro.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }





}
