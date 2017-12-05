package model;

/**
 * Un enregistrement de la table PURCHASE_ENTITY
 * @author Thida
 */
public class PurchaseEntity {

	private int num;
    private int customerP;
	private String sales;
	private String shipping;
	private String company;

	public PurchaseEntity(int num, int customerP, String sales, String shipping, String company) {
		this.num = num;
		this.customerP = customerP;
		this.sales = sales;
		this.shipping = shipping;
		this.company = company;
	}

	public int getNum() {
		return num;
	}
        
        public int getCustomerP() {
		return customerP;
	}

	public String getSales() {
		return sales;
	}
        
        public String getShipping() {
		return shipping;
	}  
        
        public String getCompany() {
		return company;
	}

}
