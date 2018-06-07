package datos;

import java.util.ArrayList;

public class Datos {

    private static ArrayList<Ransomware> ransomwares;

    public static ArrayList<Ransomware> cargarRansomwares() {
        ransomwares = new ArrayList<>();
        Ransomware r1 = new RansomwareJAES128("JAES-128", "Ransomware de cifrado simétrico AES - 128 bits para análisis académico. Parámetros: clave=1234567812345678");
        Ransomware r2 = new Jamsomware("Jamsomware", "Ransomware adaptado y descargado desde https://github.com/julupu/jamsomware");
        ransomwares.add(r1);
        ransomwares.add(r2);
        return ransomwares;
    }
    
    public static Ransomware getRansomware(String nombre){
        for(Ransomware ransomware : ransomwares) {
            if (ransomware.getName().equals(nombre)) {
                return ransomware;
            }
        }
        return null;
    }
}
