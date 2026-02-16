package utilidades;

import enums.AlgoritmoRecomendacion;
import excepciones.recomendacion.RecomendacionException;
import interfaces.iRecomendador;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

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


    public RecomendadorIA() {

        matrizPreferencias = new HashMap<>();
        historialCompleto = new HashMap<>();
        catalogoReferencia = new ArrayList<>();
    }


    public RecomendadorIA(AlgoritmoRecomendacion algoritmo) {

        matrizPreferencias = new HashMap<>();
        historialCompleto = new HashMap<>();
        catalogoReferencia = new ArrayList<>();

        this.algoritmo = algoritmo;
    }


    @Override
    public ArrayList<Contenido> recomendar(Usuario usuario) throws RecomendacionException {

        if (!modeloEntrenado) {
            throw new RecomendacionException();
        }

        if (usuario == null || usuario.getHistorial().isEmpty()) {
            throw new RecomendacionException();
        }

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

    @Override
    public ArrayList<Contenido> obtenerSimilares(Contenido contenido) throws RecomendacionException {
        if (!modeloEntrenado) {
            throw new RecomendacionException();
        }

        if (contenido == null) {
            throw new RecomendacionException();
        }

        ArrayList<Contenido> similares = new ArrayList<>();

        for (Contenido c : catalogoReferencia) {

            if (c == null || c.equals(contenido)) continue;

            // "género/categoría" -> tags (coincidencia de al menos 1)
            for (String tag : contenido.getTags()) {
                if (tag != null && c.getTags().contains(tag)) {
                    similares.add(c);
                    break;
                }
            }
        }

        return similares;
    }




    // MÉTODOS PROPIOS



    // Construye el modelo a partir de usuarios
    public void entrenarModelo(ArrayList<Usuario> usuarios) {

        matrizPreferencias.clear();
        historialCompleto.clear();

        for (Usuario u : usuarios) {

            ArrayList<String> preferencias = new ArrayList<>();

            for (Contenido c : u.getHistorial()) {

                // guardar historial completo
                historialCompleto
                        .computeIfAbsent(u.getId(), k -> new ArrayList<>())
                        .add(c);

                // extraer géneros/categorías (tags)
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


    // Construye el modelo y fija catálogo de referencia
    public void entrenarModelo(ArrayList<Usuario> usuarios, ArrayList<Contenido> catalogo) {

        entrenarModelo(usuarios);

        catalogoReferencia.clear();
        catalogoReferencia.addAll(catalogo);
    }


    // Calcula similitud entre usuarios
    public double calcularSimilitud(Usuario u1, Usuario u2) {

        ArrayList<String> p1 = matrizPreferencias.get(u1.getId());
        ArrayList<String> p2 = matrizPreferencias.get(u2.getId());

        if (p1 == null || p2 == null) return 0.0;

        int coincidencias = 0;

        for (String tag : p1) {
            if (p2.contains(tag)) {
                coincidencias++;
            }
        }

        int total = Math.max(p1.size(), p2.size());

        if (total == 0) return 0.0;

        return (double) coincidencias / total;
    }


    // Actualiza preferencias del usuario según historial
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


    // Cuenta preferencias globales
    public HashMap<String, Integer> obtenerGenerosPopulares() {

        HashMap<String, Integer> conteo = new HashMap<>();

        for (ArrayList<String> preferencias : matrizPreferencias.values()) {

            for (String tag : preferencias) {

                conteo.put(tag, conteo.getOrDefault(tag, 0) + 1);
            }
        }

        return conteo;
    }



    // MÉTODOS PRIVADO

    private double calcularSimilitudContenido(Contenido contenido, ArrayList<String> preferencias) {

        if (contenido == null || preferencias == null || preferencias.isEmpty()) {
            return 0.0;
        }

        int coincidencias = 0;

        for (String tag : contenido.getTags()) {

            if (preferencias.contains(tag)) {
                coincidencias++;
            }
        }

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


    // COPIA DEFENSIVA
    public HashMap<String, ArrayList<String>> getMatrizPreferencias() {

        HashMap<String, ArrayList<String>> copia = new HashMap<>();

        for (String key : matrizPreferencias.keySet()) {
            copia.put(key, new ArrayList<>(matrizPreferencias.get(key)));
        }

        return copia;
    }


    public void setCatalogoReferencia(ArrayList<Contenido> catalogo) {

        catalogoReferencia.clear();
        catalogoReferencia.addAll(catalogo);
    }





}
