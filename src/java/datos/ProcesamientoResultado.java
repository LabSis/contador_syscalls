package datos;

/**
 *
 * @author fdrcbrtl
 */
public class ProcesamientoResultado {
    /* En porcentaje */
    private double user;
    /* En porcentaje */
    private double system;

    public ProcesamientoResultado(double porcentajeUsuario, double procentajeSistema) {
        this.user = porcentajeUsuario;
        this.system = procentajeSistema;
    }

    public double getPorcentajeUsuario() {
        return user;
    }

    public void setPorcentajeUsuario(double porcentajeUsuario) {
        this.user = porcentajeUsuario;
    }

    public double getPorcentajeSistema() {
        return system;
    }

    public void setPorcentajeSistema(double procentajeSistema) {
        this.system = procentajeSistema;
    }

}
