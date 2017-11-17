/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.DataSourceFactory;
import model.DiscountCode;

/**
 *
 * @author pauld
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DAO dao = new DAO(DataSourceFactory.getDataSource());
            //On recupère les données saisies
            String email = request.getParameter("email");         
            String pass = request.getParameter("pass");
           
            //on vérifie la presence des paramètres
            if(email==null || pass==null){
                RequestDispatcher rs = request.getRequestDispatcher("index.html");
                rs.include(request, response);
            }
            
            //Si les parametres sont définis on regarde leur validité        
            else{           
                //On verifie que le mot de passe peut etre parsé
                if(pass.matches("[0-9]+")){
                    int pass2 = Integer.parseInt(request.getParameter("pass"));
                    out.println(pass2);
                    //si oui on verifie:
                    if(dao.checkUser(email, pass2)){
                        RequestDispatcher rs = request.getRequestDispatcher("Client.html");
                        rs.forward(request, response);
                    }
                    //Les informations sont fausses
                    else{
                        out.println("Identifiant ou mot de passe incorrect");
                        RequestDispatcher rs = request.getRequestDispatcher("index.html");
                        rs.include(request, response);
                    }
                } 
                //Le mot de passe n'est pas du bon format
                else{
                   out.println("Identifiant ou mot de passe incorrect");
                   RequestDispatcher rs = request.getRequestDispatcher("index.html");
                   rs.include(request, response);
                }
            }
                  
           
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
