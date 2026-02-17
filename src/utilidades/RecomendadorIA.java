package utilidades;

import enums.AlgoritmoRecomendacion;
import excepciones.recomendacion.HistorialVacioException;
import excepciones.recomendacion.ModeloNoEntrenadoException;
import excepciones.recomendacion.RecomendacionException;
import interfaces.iRecomendador;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

// RecomendadorIA - Sistema de recomendaciones basado en IA
// Analiza el historial de usuarios para recomendar contenido personalizado
// Usa algoritmos colaborativos, de contenido o híbridos
public class RecomendadorIA implements iRecomendador {

    // ATRIBUTOS

    private HashMap<String, ArrayList<String>> matrizPreferencias; // Preferencias por usuario (tags)
    private HashMap<String, ArrayList<Contenido>> historialCompleto; // Historial guardado de usuarios
    private AlgoritmoRecomendacion algoritmo; // Tipo de algoritmo (colaborativo/contenido/híbrido)
    private double umbralSimilitud; // Nivel mínimo de similitud para recomendar
    private boolean modeloEntrenado; // Si el modelo está listo para usar
    private ArrayList<Contenido> catalogoReferencia; // Catálogo completo para comparar

    // CONSTANTES

    private static final double UMBRAL_DEFAULT = 0.6; // Umbral por defecto de similitud

    // CONSTRUCTORES

    // Crea un recomendador sin algoritmo específico
    public RecomendadorIA() {

        matrizPreferencias = new HashMap<>();
        historialCompleto = new HashMap<>();
        catalogoReferencia = new ArrayList<>();
    }

    // Crea un recomendador con algoritmo específico
    public RecomendadorIA(AlgoritmoRecomendacion algoritmo) {

        matrizPreferencias = new HashMap<>();
        historialCompleto = new HashMap<>();
        catalogoReferencia = new ArrayList<>();

        this.algoritmo = algoritmo;
    }

    // IMPLEMENTACIÓN DE INTERFACES - iRecomendador

    // Recomienda contenido basándose en el historial del usuario
    // Busca contenido similar a lo que ya ha escuchado
    @Override
    public ArrayList<Contenido> recomendar(Usuario usuario) throws RecomendacionException {

        // --- Validar que el modelo esté entrenado ---
        if (!modeloEntrenado) {
            throw new ModeloNoEntrenadoException();
        }

        // --- Validar que el usuario tenga historial ---
        if (usuario == null || usuario.getHistorial().isEmpty()) {
            throw new HistorialVacioException();
        }

        // --- Generar recomendaciones basadas en contenido similar ---
        ArrayList<Contenido> recomendaciones = new ArrayList<>();

        for (Contenido visto : usuario.getHistorial()) {
            for (Contenido sim : obtenerSimilares(visto)) {
                if (!recomendaciones.contains(sim)) {
                    recomendaciones.add(sim);
                }
            }
        }

        return recomendaciones;
    }

    // Encuentra contenido similar por tags (género/categoría)
    @Override
    public ArrayList<Contenido> obtenerSimilares(Contenido contenido) throws RecomendacionException {

        if (!modeloEntrenado) {
            throw new RecomendacionException();
        }

        if (contenido == null) {
            throw new RecomendacionException();
        }

        ArrayList<Contenido> similares = new ArrayList<>();

        // --- Buscar contenido con al menos 1 tag en común ---
        for (Contenido c : catalogoReferencia) {

            if (c == null || c.equals(contenido)) continue;

            // Comparar tags (género/categoría)
            for (String tag : contenido.getTags()) {
                if (tag != null && c.getTags().contains(tag)) {
                    similares.add(c);
                    break; // Ya encontró coincidencia, pasar al siguiente
                }
            }
        }

        return similares;
    }

    // MÉTODOS PROPIOS

