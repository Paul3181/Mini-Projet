/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import model.DAO;
import model.DataSourceFactory;


/**
 *
 * @author pauld
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    
    
    //private final DataSource myDataSource;
    /**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	//public Login(DataSource dataSource) {
	//	this.myDataSource = dataSource;
	//}
        

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
            String product = request.getParameter("product");
            String price = request.getParameter("price");
            
            //Quelle action a appellée le service ?
            String action = request.getParameter("action");
            if (action != null){
                switch (action) {
                    case "login":
                        DAO dao = new DAO(DataSourceFactory.getDataSource());
                        dao.checkUser(request);
                        break;
                    case "logout":
                        doLogout(request);
                        break;
                            
                }
            }
                 
            //On cherche a redirigé l'utilisateur 
            String userName = findUserInSession(request);
            String jspView;
            
            //On choisi la page en fonction du statut de l'utilisateur 
                      
            if (userName == null){
                jspView = "login.jsp";
                
                //On redirige vers cette page 
                request.getRequestDispatcher(jspView).forward(request, response);
            }
            
            if (userName.equals("admin")){
                jspView = "admin.jsp";
                //On redirige vers cette page 
                request.getRequestDispatcher(jspView).forward(request, response);
            }
            
                
            else{
                jspView = "client.jsp";
                try (PrintWriter out = response.getWriter()) {
                    DAO dao = new DAO(DataSourceFactory.getDataSource());   
                    //On récupère les enregistrements de la table de PRODUCTS
                    request.setAttribute("products", dao.allProducts());
                    //On redirige vers cette page 
                    request.getRequestDispatcher(jspView).forward(request, response);
                }
            }
            
            
            
             
        }
 
            
	private void doLogout(HttpServletRequest request) {
		// On termine la session
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	private String findUserInSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session == null) ? null : (String) session.getAttribute("userName");
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
