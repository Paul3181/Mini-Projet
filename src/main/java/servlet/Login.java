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
import java.sql.SQLIntegrityConstraintViolationException;
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
import model.ProductEntity;
import model.PurchaseEntity;


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
            String qte = request.getParameter("qte");
            String rate = request.getParameter("rate");
                
            String num = request.getParameter("num");
            String customerP = request.getParameter("customerP");
            String sales = request.getParameter("sales");
            String shipping = request.getParameter("shipping");
            String company = request.getParameter("company");
                
            String numO = request.getParameter("numO");
            String productO = request.getParameter("productO");
            String priceO = request.getParameter("priceO");
            String quantityO = request.getParameter("quantityO");
            String salesO = request.getParameter("salesO");
            String shippingO = request.getParameter("shippingO");
            String companyO = request.getParameter("companyO");
            String customerO = request.getParameter("customerO");
            
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
                    
                    // create table
                    dao.createTable();
                    //dao.deleteTable();
                    
                    // Id du customer
                    int customer = dao.customerId(userName);
                    
                    List<String> categories = dao.existingCategory();
                    String category = request.getParameter("category");
                    // On n'a pas forcément le paramètre
                    if (null == category) {
                        category = categories.get(1);
                    }
                        
                    List<ProductEntity> products = dao.productsInCategory(category);
                    request.setAttribute("products", products);
                    request.setAttribute("existingCategories", categories);
                    request.setAttribute("orders", dao.allOrders(customer));
                    request.setAttribute("selectedCategory", category);
                    //request.setAttribute("orders", dao.allOrders());
                            
                    switch (action) {
                        case "Add": // Requête d'ajout (vient du formulaire de saisie)
                            String product1 = request.getParameter("product1");
                            String price1 = request.getParameter("price1");
                            String quantity1 = request.getParameter("quantity1");
                            String qte1 = request.getParameter("qte1");
                            PurchaseEntity purchase = dao.allPurchase(customer, product1);
                                
                            float price2 = Float.parseFloat(price1);
                            int quantity2 = Integer.parseInt(quantity1);
                            int qte2 = Integer.parseInt(qte1);
                            
                            try{
                                int num1 = purchase.getNum();
                                int customer1 = purchase.getCustomerP();
                                String sales1 = purchase.getSales();
                                String shipping1 = purchase.getShipping();
                                String company1 = purchase.getCompany();
                                if(quantity2<=qte2){
                                    dao.insert(num1, product1, price2, quantity2, sales1, shipping1, company1, customer1);
                                }else{
                                    request.setAttribute("message3", "Quantity not available !");
                                }
                                request.setAttribute("orders", dao.allOrders(customer));
                            }catch(NullPointerException e){
                                request.setAttribute("message2", "Impossible to add !");
                                request.setAttribute("orders", dao.allOrders(customer));
                            }
                            break;
                            
                        case "Delete": // Requête de suppression (vient du lien hypertexte)
                            String numD = request.getParameter("numD");
                            System.out.println(numD);
                            try {
                                dao.deleteOrder(numD);
                                request.setAttribute("message", "Order " + numD + " has been deleted");
                                request.setAttribute("orders", dao.allOrders(customer));								
                            } catch (SQLIntegrityConstraintViolationException e) {
                            }
                            break;
                                
                        case "Modify": // Requête de modification
                            String qte3 = request.getParameter("qte3");
                            String numD1 = request.getParameter("numD");
                            int qte4 = Integer.parseInt(qte3);
                            int numD2 = Integer.parseInt(numD1);
                            dao.modifyQte(qte4, numD2);
                            request.setAttribute("message4", "Quantity modified");
                            request.setAttribute("orders", dao.allOrders(customer));
                            break;
			}
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
