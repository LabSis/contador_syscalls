package controladores;

import com.google.gson.Gson;
import datos.ConexionBD;
import datos.Configuracion;
import datos.GeneradorDeArchivos;
import datos.Prueba;
import datos.Resultado;
import estadisticas.Estadisticas;
import estadisticas.EstadisticasEjecutable;
import ransomware.Ransomware;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import programas.Ejecutable;
import ransomware.Jamsomware;

/**
 *
 * @author gochi
 */
@WebServlet(name = "ControladorEjecutable", urlPatterns = {"/ControladorEjecutable"})
public class ControladorEjecutable extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorRansomware</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorRansomware at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Ejecutable> ejecutables = datos.Datos.cargarEjecutables();
        String json = new Gson().toJson(ejecutables);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConexionBD bd = new ConexionBD();
        datos.Datos.cargarEjecutables();
        String idEjecutableString = request.getParameter("id_programa");

        int idEjecutable = 1;

        try {
            idEjecutable = Integer.parseInt(idEjecutableString);
        } catch (NumberFormatException nfe) {

        }

        Ejecutable e = datos.Datos.getEjecutable(idEjecutable);
        Resultado resultado = null;
        if (e != null) {
            try {
                EstadisticasEjecutable estadisticas = new EstadisticasEjecutable(e);
                resultado = estadisticas.ejecutar();
            } catch (Exception ex) {
                Logger.getLogger(ControladorEjecutable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (resultado != null) {
            String json = new Gson().toJson(resultado);
            response.getWriter().write(json);
        }
    }

}
