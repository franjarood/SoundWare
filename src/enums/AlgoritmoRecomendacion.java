package enums;

// AlgoritmoRecomendacion - Tipos de algoritmos para el sistema de recomendaciones
// Se usa en RecomendadorIA para decidir cómo recomendar contenido a los usuarios
public enum AlgoritmoRecomendacion {

    COLABORATIVO("Basado en usuarios similares"),           // Recomienda según otros usuarios con gustos parecidos
    CONTENIDO("Basado en características del contenido"),   // Recomienda según tags/géneros del contenido
    HIBRIDO("Combinación de ambos");                        // Combina ambos enfoques

    // ATRIBUTOS

    private String descripcion; // Descripción del algoritmo

    // CONSTRUCTOR

    AlgoritmoRecomendacion(String descripcion) {
        this.descripcion = descripcion;
    }

    // GETTERS

    public String getDescripcion() {
        return descripcion;
    }

    // OVERRIDES

    @Override
    public String toString() {
        return name() + " (Descripción: " + descripcion + ")";
    }

}
