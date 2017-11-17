package model;

/**
 * Un enregistrement de la table PRODUCT
 * @author 
 */
public class ProductEntity {

	private String product;

	private float price;

	public ProductEntity(String product, float price) {
		this.product = product;
		this.price = price;
	}


	public String getProduct() {
		return product;
	}

	public float getPrice() {
		return price;
	}

}
