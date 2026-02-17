package modelo.artistas;

import enums.GeneroMusical;
import excepciones.artista.AlbumCompletoException;
import excepciones.contenido.DuracionInvalidaException;
import excepciones.playlist.CancionNoEncontradaException;
import modelo.contenido.Cancion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;



public class Album {

    private String id;
    private String titulo;
    private Artista artista;
    private Date fechaLanzamiento;
    private ArrayList<Cancion> canciones;
    private String portadaURL;
    private String discografica;
    private String tipoAlbum;

    private static final int MAX_CANCIONES = 20;



    public Album(String titulo, Artista artista, Date fechaLanzamiento) {
        this.titulo = titulo;
        this.artista = artista;
        this.fechaLanzamiento = fechaLanzamiento;

        this.canciones = new ArrayList<>();
    }


    public Album(String titulo, Artista artista, Date fechaLanzamiento, String discografica, String tipoAlbum) {
        this.titulo = titulo;
        this.artista = artista;
        this.fechaLanzamiento = fechaLanzamiento;
        this.discografica = discografica;
        this.tipoAlbum = tipoAlbum;

        this.canciones = new ArrayList<>();
    }



    public Cancion crearCancion(String titulo, int duracionSegundos, GeneroMusical genero)
            throws AlbumCompletoException, DuracionInvalidaException {

        if (canciones.size() >= MAX_CANCIONES) {
            throw new AlbumCompletoException("El álbum ya tiene el número máximo de " + MAX_CANCIONES + " canciones permitido.");
        }

        Cancion cancion = new Cancion(titulo, duracionSegundos, this.artista, genero);

        cancion.setAlbum(this);

        canciones.add(cancion);

        if (artista != null) {
            artista.publicarCancion(cancion);
        }
        return cancion;
    }

    public Cancion crearCancion(String titulo, int duracionSegundos,
                                GeneroMusical genero, String letra, boolean explicit)
            throws AlbumCompletoException, DuracionInvalidaException {

        if (canciones.size() >= MAX_CANCIONES) {
            throw new AlbumCompletoException();
        }

        Cancion cancion = new Cancion(titulo, duracionSegundos, artista, genero, letra, explicit);

        cancion.setAlbum(this);

        canciones.add(cancion);

        return cancion;
    }




    public void eliminarCancion(int posicion) throws CancionNoEncontradaException {

        if (posicion < 1 || posicion > canciones.size()) {
            throw new CancionNoEncontradaException("No existe una canción en la posición especificada.");
        }

        Cancion cancion = canciones.remove(posicion - 1);
        cancion.setAlbum(null);
    }


    public void eliminarCancion(Cancion cancion) throws CancionNoEncontradaException {

        if (!canciones.remove(cancion)) {
            throw new CancionNoEncontradaException("La cancion no existe en el album");
        }
        cancion.setAlbum(null);
    }


    public int getDuracionTotal() {

        int total = 0;

        for (Cancion c : canciones) {
            total += c.getDuracionSegundos();
        }

        return total;
    }


    public String getDuracionTotalFormateada() {

        int total = getDuracionTotal();
        int horas = total / 3600;
        int minutos = (total % 3600) / 60;
        int segundos = total % 60;
        if (horas > 0) {
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        }
        return String.format("%d:%02d", minutos, segundos);
    }


    public int getNumCanciones() {
        return canciones.size();
    }


    public void ordenarPorPopularidad() {

        canciones.sort((c1, c2) ->
                Integer.compare(c2.getReproducciones(), c1.getReproducciones()));
    }


    public Cancion getCancion(int posicion) throws CancionNoEncontradaException {

        if (posicion < 1 || posicion > canciones.size()) {
            throw new CancionNoEncontradaException();
        }

        return canciones.get(posicion - 1);
    }


    public int getTotalReproducciones() {

        int total = 0;

        for (Cancion c : canciones) {
            total += c.getReproducciones();
        }

        return total;
    }



    // GETTERS Y SETTERS - ALBUM


    public String getId() {
        return id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }


    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }


    // COPIA DEFENSIVA
    public ArrayList<Cancion> getCanciones() {
        return new ArrayList<>(canciones);
    }


    public String getPortadaURL() {
        return portadaURL;
    }

    public void setPortadaURL(String portadaURL) {
        this.portadaURL = portadaURL;
    }


    public String getDiscografica() {
        return discografica;
    }

    public void setDiscografica(String discografica) {
        this.discografica = discografica;
    }


    public String getTipoAlbum() {
        return tipoAlbum;
    }

    public void setTipoAlbum(String tipoAlbum) {
        this.tipoAlbum = tipoAlbum;
    }


    // normalmente es constante
    public int getMaxCanciones() {
        return MAX_CANCIONES;
    }



    @Override
    public String toString() {
        return "Album: " + titulo +
                " | Artista: " + artista.getNombreArtistico() +
                " | Fecha: " + fechaLanzamiento +
                " | Canciones: " + canciones.size();
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Album otro = (Album) obj;

        return Objects.equals(id, otro.id);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }














}
