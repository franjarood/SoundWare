package modelo.usuarios;

public class UsuarioPremium {

    private boolean descargasOffline;
    private int maxDescargas;
    private descargados ArrayList<Contenido>;
    private String calidadAudio;

    public UsuarioPremium(boolean descargasOffline, int maxDescargas, descargados arrayList, String calidadAudio) {
        this.descargasOffline = descargasOffline;
        this.maxDescargas = maxDescargas;
        ArrayList = arrayList;
        this.calidadAudio = calidadAudio;
    }

    public boolean isDescargasOffline() {
        return descargasOffline;
    }

    public void setDescargasOffline(boolean descargasOffline) {
        this.descargasOffline = descargasOffline;
    }

    public int getMaxDescargas() {
        return maxDescargas;
    }

    public void setMaxDescargas(int maxDescargas) {
        this.maxDescargas = maxDescargas;
    }

    public descargados getArrayList() {
        return ArrayList;
    }

    public void setArrayList(descargados arrayList) {
        ArrayList = arrayList;
    }

    public String getCalidadAudio() {
        return calidadAudio;
    }

    public void setCalidadAudio(String calidadAudio) {
        this.calidadAudio = calidadAudio;
    }

    public void reproducir(Contenido contenido){};


    public void descargar(Contenido contenido){};

    public void eliminarDescarga(Contenido contenido){};

    public boolean verificarEspacioDescarga(){
        return false;
    };


}
