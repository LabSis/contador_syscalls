package datos;

import ransomware.Jamsomware;
import ransomware.Ransomware;
import ransomware.RansomwareJAES128;
import java.util.ArrayList;

public class Datos {

    private static ArrayList<Ransomware> ransomwares;

    public static ArrayList<Ransomware> cargarRansomwares() {
        ransomwares = new ArrayList<>();
        Ransomware r1 = new RansomwareJAES128(1, "JAES-128", "Ransomware de cifrado simétrico AES - 128 bits para análisis académico. Parámetros: clave=1234567812345678");
        Ransomware r2 = new Jamsomware(2, "Jamsomware", "Ransomware adaptado y descargado desde https://github.com/julupu/jamsomware");
        ransomwares.add(r1);
        ransomwares.add(r2);
        return ransomwares;
    }
    
    public static Ransomware getRansomware(int id){
        for(Ransomware ransomware : ransomwares) {
            if (ransomware.getId() == id) {
                return ransomware;
            }
        }
        return null;
    }
}
