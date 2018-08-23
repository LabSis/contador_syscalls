package programas;

import datos.Configuracion;

public class ComprimirTar extends Ejecutable {

    public ComprimirTar(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public Process ejecutar() throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls tar -czvf " + Configuracion.DIRECTORIO_COMPRIMIDO + "comprimido.tar.gz " + Configuracion.DIRECTORIO_COMPRIMIR);
    }

}
