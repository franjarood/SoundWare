package modelo.plataforma;

import enums.TipoAnuncio;

import java.util.Objects;

// Anuncio - Publicidad que se muestra a usuarios gratuitos
public class Anuncio {

    // ATRIBUTOS

    private String id;
    private String empresa;
    private int duracionSegundos;
    private String audioURL;
    private TipoAnuncio tipo;
    private int impresiones;
    private double presupuesto;
    private boolean activo;

    // CONSTRUCTORES

    public Anuncio(String empresa, TipoAnuncio tipo, double presupuesto) {
        this.empresa = empresa;
        this.tipo = tipo;
        this.presupuesto = presupuesto;
        this.activo = true; // Los anuncios se crean activos por defecto
    }

    public Anuncio(String empresa, TipoAnuncio tipo, double presupuesto, String audioURL) {
        this.empresa = empresa;
        this.tipo = tipo;
        this.presupuesto = presupuesto;
        this.audioURL = audioURL;
        this.activo = true; // Los anuncios se crean activos por defecto
    }

    // MÉTODOS PÚBLICOS

    // Reproduce el anuncio y registra la impresión
    public void reproducir() {

        if (activo) {
            registrarImpresion();
        }
    }

    // Registra una nueva impresión y desactiva si se agota el presupuesto
    public void registrarImpresion() {

        impresiones++;

        if (calcularCostoTotal() >= presupuesto) {
            desactivar();
        }
    }

    // Calcula el costo por cada impresión según el tipo de anuncio
    public double calcularCostoPorImpresion() {
        return tipo.getCostoPorImpresion();
    }

    // Calcula el costo total acumulado
    public double calcularCostoTotal() {
        return impresiones * calcularCostoPorImpresion();
    }

    // Calcula cuántas impresiones quedan disponibles
    public int calcularImpresionesRestantes() {

        double restante = presupuesto - calcularCostoTotal();

        if (restante <= 0) {
            return 0;
        }

        return (int)(restante / calcularCostoPorImpresion());
    }

    // Desactiva el anuncio
    public void desactivar() {
        activo = false;
    }

    // Activa el anuncio
    public void activar() {
        activo = true;
    }

    // Verifica si el anuncio puede mostrarse
    public boolean puedeMostrarse() {
        return activo && calcularCostoTotal() < presupuesto;
    }

    // GETTERS Y SETTERS

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

    // OVERRIDES

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

        return Objects.equals(id, otro.id);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }





}
