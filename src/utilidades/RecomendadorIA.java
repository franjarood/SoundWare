package utilidades;

import enums.AlgoritmoRecomendacion;
import interfaces.iRecomendador;
import modelo.contenido.Contenido;

import java.util.ArrayList;
import java.util.HashMap;

public class RecomendadorIA implements iRecomendador {

    private HashMap<String, ArrayList<String>> matrizPreferencias;
    private HashMap<String, ArrayList<Contenido>> historialCompleto;
    private AlgoritmoRecomendacion algoritmo;
    private double umbralSimilitud;
    private boolean modeloEntrenado;
    private ArrayList<Contenido> catalogoReferencia;


    private static final double UMBRAL_DEFAULT = 0.6;

}
