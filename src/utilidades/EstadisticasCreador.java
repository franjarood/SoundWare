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
}
