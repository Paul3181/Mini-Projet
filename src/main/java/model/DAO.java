package model;

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
	 * Contenu de la table DISCOUNT_CODE
	 * @return Liste des discount codes
	 * @throws SQLException renvoyées par JDBC
	 */
	public List<DiscountCode> allCodes() throws SQLException {

		List<DiscountCode> result = new LinkedList<>();

		String sql = "SELECT * FROM DISCOUNT_CODE ORDER BY DISCOUNT_CODE";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("DISCOUNT_CODE");
				float rate = rs.getFloat("RATE");
				DiscountCode c = new DiscountCode(id, rate);
				result.add(c);
			}
		}
		return result;
	}

	/**
	 * Ajout d'un enregistrement dans la table DISCOUNT_CODE
	 * @param code le code (non null)
	 * @param rate le taux (positive or 0)
	 * @return 1 si succès, 0 sinon
	 * @throws SQLException renvoyées par JDBC
	 */
	public int addDiscountCode(String code, float rate) throws SQLException {
		int result = 0;
		String sql = "INSERT INTO DISCOUNT_CODE VALUES (?, ?)";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, code);
			stmt.setFloat(2, rate);
			result = stmt.executeUpdate();
		}
		return result;
	}

		
	/**
	 * Supprime un enregistrement dans la table DISCOUNT_CODE
	 * @param code la clé de l'enregistrement à supprimer
	 * @return le nombre d'enregistrements supprimés (1 ou 0)
	 * @throws java.sql.SQLException renvoyées par JDBC
	 **/
	public int deleteDiscountCode(String code) throws SQLException {
		int result = 0;
		String sql = "DELETE FROM DISCOUNT_CODE WHERE DISCOUNT_CODE = ?";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, code);
			result = stmt.executeUpdate();
		}
		return result;
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
                
                try (Connection connection = myDataSource.getConnection(); 
                         PreparedStatement stmt = connection.prepareStatement(sql)) {
                    boolean check = false;
                    //On recupere les parametres
                    String email = request.getParameter("email");
                    String pass = request.getParameter("pass");
                    int pass2 = 0;
                    //On verifie la validité
                    
                    //parametres incomplet
                    if(email==null || pass==null){
                        request.setAttribute("errorMessage", "Identifiant ou mot de passe incorrect!");
                    }
                    
                    //on cast le mot de passe si on peut
                    if(pass.matches("[0-9]+")){
                        pass2 = Integer.parseInt(pass);
                    }
                    //sinon on test si c'est un admin
                    else{
                        if(email=="admin" && pass=="master") {
                            HttpSession session = request.getSession(true);
                            session.setAttribute("userName", email);
                        }
                        else{
                            request.setAttribute("errorMessage", "Identifiant ou mot de passe incorrect!");
                        }
                    }
                    

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
