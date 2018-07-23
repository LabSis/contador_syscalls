package datos;

/**
 *
 * @author fdrcbrtl
 */
public class MemoriaResultado {
    /* En MB */
    private double memused;
    private double porcentajeMemused;

    public MemoriaResultado(double memused, double porcentajeMemused) {
        this.memused = memused;
        this.porcentajeMemused = porcentajeMemused;
    }

    public double getMemused() {
        return memused;
    }

    public void setMemused(double memused) {
        this.memused = memused;
    }

    public double getPorcentajeMemused() {
        return porcentajeMemused;
    }

    public void setPorcentajeMemused(double porcentajeMemused) {
        this.porcentajeMemused = porcentajeMemused;
    }
}
