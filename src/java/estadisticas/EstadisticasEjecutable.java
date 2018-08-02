package estadisticas;

import datos.Resultado;
import programas.Ejecutable;

public class EstadisticasEjecutable extends Estadisticas {

    private Ejecutable ejecutable;

    public EstadisticasEjecutable(Ejecutable ejecutable) {
        super(null, 0);
        this.ejecutable = ejecutable;
    }

    public Resultado ejecutar() throws Exception {
        Process proceso = ejecutable.ejecutar();
        return super.generarResultado(proceso);
    }
}
