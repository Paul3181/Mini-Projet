package model;

/**
 * Un enregistrement de la table PRODUCT
 * @author 
 */
public class ProductEntity {

	private String product;

	private float price;
        
        private int qte;
        
        private float rate;

	public ProductEntity(String product, float price, int qte, float rate) {
		this.product = product;
		this.price = price;
                this.qte = qte;
                this.rate = rate;
	}


	public String getProduct() {
		return product;
	}

	public float getPrice() {
		return price;
	}

}
