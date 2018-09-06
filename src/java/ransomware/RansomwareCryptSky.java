package ransomware;

import datos.Configuracion;
import javax.crypto.Cipher;

public class RansomwareCryptSky extends Ransomware {

    private transient Cipher cipher;
    private transient String victimDir;

    public RansomwareCryptSky(int id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }

    @Override
    public Process encrypt(String directorioVictima) throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls python " + Configuracion.DIRECTORIO_RANSOMWARES + "CryptSky/main.py --dir " + directorioVictima);
    }

    @Override
    public Process decrypt(String victimDir) throws Exception {
        return null;
    }

}
