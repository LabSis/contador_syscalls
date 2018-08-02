package estadisticas;

import datos.Resultado;
import datos.SyscallResultado;
import java.io.BufferedReader;
import java.io.IOException;
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
    protected long cantidadDatosACifrarBytes;
    protected String salidaResumidaStrace;
    protected HashMap<String, Integer> mapaSyscalls;

    public Estadisticas(Ransomware ransomware, long cantidadDatosACifrarBytes) {
        this.ransomware = ransomware;
        this.cantidadDatosACifrarBytes = cantidadDatosACifrarBytes;
        this.salidaResumidaStrace = "";
    }

    protected Resultado generarResultado(Process proceso) throws IOException {
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
        Resultado resultado = new Resultado(null, null, null, null);
        ArrayList<SyscallResultado> syscalls = getSyscalls(resultado);
        Collections.sort(syscalls, new Comparator<SyscallResultado>() {
            @Override
            public int compare(SyscallResultado t, SyscallResultado t1) {
                return t1.getCantidad() - t.getCantidad();
            }
        });
        System.out.println(syscalls);
        resultado.setSyscalls(syscalls);
        return resultado;
    }

    public Resultado ejecutar(String directorioVictima) throws Exception {
        Process proceso = ransomware.encrypt(directorioVictima);
        return generarResultado(proceso);
    }

    public ArrayList<SyscallResultado> getSyscalls(Resultado resultado) {
        mapaSyscalls = new HashMap<>();
        ArrayList<SyscallResultado> syscalls = new ArrayList<>();
        String renglones[] = salidaResumidaStrace.split("\n");
        int totalCantidadSyscalls = 0;
        for (int i = 0; i < renglones.length; i++) {
            String renglon = renglones[i].trim();
            String celdas[] = renglon.split("\\s+");

            if (celdas.length > 3) {
                String nombreSyscall = celdas[celdas.length - 1];
                try {
                    int cantidad = Integer.parseInt(celdas[3]);
                    mapaSyscalls.put(nombreSyscall, cantidad);
                    totalCantidadSyscalls += cantidad;
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println(renglon);
                }
            } else {
                throw new RuntimeException("Error al generar las estadísticas. Faltan columnas.");
            }
        }

        for (Map.Entry<String, Integer> entradaSyscall : mapaSyscalls.entrySet()) {
            String nombreSyscall = entradaSyscall.getKey();
            Integer cantidadSyscall = entradaSyscall.getValue();
            SyscallResultado syscallResultado = new SyscallResultado(nombreSyscall, cantidadSyscall);
            if (cantidadDatosACifrarBytes > 0) {
                double k = (double) cantidadSyscall / (double) cantidadDatosACifrarBytes;
                syscallResultado.setK(k);
            }
            double q = (double) cantidadSyscall / (double) totalCantidadSyscalls;
            syscallResultado.setQ(q);
            syscalls.add(syscallResultado);
        }
        resultado.setTotalCantidadSyscalls(totalCantidadSyscalls);

        return syscalls;
    }
}
