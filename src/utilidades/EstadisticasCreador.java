package utilidades;

import modelo.artistas.Creador;
import modelo.contenido.Podcast;

import java.util.HashMap;

// EstadisticasCreador - Genera y calcula estadísticas de un creador de podcasts
// Analiza episodios, reproducciones, likes, engagement y crecimiento estimado
public class EstadisticasCreador {

    // ATRIBUTOS

    private Creador creador; // Creador del que se generan las estadísticas
    private int totalEpisodios; // Total de episodios publicados
    private int totalReproducciones; // Suma de todas las reproducciones
    private double promedioReproducciones; // Promedio de reproducciones por episodio
    private int totalSuscriptores; // Número de suscriptores del canal
    private int totalLikes; // Suma de todos los likes
    private int duracionTotalSegundos; // Duración total de todos los episodios
    private Podcast episodioMasPopular; // Episodio con más reproducciones
    private HashMap<Integer, Integer> episodiosPorTemporada; // Cantidad por temporada

    // CONSTRUCTORES

    // Crea estadísticas para un creador y calcula todos los valores
    public EstadisticasCreador(Creador creador) {
        this.creador = creador;
        this.episodiosPorTemporada = new HashMap<>();
        calcularEstadisticas();
    }

    // MÉTODOS PRIVADOS

    // Recorre todos los episodios del creador y calcula las estadísticas
    private void calcularEstadisticas() {

        totalEpisodios = 0;
        totalReproducciones = 0;
        promedioReproducciones = 0.0;
        totalLikes = 0;
        duracionTotalSegundos = 0;
        episodioMasPopular = null;

        episodiosPorTemporada.clear();

        // --- Analizar cada episodio del creador ---
        for (Podcast p : creador.getEpisodios()) {

            totalEpisodios++;
            totalReproducciones += p.getReproducciones();
            totalLikes += p.getLikes();
            duracionTotalSegundos += p.getDuracionSegundos();

            // Encontrar el episodio más popular
            if (episodioMasPopular == null ||
                    p.getReproducciones() > episodioMasPopular.getReproducciones()) {
                episodioMasPopular = p;
            }

            // Contar episodios por temporada
            int temporada = p.getTemporada();
            episodiosPorTemporada.put(
                    temporada,
                    episodiosPorTemporada.getOrDefault(temporada, 0) + 1
            );
        }

        // --- Calcular promedio ---
        if (totalEpisodios > 0) {
            promedioReproducciones = (double) totalReproducciones / totalEpisodios;
        }

        totalSuscriptores = creador.getSuscriptores();
    }

    // Convierte segundos a formato "H:MM:SS" o "M:SS"
    private String formatearDuracion(int segundos) {

        int horas = segundos / 3600;
        int resto = segundos % 3600;
        int minutos = resto / 60;
        int seg = resto % 60;

        if (horas > 0) {
            return String.format("%d:%02d:%02d", horas, minutos, seg);
        }
        return String.format("%d:%02d", minutos, seg);
    }

    // MÉTODOS PÚBLICOS

    // Genera un reporte completo con todas las estadísticas del creador
    public String generarReporte() {

        String masPopular = (episodioMasPopular == null)
                ? "N/A"
                : episodioMasPopular.getTitulo() + " (" + episodioMasPopular.getReproducciones() + " reps)";

        return "Reporte de Estadísticas del Creador" +
                "\nCanal: " + creador.getNombreCanal() +
                "\nCreador: " + creador.getNombre() +
                "\nSuscriptores: " + totalSuscriptores +
                "\nTotal episodios: " + totalEpisodios +
                "\nTotal reproducciones: " + totalReproducciones +
                "\nPromedio reproducciones: " + promedioReproducciones +
                "\nTotal likes: " + totalLikes +
                "\nDuración total: " + formatearDuracion(duracionTotalSegundos) +
                "\nEpisodio más popular: " + masPopular +
                "\nEngagement: " + String.format("%.2f", calcularEngagement()) + "%" +
                "\nCrecimiento mensual estimado: " + estimarCrecimientoMensual() + " suscriptores/mes";
    }

    // Calcula el engagement (porcentaje de likes respecto a reproducciones)
    public double calcularEngagement() {

        if (totalReproducciones <= 0) return 0.0;

        return ((double) totalLikes / totalReproducciones) * 100.0;
    }

    // Estima cuántos suscriptores nuevos podría ganar al mes
    // Basado en promedio de reproducciones y engagement
    public int estimarCrecimientoMensual() {

        double crecimiento = (promedioReproducciones / 100.0) + (calcularEngagement() / 10.0);

        if (crecimiento < 0) crecimiento = 0;

        return (int) Math.round(crecimiento);
    }

    // GETTERS

    public Creador getCreador() {
        return creador;
    }

    public int getTotalEpisodios() {
        return totalEpisodios;
    }

    public int getTotalReproducciones() {
        return totalReproducciones;
    }

    public double getPromedioReproducciones() {
        return promedioReproducciones;
    }

    public int getTotalSuscriptores() {
        return totalSuscriptores;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public int getDuracionTotalSegundos() {
        return duracionTotalSegundos;
    }

    public Podcast getEpisodioMasPopular() {
        return episodioMasPopular;
    }

    // Devuelve copia del mapa para evitar modificaciones externas
    public HashMap<Integer, Integer> getEpisodiosPorTemporada() {
        return new HashMap<>(episodiosPorTemporada);
    }

    // OVERRIDES

    @Override
    public String toString() {

        return "EstadisticasCreador{" +
                "canal='" + creador.getNombreCanal() + '\'' +
                ", episodios=" + totalEpisodios +
                ", reproducciones=" + totalReproducciones +
                ", likes=" + totalLikes +
                ", suscriptores=" + totalSuscriptores +
                '}';
    }


}
