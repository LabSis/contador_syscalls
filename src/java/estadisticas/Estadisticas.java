package estadisticas;

import datos.Resultado;
import ransomware.Ransomware;

public abstract class Estadisticas {

    protected Ransomware ransomware;

    public Estadisticas(Ransomware ransomware) {
        this.ransomware = ransomware;
    }
    
    public abstract Resultado ejecutar();
}
