package ransomware;

import datos.Configuracion;
import javax.crypto.Cipher;

public class RansomwareRansPy extends Ransomware {

    private transient Cipher cipher;
    private transient String victimDir;

    public RansomwareRansPy(int id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }

    @Override
    public Process encrypt(String directorioVictima) throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls python3 " + Configuracion.DIRECTORIO_RANSOMWARES + "rans_py.py " + directorioVictima);
    }

    @Override
    public Process decrypt(String victimDir) throws Exception {
        return null;
    }

}
