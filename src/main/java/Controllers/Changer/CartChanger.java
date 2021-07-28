package Controllers.Changer;

import org.json.JSONObject;

public class CartChanger {

	private String newAmount;
	private String productId;
	private JSONObject currentCart;
	/**
	 * @return the newAmount
	 */
	public String getNewAmount() {
		return newAmount;
	}
	/**
	 * @param newAmount the newAmount to set
	 */
	public void setNewAmount(String newAmount) {
		this.newAmount = newAmount;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the currentCart
	 */
	public JSONObject getCurrentCart() {
		return currentCart;
	}
	/**
	 * @param currentCart the currentCart to set
	 */
	public void setCurrentCart(JSONObject currentCart) {
		this.currentCart = currentCart;
	}
	
	
}
