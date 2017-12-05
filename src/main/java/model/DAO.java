package model;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
				//ProductEntity c = new ProductEntity(product, price);
				//result.add(c);
			}
		}
		return result;
	}         
        	public List<ChiffreAEntity> chiffreAbyCat() throws SQLException {

		List<ChiffreAEntity> result = new LinkedList<>();

		String sql = "SELECT PRODUCT_ID, SUM(QUANTITY) AS QUANTITY_TOTAL FROM APP.PURCHASE_ORDER GROUP BY PRODUCT_ID";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String product = rs.getString("DESCRIPTION");
				float price = rs.getFloat("PURCHASE_COST");
				//ProductEntity c = new ProductEntity(product, price);
				//result.add(c);
                                
			}
		}
		return result;
	} 


                
                public List<LocEntity> Localisation() throws SQLException {
		List<LocEntity> result = new LinkedList<>();
		String sql = "SELECT DISTINCT m.AREA_LENGTH, m.AREA_WIDTH, c.CITY FROM APP.CUSTOMER as c, APP.MICRO_MARKET as m WHERE c.ZIP=APP.m.ZIP_CODE";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                                float L = rs.getFloat("AREA_LENGTH");
                                float W = rs.getFloat("AREA_WIDTH");
				String C = rs.getString("CHIFFREA");
				LocEntity ici = new LocEntity(L,W,C);
				result.add(ici);
			}
		}
		return result;
	} 
             /**
	 * ID du customer dans la table CUSTOMER
	 * @param email
	 * @return l'id du customer
	 * @throws SQLException
	 */
	public int customerId(String mail) throws SQLException {
		int result = 0;
		String sql = "SELECT DISTINCT CUSTOMER_ID FROM CUSTOMER WHERE EMAIL = ?";
                try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setString(1, mail);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
                            result = rs.getInt("CUSTOMER_ID");
			}
		}
		return result;
	}
        
             /**
	 * Liste des catégories dans la table PRODUCT_CODE
	 * @return la liste des catégories
	 * @throws SQLException
	 */
	public List<String> existingCategory() throws SQLException {
		List<String> result = new LinkedList<>();
		String sql = "SELECT DISTINCT DESCRIPTION FROM PRODUCT_CODE";
                try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String category = rs.getString("DESCRIPTION");
				result.add(category);
			}
		}
		return result;
	}
        
	 
	public List<ProductEntity> productsInCategory(String category) throws SQLException {
                List<ProductEntity> result = new ArrayList<ProductEntity>();
                //String sql = "SELECT * FROM PRODUCT AS P INNER JOIN PRODUCT_CODE AS PC ON P.PRODUCT_CODE = PC.PROD_CODE WHERE PC.DESCRIPTION = ?";
                String sql = "SELECT * FROM PRODUCT AS P INNER JOIN PRODUCT_CODE AS PC ON P.PRODUCT_CODE = PC.PROD_CODE INNER JOIN DISCOUNT_CODE AS DC ON PC.DISCOUNT_CODE = DC.DISCOUNT_CODE WHERE PC.DESCRIPTION = ?";
                try (Connection connection = myDataSource.getConnection(); 
		    PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, category); // Définir la valeur du paramètre
                    try (ResultSet rs = stmt.executeQuery()){
                        while(rs.next()){
                            String product = rs.getString("DESCRIPTION");
                            float price = rs.getFloat("PURCHASE_COST");
                            int qte = rs.getInt("QUANTITY_ON_HAND");
                            float rate = rs.getFloat("RATE");
                            ProductEntity c = new ProductEntity(product, price, qte, rate);
                            result.add(c);
                        }
                    }
		}
		return result;
	}
        
                 
        public PurchaseEntity allPurchase(int customer, String product) throws SQLException {

		PurchaseEntity result = null;

		String sql = "SELECT * FROM PRODUCT AS P INNER JOIN PURCHASE_ORDER AS PO ON P.PRODUCT_ID = PO.PRODUCT_ID WHERE CUSTOMER_ID = ? AND P.DESCRIPTION = ?";
		try (Connection connection = myDataSource.getConnection(); 
		    PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, customer);
                    stmt.setString(2, product);
                    ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("ORDER_NUM");
                                int customerP = rs.getInt("CUSTOMER_ID");
				String sales = rs.getString("SALES_DATE");
                                String shipping = rs.getString("SHIPPING_DATE");
                                String company = rs.getString("FREIGHT_COMPANY");
				result = new PurchaseEntity(num, customerP, sales, shipping, company);
			}
		}
		return result;
	}
         
        
        /**
	 * Créer une table ORDERS dans la base de données
	 * @return Table ORDERS
	 * @throws SQLException renvoyées par JDBC
	 */
        public void createTable() throws SQLException{
            String sql = "CREATE TABLE ORDERS (NUM INT, PRODUCT VARCHAR(55), PRICE DECIMAL, QUANTITY INT, SALES_DATE VARCHAR(30), SHIPPING_DATE VARCHAR(30), COMPANY VARCHAR(30), CUSTOMER_ID INT, PRIMARY KEY(NUM))";
            try {
		Connection conn = myDataSource.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
                System.out.println("La table a été créee");

            } catch (SQLException e) {
                
            } finally {
                
            }
        }
        
        /**
	 * Insère un enregistrement dans la table ORDERS
	 * @param customerO
	 * @param numO
         * @param productO
         * @param priceO
         * @param quantityO
         * @param salesO
         * @param shippingO
         * @param companyO
	 * @return les enregistrements de la table ORDERS
	 */
        public void insert(int numO, String productO, float priceO, int quantityO, String salesO, String shippingO, String companyO, int customerO) {
            String sql = "INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, numO);
			stmt.setString(2, productO);
			stmt.setFloat(3, priceO);
                        stmt.setInt(4, quantityO);
			stmt.setString(5, salesO);
			stmt.setString(6, shippingO);
                        stmt.setString(7, companyO);
                        stmt.setInt(8, customerO);
			stmt.executeUpdate();
			System.out.println("INSERT INTO");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
        
	 
        public List<OrderEntity> allOrders(int customer_id) throws SQLException {

		List<OrderEntity> result = new LinkedList<>();

		String sql = "SELECT * FROM ORDERS WHERE CUSTOMER_ID = ?"; //WHERE CUSTOMER_ID = ?"
		try (Connection connection = myDataSource.getConnection(); 
		    PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, customer_id);
                    ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int numO = rs.getInt("NUM");
				String productO = rs.getString("PRODUCT");
                                float priceO = rs.getFloat("PRICE");
                                int quantityO = rs.getInt("QUANTITY");
				String salesO = rs.getString("SALES_DATE");
                                String shippingO = rs.getString("SHIPPING_DATE");
                                String companyO = rs.getString("COMPANY");
                                int customerO = rs.getInt("CUSTOMER_ID");
				OrderEntity c = new OrderEntity(numO, productO, priceO, quantityO, salesO, shippingO, companyO, customerO);
				result.add(c);
			}
		}
		return result;
	}
        
        
        /**
	 * Supprime un enregistrement dans la table ORDERS
	 * @param numO la clé de l'enregistrement à supprimer
	 * @return le nombre d'enregistrements supprimés
	 * @throws java.sql.SQLException renvoyées par JDBC
	 **/
	public int deleteOrder(String numO) throws SQLException {
		int result = 0;
		String sql = "DELETE FROM ORDERS WHERE NUM = ?";
		try (Connection connection = myDataSource.getConnection(); 
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, numO);
			result = stmt.executeUpdate();
		}
		return result;
	}
        
        /**
	 * Modifie la quantitée d'une commande dans la table ORDERS
	 * @param qte
         * @param num
	 * @return l'enregistrement avec la quantitée modifiée
	 * @throws java.sql.SQLException renvoyées par JDBC
	 **/
        public void modifyQte(int qte, int num) throws SQLException {

		String sql = "UPDATE ORDERS SET QUANTITY = ? WHERE NUM = ?";
		try (Connection connection = myDataSource.getConnection(); 
		    PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, qte);
                    stmt.setInt(2, num);
                    stmt.executeUpdate();
			}
        }

        public void deleteTable() throws SQLException{
            String sql = "DROP TABLE ORDERS";
            try {
		Connection conn = myDataSource.getConnection();
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
                System.out.println("La table a été supprimée");

            } catch (SQLException e) {
                
            } finally {
                
            }
        }       
}
