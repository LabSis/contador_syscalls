package programas;

public class Find extends Ejecutable {

    public Find(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public Process ejecutar() throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls find /var/www/html/");
    }

}
