package interfaces;

import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;

// iDescargable - Contrato para contenido descargable para uso offline
// Implementada por Cancion y Podcast (solo usuarios premium pueden descargar)
public interface iDescargable {

    // Descarga el contenido (falla si supera límites o ya está descargado)
    boolean descargar() throws LimiteDescargasException, ContenidoYaDescargadoException;

    // Elimina la descarga existente
    boolean eliminarDescarga();

    // Devuelve el espacio aproximado requerido para la descarga (en MB)
    int espacioRequerido();

}
