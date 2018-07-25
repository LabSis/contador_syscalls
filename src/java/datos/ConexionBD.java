package datos;

import ransomware.Jamsomware;
import ransomware.Ransomware;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fdrcbrtl
 */
public class ConexionBD {

    private String BD = "gestion_ransomware";
    private String USUARIO = "root";
    private String CLAVE = "";

    private Connection connection;

    public ConexionBD() {

    }

    private void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/" + this.BD;
            this.connection = DriverManager.getConnection(servidor, USUARIO, CLAVE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cerrarConexion() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarRansomware(Ransomware ransomware) {
        try {
            this.conectar();
            this.connection.setAutoCommit(false);
            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO ransomwares (nombre, descripcion) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, ransomware.getName());
            ps.setString(2, ransomware.getDescription());
            ps.executeUpdate();
            ResultSet ids = ps.getGeneratedKeys();
            if (ids.next()) {
                int idRansomware = ids.getInt(1);
                for (Map.Entry<String, String> entrada : ransomware.getParametros().entrySet()) {
                    PreparedStatement psParametro = this.connection.prepareStatement("INSERT INTO parametros (clave, valor,id_ransomware) VALUES (?,?,?)");
                    psParametro.setString(1, entrada.getKey());
                    psParametro.setString(2, entrada.getValue());
                    psParametro.setInt(3, idRansomware);
                    psParametro.execute();
                }
                this.connection.commit();
            }

        } catch (SQLException ex) {
            try {
                this.connection.rollback();
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            this.cerrarConexion();
        }
    }

    public void registrarPrueba(Prueba prueba) {
        try {
            this.conectar();
            this.connection.setAutoCommit(false);
            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO pruebas (id_ransomware,cantidad_datos,cantidad_archivos,detector_habilitado,deteccion_positiva,tiempo_ejecucion_ms) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prueba.getRansomware().getId());
            ps.setInt(2, prueba.getCantidadDatos());
            ps.setInt(3, prueba.getCantidadArchivos());
            ps.setInt(4, (prueba.isDetectorHabilitado()) ? 1 : 0);
            ps.setInt(5, (prueba.isDeteccionPositiva()) ? 1 : 0);
            ps.setLong(6, prueba.getTiempoEjecucion());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int idPrueba = keys.getInt(1);
                PreparedStatement psSyscall = this.connection.prepareStatement("INSERT INTO syscall_resultados (syscall,cantidad,id_prueba) VALUES (?,?,?)");
                for (SyscallResultado syscall : prueba.getResultado().getSyscalls()) {
                    psSyscall.setString(1, syscall.getSyscall());
                    psSyscall.setInt(2, syscall.getCantidad());
                    psSyscall.setInt(3, idPrueba);
                    psSyscall.execute();
                }

                PreparedStatement psProcesamiento = this.connection.prepareStatement("INSERT INTO procesamiento_resultados (user, system, id_prueba) VALUES (?,?,?)");
                double user = 0;
                double system = 0;
                if(prueba.getResultado().getProcesamiento() != null){
                    user = prueba.getResultado().getProcesamiento().getPorcentajeUsuario();
                    system = prueba.getResultado().getProcesamiento().getPorcentajeSistema();
                }
                psProcesamiento.setDouble(1, user);
                psProcesamiento.setDouble(2, system);
                psProcesamiento.setInt(3, idPrueba);
                psProcesamiento.execute();

                PreparedStatement psDisco = this.connection.prepareStatement("INSERT INTO disco_resultados (iowait, idle, id_prueba) VALUES (?,?,?)");
                double iowait = 0;
                double idle = 0;
                if(prueba.getResultado().getDisco() != null){
                    iowait = prueba.getResultado().getDisco().getIowait();
                    idle = prueba.getResultado().getDisco().getIdle();
                }
                psDisco.setDouble(1, iowait);
                psDisco.setDouble(2, idle);
                psDisco.setInt(3, idPrueba);
                psDisco.execute();

                PreparedStatement psMemoria = this.connection.prepareStatement("INSERT INTO memoria_resultados (memused, porcentaje_memused, id_prueba) VALUES (?,?,?)");
                double memused = 0;
                double porcentajeMemused = 0;
                if(prueba.getResultado().getMemoria() != null){
                    memused = prueba.getResultado().getMemoria().getMemused();
                    porcentajeMemused = prueba.getResultado().getMemoria().getPorcentajeMemused();
                }
                psMemoria.setDouble(1, memused);
                psMemoria.setDouble(2, porcentajeMemused);
                psMemoria.setInt(3, idPrueba);
                psMemoria.execute();

                this.connection.commit();
            } else {
                this.connection.rollback();
            }
        } catch (SQLException ex) {
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.cerrarConexion();
        }
    }

    public ArrayList<Prueba> getPruebas() {
        ArrayList<Prueba> pruebas = new ArrayList<>();
        try {
            this.conectar();
            String consultaPruebas = "SELECT p.id,p.id_ransomware,r.nombre,r.descripcion,"
                    + "p.cantidad_datos,p.cantidad_archivos,p.detector_habilitado,p.deteccion_positiva,"
                    + "p.tiempo_ejecucion_ms,p.fecha_hora,d.iowait,d.idle,m.memused,m.porcentaje_memused,pro.user,pro.system "
                    + "FROM pruebas AS p INNER JOIN ransomwares AS r ON p.id_ransomware=r.id "
                    + "INNER JOIN disco_resultados AS d ON p.id=d.id_prueba "
                    + "INNER JOIN memoria_resultados AS m ON p.id=m.id_prueba "
                    + "INNER JOIN procesamiento_resultados AS pro ON p.id=pro.id_prueba";
            PreparedStatement psPruebas = this.connection.prepareStatement(consultaPruebas);

            String consultaSyscalls = "SELECT syscall,cantidad "
                    + "FROM syscall_resultados "
                    + "WHERE id_prueba=?";
            PreparedStatement psSyscalls = this.connection.prepareStatement(consultaSyscalls);

            ResultSet pruebasResultSet = psPruebas.executeQuery();
            while (pruebasResultSet.next()) {
                int id = pruebasResultSet.getInt("id");
                int idRansomware = pruebasResultSet.getInt("id_ransomware");
                String nombreRansomware = pruebasResultSet.getString("nombre");
                String descripcionRansomware = pruebasResultSet.getString("descripcion");
                int cantidadDatos = pruebasResultSet.getInt("cantidad_datos");
                int cantidadArchivos = pruebasResultSet.getInt("cantidad_archivos");
                boolean detectorHabilitado = pruebasResultSet.getBoolean("detector_habilitado");
                boolean deteccionPositiva = pruebasResultSet.getBoolean("deteccion_positiva");
                long tiempoEjecucion = pruebasResultSet.getLong("tiempo_ejecucion_ms");
                Date fecha = pruebasResultSet.getDate("fecha_hora");
                
                //Corregir esto.
                Ransomware ransomware = new Jamsomware(idRansomware, nombreRansomware, descripcionRansomware);
                Prueba prueba = new Prueba(ransomware, cantidadDatos, cantidadArchivos, detectorHabilitado);
                prueba.setDeteccionPositiva(deteccionPositiva);
                prueba.setTiempoEjecucion(tiempoEjecucion);
                prueba.setFechaHora(fecha);
                
                /* Resultado */
                double iowait = pruebasResultSet.getDouble("iowait");
                double idle = pruebasResultSet.getDouble("idle");
                DiscoResultado discoResultado = new DiscoResultado(iowait, idle);
                
                double user = pruebasResultSet.getDouble("user");
                double system = pruebasResultSet.getDouble("system");
                ProcesamientoResultado procesamientoResultado = new ProcesamientoResultado(user, system);
                
                double memused = pruebasResultSet.getDouble("memused");
                double porcentajeMemused = pruebasResultSet.getDouble("porcentaje_memused");
                MemoriaResultado memoriaResultado = new MemoriaResultado(memused, porcentajeMemused);
                
                ArrayList<SyscallResultado> syscallResultados = new ArrayList<>();
                psSyscalls.setInt(1, id);
                ResultSet syscallsResultSet = psSyscalls.executeQuery();
                while(syscallsResultSet.next()){
                    String syscall = syscallsResultSet.getString("syscall");
                    int cantidad = syscallsResultSet.getInt("cantidad");
                    SyscallResultado syscallResultado = new SyscallResultado(syscall, cantidad);
                    syscallResultados.add(syscallResultado);
                }
                
                Resultado resultado = new Resultado(syscallResultados, procesamientoResultado, discoResultado, memoriaResultado);
                prueba.setResultado(resultado);
                pruebas.add(prueba);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.cerrarConexion();
        }
        return pruebas;
    }

    public static void main(String args[]) {
        

        ConexionBD conexionBD = new ConexionBD();
        conexionBD.getPruebas();

    }
}
