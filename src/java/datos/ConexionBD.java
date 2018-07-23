package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        }finally{
            this.cerrarConexion();
        }
    }
    
    public void registrarPrueba(Prueba prueba){
        try {
            this.conectar();
            this.connection.setAutoCommit(false);
            PreparedStatement ps = this.connection.prepareStatement("INSERT INTO pruebas (id_ransomware,cantidad_datos,cantidad_archivos,detector_habilitado,deteccion_positiva) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prueba.getRansomware().getId());
            ps.setInt(2, prueba.getCantidadDatos());
            ps.setInt(3, prueba.getCantidadArchivos());
            ps.setInt(4, (prueba.isDetectorHabilitado())? 1 : 0);
            ps.setInt(5, (prueba.isDeteccionPositiva())? 1 : 0);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                int idPrueba = keys.getInt(1);
                PreparedStatement psSyscall = this.connection.prepareStatement("INSERT INTO syscall_resultados (syscall,cantidad,id_prueba) VALUES (?,?,?)");
                for(SyscallResultado syscall : prueba.getResultado().getSyscalls()){                    
                    psSyscall.setString(1, syscall.getSyscall());
                    psSyscall.setInt(2, syscall.getCantidad());
                    psSyscall.setInt(3, idPrueba);
                    psSyscall.execute();
                }
                
                PreparedStatement psProcesamiento = this.connection.prepareStatement("INSERT INTO procesamiento_resultados (user, system, id_prueba) VALUES (?,?,?)");
                psProcesamiento.setDouble(1, prueba.getResultado().getProcesamiento().getPorcentajeUsuario());
                psProcesamiento.setDouble(2, prueba.getResultado().getProcesamiento().getProcentajeSistema());
                psProcesamiento.setInt(3, idPrueba);
                psProcesamiento.execute();
                
                PreparedStatement psDisco = this.connection.prepareStatement("INSERT INTO disco_resultados (iowait, idle, id_prueba) VALUES (?,?,?)");
                psDisco.setDouble(1, prueba.getResultado().getDisco().getIowait());
                psDisco.setDouble(2, prueba.getResultado().getDisco().getIdle());
                psDisco.setInt(3, idPrueba);
                psDisco.execute();
                
                PreparedStatement psMemoria = this.connection.prepareStatement("INSERT INTO memoria_resultados (memused, porcentaje_memused, id_prueba) VALUES (?,?,?)");
                psMemoria.setDouble(1, prueba.getResultado().getMemoria().getMemused());
                psMemoria.setDouble(2, prueba.getResultado().getMemoria().getPorcentajeMemused());
                psMemoria.setInt(3, idPrueba);
                psMemoria.execute();
                
                this.connection.commit();
            }else{
                this.connection.rollback();
            }
        } catch (SQLException ex) {
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            this.cerrarConexion();
        }
    }

    public static void main(String args[]) {
        Ransomware r = new Jamsomware("fedee", "Descripcion");
        r.setId(4);
        r.addParameter("clave", "4960520");
        r.addParameter("clave1", "49");
        r.addParameter("clave2", "49605");
        
        SyscallResultado sr = new SyscallResultado("read", 125);
        SyscallResultado sr2 = new SyscallResultado("write", 256);
        ArrayList<SyscallResultado> syscallResultados = new ArrayList<>();
        syscallResultados.add(sr);
        syscallResultados.add(sr2);
        
        ProcesamientoResultado pr = new ProcesamientoResultado(0.25, 0.75);
        DiscoResultado dr = new DiscoResultado(26.7, 95.6);
        MemoriaResultado mr = new MemoriaResultado(236, 66.5);
        Resultado resultado = new Resultado(syscallResultados, pr, dr, mr);
        
        Prueba prueba = new Prueba(r, 80, 2, false);
        prueba.setResultado(resultado);
        
        ConexionBD conexionBD = new ConexionBD();
        conexionBD.registrarPrueba(prueba);

    }
}
