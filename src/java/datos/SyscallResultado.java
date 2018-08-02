package datos;

/**
 *
 * @author fdrcbrtl
 */
public class SyscallResultado {
    private String syscall;
    private int cantidad;
    private double k;
    private double q;

    public SyscallResultado(String syscall, int cantidad) {
        this.syscall = syscall;
        this.cantidad = cantidad;
    }

    public String getSyscall() {
        return syscall;
    }

    public void setSyscall(String syscall) {
        this.syscall = syscall;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    @Override
    public String toString() {
        return "SyscallResultado{" + "syscall=" + syscall + ", cantidad=" + cantidad + ", k=" + k + ", q=" + q + '}';
    }
    
    
    
}
