package programas;

public class LS extends Ejecutable {

    public LS(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public Process ejecutar() throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls ls /");
    }

}
