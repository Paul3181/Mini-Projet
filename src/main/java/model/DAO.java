package model;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



public class DAO {

	private final DataSource myDataSource;

	/**
	 * Construit le AO avec sa source de données
	 * @param dataSource la source de données à utiliser
	 */
	public DAO(DataSource dataSource) {
		this.myDataSource = dataSource;
	}

        
        /**
         * Methode permettant de valider ou non les informations de connexion
         * transmises par le formulaire de login
         * @param email
         * @param pass
         * @return
         * @throws SQLException 
         */
        public void checkUser(HttpServletRequest request) throws SQLException{   
            
                String sql = "SELECT * FROM CUSTOMER WHERE EMAIL=? AND CUSTOMER_ID=?";
                String email = request.getParameter("email");
                String pass = request.getParameter("pass");
                boolean admin = false;
     
                //on teste si c'est un admin
                if(email.equals("admin") && pass.equals("master")) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("userName", email);
                }
                else{
                    try (Connection connection = myDataSource.getConnection(); 
                        PreparedStatement stmt = connection.prepareStatement(sql)) {
                        boolean check = false;
                        int pass2 = 0;
    
                        //on cast le mot de passe si on peut et on test
                        if(pass.matches("[0-9]+")){
                            pass2 = Integer.parseInt(pass);
                            stmt.setString(1, email);
                            stmt.setInt(2, pass2);
                            ResultSet res = stmt.executeQuery();
                            //check est vrai si la requete retourne 1 resultat faux sinon
                            check = res.next();

                            if (check){
                                    HttpSession session = request.getSession(true);
                                    session.setAttribute("userName", email);
                            }
                            else{
                                request.setAttribute("errorMessage", "Identifiant ou mot de passe incorrect!");
                            }
                        }
                        //sinon mauvais mdp
                        else{
                            request.setAttribute("errorMessage", "Identifiant ou mot de passe incorrect!");
                        }   
                    }         
                }                 
        }
        
        /**
	 * Contenu de la table PRODUCTS
	 * @return Liste des discount codes
	 * @throws SQLException renvoyées par JDBC
	 */
	public List<ProductEntity> allProducts() throws SQLException {

		List<ProductEntity> result = new LinkedList<>();

		String sql = "SELECT * FROM PRODUCT";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String product = rs.getString("DESCRIPTION");
				float price = rs.getFloat("PURCHASE_COST");
				ProductEntity c = new ProductEntity(product, price);
				result.add(c);
			}
		}
		return result;
	}  


}
