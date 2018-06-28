/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import com.google.gson.Gson;
import datos.GeneradorDeArchivos;
import datos.Ransomware;
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
        String nombreRansomware = request.getParameter("id_ransomware");
        String cantidadDatosACifrarString = request.getParameter("cantidad_datos_a_cifrar");
        String cantidadArchivosString = request.getParameter("cantidad_archivos");
        int cantidadDatosACifrar = 30;
        int cantidadArchivos = 5;
        
        try {
            cantidadDatosACifrar = Integer.parseInt(cantidadDatosACifrarString);
        } catch (NumberFormatException nfe) {

        }
        try {
            cantidadArchivos = Integer.parseInt(cantidadArchivosString);
        } catch (NumberFormatException nfe) {

        }
        
        String directorio = "/home/gochi/Proyectos/GestionRansomware/test/";
        File directorioFile = new File(directorio);
        if (!directorioFile.exists()) {
            directorioFile.createNewFile();
        }
        GeneradorDeArchivos ga = new GeneradorDeArchivos();
        ga.generar(directorio, cantidadDatosACifrar, cantidadArchivos);
        
        /*Ransomware r = datos.Datos.getRansomware(nombreRansomware);
        try {
            r.encrypt(directorio);
        } catch (Exception ex) {
            Logger.getLogger(ControladorRansomware.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (directorioFile.exists()) {
            directorioFile.delete();
        }*/
    }

}
