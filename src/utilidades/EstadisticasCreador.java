package utilidades;

import modelo.artistas.Creador;
import modelo.contenido.Podcast;

import java.util.HashMap;

public class EstadisticasCreador {

    private Creador creador;
    private int totalEpisodios;
    private int totalReproducciones;
    private double promedioReproducciones;
    private int totalSuscriptores;
    private int totalLikes;
    private int duracionTotalSegundos;
    private Podcast episodioMasPopular;
    private HashMap<Integer, Integer> episodiosPorTemporada;

    public EstadisticasCreador(Creador creador) {
        this.creador = creador;
        this.episodiosPorTemporada = new HashMap<>();
        calcularEstadisticas();
    }


    // Métodos privados (enunciado)

    private void calcularEstadisticas() {

        totalEpisodios = 0;
        totalReproducciones = 0;
        promedioReproducciones = 0.0;
        totalLikes = 0;
        duracionTotalSegundos = 0;
        episodioMasPopular = null;

        episodiosPorTemporada.clear();

        // Con tu Creador: episodios = creador.getEpisodios()
        for (Podcast p : creador.getEpisodios()) {

            totalEpisodios++;
            totalReproducciones += p.getReproducciones();

            // Estos 2 getters dependen de tu clase Podcast (los pongo porque tienes atributos para ello)
            totalLikes += p.getLikes();
            duracionTotalSegundos += p.getDuracionSegundos();

            if (episodioMasPopular == null ||
                    p.getReproducciones() > episodioMasPopular.getReproducciones()) {
                episodioMasPopular = p;
            }

            int temporada = p.getTemporada();
            episodiosPorTemporada.put(
                    temporada,
                    episodiosPorTemporada.getOrDefault(temporada, 0) + 1
            );
        }

        if (totalEpisodios > 0) {
            promedioReproducciones = (double) totalReproducciones / totalEpisodios;
        }

        // Con tu Creador: suscriptores es int
        totalSuscriptores = creador.getSuscriptores();
    }

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


    public double calcularEngagement() {

        if (totalReproducciones <= 0) return 0.0;


        return ((double) totalLikes / totalReproducciones) * 100.0;
    }


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


    // COPIA DEFENSIVA
    public HashMap<Integer, Integer> getEpisodiosPorTemporada() {

        return new HashMap<>(episodiosPorTemporada);
    }



    // OVERRIDE


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
