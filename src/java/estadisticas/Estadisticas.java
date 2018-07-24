package estadisticas;

import datos.Resultado;
import datos.SyscallResultado;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import ransomware.Ransomware;

public class Estadisticas {

    protected Ransomware ransomware;
    protected String salidaStrace;
    protected HashMap<String, Integer> mapaSyscalls;

    public Estadisticas(Ransomware ransomware) {
        this.ransomware = ransomware;
        this.salidaStrace = "";

    }

    public Resultado ejecutar(String directorioVictima) throws Exception {
        Process proceso = ransomware.encrypt(directorioVictima);
        InputStream stderr = proceso.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stderr));

        String line = reader.readLine();
        while (line != null) {
            line = reader.readLine();
            salidaStrace += line + "\n";
        }
        ArrayList<SyscallResultado> syscalls = getSyscalls();
        Collections.sort(syscalls, new Comparator<SyscallResultado>() {
            @Override
            public int compare(SyscallResultado t, SyscallResultado t1) {
                return t1.getCantidad() - t.getCantidad();
            }
            
        });
        Resultado resultado = new Resultado(syscalls, null, null, null);
        return resultado;
    }

    public ArrayList<SyscallResultado> getSyscalls() {
        mapaSyscalls = new HashMap<>();
        ArrayList<SyscallResultado> syscalls = new ArrayList<>();
        String renglones[] = salidaStrace.split("\n");
        for (int i = 0; i < renglones.length; i++) {
            String renglon = renglones[i];
            int indiceParentesis = renglon.indexOf("(");
            if (indiceParentesis > 0) {
                String nombreSyscall = renglon.substring(0, indiceParentesis);
                Integer cantidad = mapaSyscalls.get(nombreSyscall);
                if (cantidad != null) {
                    mapaSyscalls.put(nombreSyscall, cantidad + 1);
                } else {
                    mapaSyscalls.put(nombreSyscall, 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entradaSyscall : mapaSyscalls.entrySet()) {
            String nombreSyscall = entradaSyscall.getKey();
            Integer cantidadSyscall = entradaSyscall.getValue();
            SyscallResultado syscallResultado = new SyscallResultado(nombreSyscall, cantidadSyscall);
            syscalls.add(syscallResultado);
        }

        return syscalls;
    }
}
