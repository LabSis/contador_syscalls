package ransomware;

import datos.Configuracion;
import ransomware.Ransomware;

public class Jamsomware extends Ransomware {

    public Jamsomware(int id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public Process encrypt(String directorioVictima) throws Exception {
        return Runtime.getRuntime().exec("strace python3 " + Configuracion.DIRECTORIO_RANSOMWARES + "jamsomware.py --dir " + directorioVictima);

        //p.waitFor(10, TimeUnit.SECONDS);

//        InputStream stdout = p.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
//
//        String linea;
//        while ((linea = reader.readLine()) != null) {
//            System.out.println(linea);
//        }
//
//        p.destroy();
    }

    @Override
    public Process decrypt(String directorioVictima) throws Exception {
        return null;
    }

}
