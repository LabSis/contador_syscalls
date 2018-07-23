package datos;

/**
 *
 * @author fdrcbrtl
 */
public class DiscoResultado {
    private double iowait;
    private double idle;

    public DiscoResultado(double iowait, double idle) {
        this.iowait = iowait;
        this.idle = idle;
    }

    public double getIowait() {
        return iowait;
    }

    public void setIowait(double iowait) {
        this.iowait = iowait;
    }

    public double getIdle() {
        return idle;
    }

    public void setIdle(double idle) {
        this.idle = idle;
    }
}
