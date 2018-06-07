package datos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class GeneradorDeArchivos {

    public GeneradorDeArchivos() {
    }
    
    public void generar(String directorio, long tamTotalMB, long cantidadArchivos) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        long tamPorArchivo = (tamTotalMB / cantidadArchivos) * 1000 * 1000;
        for (int i = 1; i <= cantidadArchivos; i++) {
            RandomAccessFile f = new RandomAccessFile(directorio + "a" + i, "rw");
           f.setLength(tamPorArchivo);
        }
    }
    
    public static void main(String args[]) throws UnsupportedEncodingException, IOException {
        GeneradorDeArchivos ga = new GeneradorDeArchivos();
        ga.generar("", 30, 5);
    }
}
