/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.google.gson.Gson;
import datos.ConexionBD;
import datos.Configuracion;
import datos.GeneradorDeArchivos;
import datos.Prueba;
import datos.Resultado;
import estadisticas.Estadisticas;
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
import ransomware.Jamsomware;

/**
 *
 * @author gochi
 */
@WebServlet(name = "ControladorRansomware", urlPatterns = {"/ControladorRansomware"})
public class ControladorRansomware extends HttpServlet {

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
        ArrayList<Ransomware> ransomwares = datos.Datos.cargarRansomwares();
        String json = new Gson().toJson(ransomwares);
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
        String idRansomwareString = request.getParameter("id_ransomware");
        String cantidadDatosACifrarString = request.getParameter("cantidad_datos_a_cifrar");
        String cantidadArchivosString = request.getParameter("cantidad_archivos");
        String dejarArchivosCreadosString = request.getParameter("dejar_archivos_creados");
        int cantidadDatosACifrar = 30;
        int cantidadArchivos = 5;
        int idRansomware = 1;
        boolean dejarArchivosCreados = false;

        try {
            idRansomware = Integer.parseInt(idRansomwareString);
        } catch (NumberFormatException nfe) {

        }

        try {
            cantidadDatosACifrar = Integer.parseInt(cantidadDatosACifrarString);
        } catch (NumberFormatException nfe) {

        }
        try {
            cantidadArchivos = Integer.parseInt(cantidadArchivosString);
        } catch (NumberFormatException nfe) {

        }

        try {
            dejarArchivosCreados = Boolean.parseBoolean(dejarArchivosCreadosString);
        } catch (NumberFormatException nfe) {

        }       
        
        String nombreDirectorio = "test_" + ((int) (Math.random() * 1000)) + "_" + System.currentTimeMillis();
        String directorio = Configuracion.DIRECTORIO_EJECUCION_CIFRADO + nombreDirectorio + "/";
        File directorioFile = new File(directorio);
        if (!directorioFile.exists()) {
            directorioFile.mkdir();
        }
        GeneradorDeArchivos ga = new GeneradorDeArchivos();
        ga.generar(directorio, cantidadDatosACifrar, cantidadArchivos);

        Ransomware r = datos.Datos.getRansomware(idRansomware);
        Prueba prueba = new Prueba(r,cantidadDatosACifrar, cantidadArchivos, false);
        long tI = System.currentTimeMillis();
        Resultado resultado = null;
        if (r != null) {
            try {
                Estadisticas estadisticas = new Estadisticas(r, cantidadDatosACifrar * 1024  * 1024);
                resultado = estadisticas.ejecutar(directorio);
                long tF = System.currentTimeMillis();
                long msEjecucion = tF - tI;
                prueba.setTiempoEjecucion(msEjecucion);
                prueba.setResultado(resultado);
                bd.registrarPrueba(prueba);
            } catch (Exception ex) {
                Logger.getLogger(ControladorRansomware.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!dejarArchivosCreados && directorioFile.exists()) {
            for (String nombreArchivo : directorioFile.list()) {
                File archivo = new File(directorioFile.getPath(), nombreArchivo);
                archivo.delete();
            }
            directorioFile.delete();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (resultado != null) {
            String json = new Gson().toJson(resultado);
            response.getWriter().write(json);
        }
    }

}
