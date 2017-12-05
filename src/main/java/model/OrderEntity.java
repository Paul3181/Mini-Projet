package model;

/**
 * Un enregistrement de la table ORDERS
 * @author Thida
 */
public class OrderEntity {

        private int numO;
		private String productO;
		private float priceO;
        private int quantityO;
        private String salesO;
        private String shippingO;
        private String companyO;
        private int customerO;
        
        public OrderEntity() {

	}

	public OrderEntity(int numO, String productO, float priceO, int quantityO, String salesO, String shippingO, String companyO, int customerO) {
            this.numO = numO;
            this.productO = productO;
			this.priceO = priceO;
            this.quantityO = quantityO;
			this.salesO = salesO;
			this.shippingO = shippingO;
			this.companyO = companyO;
			this.customerO = customerO;
	}

    public int getNumO() {
		return numO;
	}

	public String getProductO() {
		return productO;
	}

	public float getPriceO() {
		return priceO;
	}
        
        public int getQuantityO() {
		return quantityO;
	}
        
        public String getSalesO() {
		return salesO;
	}
        
        public String getShippingO() {
		return shippingO;
	}  
        
        public String getCompanyO() {
		return companyO;
	}
        
        public int getCustomerO(){
                return customerO;
	}
        
        public void setNumO(int numO) {
		this.numO = numO;
	}

	public void setProductO(String productO) {
		this.productO = productO;
	}

	public void setPriceO(float priceO) {
		this.priceO = priceO;
	}
        
        public void setQuantityO(int quantityO) {
		this.quantityO = quantityO;
	}
        
        public void setSalesO(String salesO) {
		this.salesO = salesO;
	}
        
        public void setShippingO(String shippingO) {
		this.shippingO = shippingO;
	}  
        
        public void setCompanyO(String companyO) {
		this.companyO = companyO;
	}
        
        public void setCustomerO(int customerO) {
		this.customerO = customerO;
	}

}
