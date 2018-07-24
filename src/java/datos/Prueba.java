package datos;

import ransomware.Ransomware;

/**
 *
 * @author fdrcbrtl
 */
public class Prueba {

    private Ransomware ransomware;
    /* Cantidad de datos a cifrar en MB */
    private int cantidadDatos;
    private int cantidadArchivos;
    private boolean detectorHabilitado;
    private boolean deteccionPositiva;
    private Resultado resultado;

    public Prueba(Ransomware ransomware, int cantidadDatos, int cantidadArchivos, boolean detectorHabilitado, Resultado resultado) {
        this.ransomware = ransomware;
        this.cantidadDatos = cantidadDatos;
        this.cantidadArchivos = cantidadArchivos;
        this.detectorHabilitado = detectorHabilitado;
        this.deteccionPositiva = false;
        this.resultado = resultado;
    }

    public Prueba(Ransomware ransomware, int cantidadDatos, int cantidadArchivos, boolean detectorHabilitado) {
        this.ransomware = ransomware;
        this.cantidadDatos = cantidadDatos;
        this.cantidadArchivos = cantidadArchivos;
        this.detectorHabilitado = detectorHabilitado;
        this.deteccionPositiva = false;
        this.resultado = null;
    }

    public Ransomware getRansomware() {
        return ransomware;
    }

    public void setRansomware(Ransomware ransomware) {
        this.ransomware = ransomware;
    }

    public int getCantidadDatos() {
        return cantidadDatos;
    }

    public void setCantidadDatos(int cantidadDatos) {
        this.cantidadDatos = cantidadDatos;
    }

    public int getCantidadArchivos() {
        return cantidadArchivos;
    }

    public void setCantidadArchivos(int cantidadArchivos) {
        this.cantidadArchivos = cantidadArchivos;
    }

    public boolean isDetectorHabilitado() {
        return detectorHabilitado;
    }

    public void setDetectorHabilitado(boolean detectorHabilitado) {
        this.detectorHabilitado = detectorHabilitado;
    }

    public boolean isDeteccionPositiva() {
        return deteccionPositiva;
    }

    public void setDeteccionPositiva(boolean deteccionPositiva) {
        this.deteccionPositiva = deteccionPositiva;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
}
