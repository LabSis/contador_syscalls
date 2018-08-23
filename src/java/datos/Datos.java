package datos;

import ransomware.Jamsomware;
import ransomware.Ransomware;
import ransomware.RansomwareJAES128;
import java.util.ArrayList;
import programas.Ejecutable;
import programas.Find;
import programas.LS;
import programas.Spotify;

public class Datos {

    private static ArrayList<Ransomware> ransomwares;
    private static ArrayList<Ejecutable> ejecutables;

    public static ArrayList<Ransomware> cargarRansomwares() {
        ransomwares = new ArrayList<>();
        Ransomware r1 = new RansomwareJAES128(1, "JAES-128", "Ransomware de cifrado simétrico AES - 128 bits para análisis académico. Parámetros: clave=1234567812345678. El algoritmo de cifrado es muy ingenuo por lo tanto es muy lento.");
        Ransomware r2 = new Jamsomware(2, "Jamsomware", "Ransomware adaptado y descargado desde https://github.com/julupu/jamsomware. Utiliza la librería Crypto la cual es una de las más performantes.");
        ransomwares.add(r1);
        ransomwares.add(r2);
        return ransomwares;
    }

    public static ArrayList<Ejecutable> cargarEjecutables() {
        ejecutables = new ArrayList<>();
        Ejecutable e1 = new LS(1, "ls a directorio raíz", "");
        Ejecutable e2 = new Find(2, "find a /var/www/html/", "");
        Ejecutable e3 = new Spotify(3, "spotify", "");
        ejecutables.add(e1);
        ejecutables.add(e2);
        ejecutables.add(e3);
        return ejecutables;
    }

    public static Ransomware getRansomware(int id) {
        for (Ransomware ransomware : ransomwares) {
            if (ransomware.getId() == id) {
                return ransomware;
            }
        }
        return null;
    }

    public static Ejecutable getEjecutable(int id) {
        for (Ejecutable ejecutable : ejecutables) {
            if (ejecutable.getId() == id) {
                return ejecutable;
            }
        }
        return null;
    }
}
