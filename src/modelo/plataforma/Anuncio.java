package modelo.plataforma;

import enums.TipoAnuncio;

public class Anuncio {

    private String id;
    private String empresa;
    private int duracionSegundos;
    private String audioURL;
    private TipoAnuncio tipo;
    private int impresiones;
    private double presupuesto;
    private boolean activo;


    public Anuncio(String empresa, TipoAnuncio tipo, double presupuesto) {
        this.empresa = empresa;
        this.tipo = tipo;
        this.presupuesto = presupuesto;
    }

    public Anuncio(String empresa, TipoAnuncio tipo, double presupuesto, String audioURL) {
        this.empresa = empresa;
        this.tipo = tipo;
        this.presupuesto = presupuesto;
        this.audioURL = audioURL;
    }



    public void reproducir() {

        if (activo) {
            registrarImpresion();
        }
    }

    public void registrarImpresion() {

        impresiones++;

        if (calcularCostoTotal() >= presupuesto) {
            desactivar();
        }
    }

    public double calcularCostoPorImpresion() {
        return tipo.getCostoPorImpresion();
    }

    public double calcularCostoTotal() {
        return impresiones * calcularCostoPorImpresion();
    }

    public int calcularImpresionesRestantes() {

        double restante = presupuesto - calcularCostoTotal();

        if (restante <= 0) {
            return 0;
        }

        return (int)(restante / calcularCostoPorImpresion());
    }

    public void desactivar() {
        activo = false;
    }

    public void activar() {
        activo = true;
    }

    public boolean puedeMostrarse() {
        return activo && calcularCostoTotal() < presupuesto;
    }



    //getter y setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public TipoAnuncio getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnuncio tipo) {
        this.tipo = tipo;
    }

    public int getImpresiones() {
        return impresiones;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }





    @Override
    public String toString() {
        return "Anuncio: " + empresa +
                " | Tipo: " + tipo +
                " | Duración: " + duracionSegundos + " segundos" +
                " | Impresiones: " + impresiones +
                " | Activo: " + activo;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Anuncio otro = (Anuncio) obj;

        return id.equals(otro.id);
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }





}
