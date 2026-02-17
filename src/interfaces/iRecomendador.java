package interfaces;

import excepciones.recomendacion.RecomendacionException;
import modelo.contenido.Contenido;
import modelo.usuarios.Usuario;

import java.util.ArrayList;

// iRecomendador - Contrato del sistema de recomendaciones
// Implementada por RecomendadorIA para generar recomendaciones personalizadas
public interface iRecomendador {

    // Genera recomendaciones personalizadas basándose en el historial del usuario
    ArrayList<Contenido> recomendar(Usuario usuario) throws RecomendacionException;

    // Obtiene contenido similar a un contenido dado (por tags, género, categoría)
    ArrayList<Contenido> obtenerSimilares(Contenido contenido) throws RecomendacionException;

}
