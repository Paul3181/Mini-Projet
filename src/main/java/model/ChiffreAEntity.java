package model;

/**
 * Un enregistrement de la table PRODUCT
 * @author 
 */
public class ChiffreAEntity {

	private String categorie;
	private float chiffre;

	public ChiffreAEntity(String categorie, float chiffre) {
		this.categorie = categorie;
		this.chiffre = chiffre;
	}


	public String getProduct() {
		return categorie;
	}

	public float getPrice() {
		return chiffre;
	}

}