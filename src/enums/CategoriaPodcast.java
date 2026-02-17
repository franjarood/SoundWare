package enums;

// CategoriaPodcast - Categorías disponibles para clasificar podcasts
// Se usa para organizar y filtrar episodios por temática
public enum CategoriaPodcast {
    TECNOLOGIA("Tecnología", "Podcasts sobre tecnología e innovación"),
    DEPORTES("Deportes", "Podcasts deportivos"),
    COMEDIA("Comedia", "Podcasts de humor y entretenimiento"),
    TRUE_CRIME("True Crime", "Podcasts de crímenes reales"),
    EDUCACION("Educación", "Podcasts educativos"),
    NEGOCIOS("Negocios", "Podcasts de negocios y emprendimiento"),
    SALUD("Salud", "Podcasts de salud y bienestar"),
    ENTRETENIMIENTO("Entretenimiento", "Podcasts de entretenimiento general"),
    HISTORIA("Historia", "Podcasts históricos"),
    CIENCIA("Ciencia", "Podcasts científicos"),
    POLITICA("Política", "Podcasts de política y actualidad"),
    CULTURA("Cultura", "Podcasts culturales");

    // ATRIBUTOS

    private String nombre; // Nombre de la categoría
    private String descripcion; // Descripción de la categoría

    // CONSTRUCTOR

    CategoriaPodcast(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // OVERRIDES

    @Override
    public String toString() {
        return nombre;
    }
}
