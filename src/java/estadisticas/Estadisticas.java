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
    protected String salidaResumidaStrace;
    protected HashMap<String, Integer> mapaSyscalls;

    public Estadisticas(Ransomware ransomware) {
        this.ransomware = ransomware;
        this.salidaResumidaStrace = "";

    }

    public Resultado ejecutar(String directorioVictima) throws Exception {
        Process proceso = ransomware.encrypt(directorioVictima);
        InputStream stderr = proceso.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stderr));

        boolean comienzaTabla = false;
        boolean leyendoTabla = false;
        String line = reader.readLine();
        while (line != null) {
            if (leyendoTabla) {
                if (line.contains("------")) {
                    break;
                }
                salidaResumidaStrace += line + "\n";
            }
            if (comienzaTabla) {
                leyendoTabla = true;
            }
            if (line.contains("syscall")) {
                comienzaTabla = true;
            }
            line = reader.readLine();
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
        String renglones[] = salidaResumidaStrace.split("\n");
        for (int i = 0; i < renglones.length; i++) {
            String renglon = renglones[i];
            String celdas[] = renglon.split("\\s+");

            if (celdas.length > 4) {
                String nombreSyscall = celdas[celdas.length - 1];
                System.out.println(nombreSyscall);
                mapaSyscalls.put(nombreSyscall, Integer.parseInt(celdas[4]));
            } else {
                throw new RuntimeException("Error al generar las estadísticas. Faltan columnas.");
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