    // Entrena el modelo analizando historiales de usuarios
    // Extrae preferencias (tags) de cada usuario
    public void entrenarModelo(ArrayList<Usuario> usuarios) {

        matrizPreferencias.clear();
        historialCompleto.clear();

        for (Usuario u : usuarios) {

            ArrayList<String> preferencias = new ArrayList<>();

            for (Contenido c : u.getHistorial()) {

                // Guardar historial completo del usuario
                historialCompleto
                        .computeIfAbsent(u.getId(), k -> new ArrayList<>())
                        .add(c);

                // Extraer tags (géneros/categorías) del contenido
                for (String tag : c.getTags()) {

                    if (!preferencias.contains(tag)) {
                        preferencias.add(tag);
                    }
                }
            }

            matrizPreferencias.put(u.getId(), preferencias);
        }

        modeloEntrenado = true;
    }

    // Entrena el modelo y establece el catálogo de referencia
    public void entrenarModelo(ArrayList<Usuario> usuarios, ArrayList<Contenido> catalogo) {

        entrenarModelo(usuarios);

        catalogoReferencia.clear();
        catalogoReferencia.addAll(catalogo);
    }

    // Calcula cuánto se parecen dos usuarios (por preferencias comunes)
    // Devuelve un valor entre 0.0 (nada parecidos) y 1.0 (idénticos)
    public double calcularSimilitud(Usuario u1, Usuario u2) {

        ArrayList<String> p1 = matrizPreferencias.get(u1.getId());
        ArrayList<String> p2 = matrizPreferencias.get(u2.getId());

        if (p1 == null || p2 == null) return 0.0;

        // Contar tags en común
        int coincidencias = 0;

        for (String tag : p1) {
            if (p2.contains(tag)) {
                coincidencias++;
            }
        }

        // Calcular porcentaje de similitud
        int total = Math.max(p1.size(), p2.size());

        if (total == 0) return 0.0;

        return (double) coincidencias / total;
    }

    // Actualiza las preferencias de un usuario según su historial actual
    public void actualizarPreferencias(Usuario usuario) {

        ArrayList<String> preferencias = new ArrayList<>();

        for (Contenido c : usuario.getHistorial()) {

            for (String tag : c.getTags()) {

                if (!preferencias.contains(tag)) {
                    preferencias.add(tag);
                }
            }
        }

        matrizPreferencias.put(usuario.getId(), preferencias);
    }

    // Cuenta qué tags (géneros/categorías) son más populares globalmente
    public HashMap<String, Integer> obtenerGenerosPopulares() {

        HashMap<String, Integer> conteo = new HashMap<>();

        for (ArrayList<String> preferencias : matrizPreferencias.values()) {

            for (String tag : preferencias) {

                conteo.put(tag, conteo.getOrDefault(tag, 0) + 1);
            }
        }

        return conteo;
    }

    // MÉTODOS PRIVADOS

    // Calcula cuánto coincide un contenido con las preferencias de un usuario
    private double calcularSimilitudContenido(Contenido contenido, ArrayList<String> preferencias) {

        if (contenido == null || preferencias == null || preferencias.isEmpty()) {
            return 0.0;
        }

        // Contar coincidencias de tags
        int coincidencias = 0;

        for (String tag : contenido.getTags()) {

            if (preferencias.contains(tag)) {
                coincidencias++;
            }
        }

        // Calcular similitud como porcentaje
        int total = Math.max(contenido.getTags().size(), preferencias.size());

        if (total == 0) return 0.0;

        return (double) coincidencias / total;
    }

    // GETTERS Y SETTERS

    public AlgoritmoRecomendacion getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(AlgoritmoRecomendacion algoritmo) {
        this.algoritmo = algoritmo;
    }

    public double getUmbralSimilitud() {
        return umbralSimilitud;
    }

    public void setUmbralSimilitud(double umbralSimilitud) {
        this.umbralSimilitud = umbralSimilitud;
    }

    public boolean isModeloEntrenado() {
        return modeloEntrenado;
    }

    // Devuelve copia de la matriz de preferencias para evitar modificaciones
    public HashMap<String, ArrayList<String>> getMatrizPreferencias() {

        HashMap<String, ArrayList<String>> copia = new HashMap<>();

        for (String key : matrizPreferencias.keySet()) {
            copia.put(key, new ArrayList<>(matrizPreferencias.get(key)));
        }

        return copia;
    }

    // Establece el catálogo de contenido para las recomendaciones
    public void setCatalogoReferencia(ArrayList<Contenido> catalogo) {

        catalogoReferencia.clear();
        catalogoReferencia.addAll(catalogo);
    }





}
