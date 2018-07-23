package datos;

/**
 *
 * @author fdrcbrtl
 */
public class SyscallResultado {
    private String syscall;
    private int cantidad;

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
    
}
