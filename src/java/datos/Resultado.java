package datos;

import java.util.ArrayList;

/**
 *
 * @author fdrcbrtl
 */
public class Resultado {

    private ArrayList<SyscallResultado> syscalls;
    private ProcesamientoResultado procesamiento;
    private DiscoResultado disco;
    private MemoriaResultado memoria;
    private int totalCantidadSyscalls;

    public Resultado(ArrayList<SyscallResultado> syscalls, ProcesamientoResultado procesamiento, DiscoResultado disco, MemoriaResultado memoria) {
        this.syscalls = syscalls;
        this.procesamiento = procesamiento;
        this.disco = disco;
        this.memoria = memoria;
    }

    public ArrayList<SyscallResultado> getSyscalls() {
        return syscalls;
    }

    public void setSyscalls(ArrayList<SyscallResultado> syscalls) {
        this.syscalls = syscalls;
    }

    public ProcesamientoResultado getProcesamiento() {
        return procesamiento;
    }

    public void setProcesamiento(ProcesamientoResultado procesamiento) {
        this.procesamiento = procesamiento;
    }

    public DiscoResultado getDisco() {
        return disco;
    }

    public void setDisco(DiscoResultado disco) {
        this.disco = disco;
    }

    public MemoriaResultado getMemoria() {
        return memoria;
    }

    public void setMemoria(MemoriaResultado memoria) {
        this.memoria = memoria;
    }

    public int getTotalCantidadSyscall() {
        return totalCantidadSyscalls;
    }

    public void setTotalCantidadSyscalls(int totalCantidadSyscalls) {
        this.totalCantidadSyscalls = totalCantidadSyscalls;
    }

}
