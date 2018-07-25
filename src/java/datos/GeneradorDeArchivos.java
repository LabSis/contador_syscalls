package datos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class GeneradorDeArchivos {

    char[] alfabeto = "abcdefghijklmnopqrtuvswxyz".toCharArray();

    public GeneradorDeArchivos() {
    }

    public void generar(String directorio, long tamTotalMB, long cantidadArchivos) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        long tamPorArchivo = (tamTotalMB / cantidadArchivos) * 1000 * 1000;
        int cantidadCaracteresPorRenglon = 1000;
        for (int i = 1; i <= cantidadArchivos; i++) {
            RandomAccessFile f = new RandomAccessFile(directorio + "a" + i + ".txt", "rw");
            for (int j = 0; j < tamPorArchivo; j += cantidadCaracteresPorRenglon) {
                String renglon = "";
                for(int k = 0; k < cantidadCaracteresPorRenglon - 1; k++) {
                    int indice = (int) (Math.random() * (double) alfabeto.length);
                    renglon += alfabeto[indice];
                }
                renglon += "\n";
                f.writeUTF(renglon);
            }
        }
    }

    public static void main(String args[]) throws UnsupportedEncodingException, IOException {
        GeneradorDeArchivos ga = new GeneradorDeArchivos();
        ga.generar("", 30, 5);
    }
}
