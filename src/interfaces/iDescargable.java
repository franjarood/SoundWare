package interfaces;

import excepciones.descarga.ContenidoYaDescargadoException;
import excepciones.descarga.LimiteDescargasException;

public interface iDescargable {
    boolean descargar() throws LimiteDescargasException, ContenidoYaDescargadoException;
    boolean eliminarDescarga();
    int espacioRequerido();
}
