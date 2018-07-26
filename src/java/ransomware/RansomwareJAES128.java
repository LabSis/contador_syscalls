package ransomware;

import com.google.gson.annotations.Expose;
import datos.Configuracion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RansomwareJAES128 extends Ransomware {

    private transient Cipher cipher;
    private transient String victimDir;

    public RansomwareJAES128(int id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }

    @Override
    public Process encrypt(String directorioVictima) throws Exception {
        return Runtime.getRuntime().exec("strace -f -c -S calls java -jar " + Configuracion.DIRECTORIO_RANSOMWARES + "RansomwareJAES128.jar " + directorioVictima);
    }

    @Override
    public Process decrypt(String victimDir) throws Exception {
        return null;
    }

}
