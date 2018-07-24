package estadisticas;

import datos.Resultado;
import ransomware.Ransomware;

public class EstadisticasJamsomware extends Estadisticas{

    public EstadisticasJamsomware(Ransomware ransomware) {
        super(ransomware);
    }

    @Override
    public Resultado ejecutar() {
        return null;
    }

}
