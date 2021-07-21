package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartRecord implements Record {
	
	private int number;
	private String description;
	private double price;
	private double weight;
	private String pictureURL;
	private int quantity;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public PartRecord(ResultSet rs) throws SQLException {
		number = rs.getInt("number");
		description = rs.getString("description");
		price = rs.getDouble("price");
		weight = rs.getDouble("weight");
		pictureURL = rs.getString("pictureURL");
		quantity = rs.getInt("quantity");
	}

	/**
	 * @param number
	 * @param description
	 * @param price
	 * @param weight
	 * @param pictureURL
	 * @param quantity
	 */
	public PartRecord(int number, String description, double price, double weight, String pictureURL, int quantity) {
		this.number = number;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.pictureURL = pictureURL;
		this.quantity = quantity;
	}

	/**
	 * Get the SQL statement to insert this record
	 */
	public String insert() {
		return "INSERT INTO parts VALUES (" + number + ",'" + description + "'," + price + "," + weight + ",'" + pictureURL + "', " + quantity +")";
	}

	/**
	 * Get the SQL statement to update this record
	 */
	public String update() {
		return "UPDATE parts SET description = '" + description.replace("'", "''") + "', price= " + price + ", weight= " + weight + ", pictureURL= '" + pictureURL.replace("'", "''") + "', quantity= " + quantity + " where number=" + number;
	}
	
	/**
	 * Get the SQL statement to delete this record
	 */
	public String delete() {
		return "delete from parts where number=" + number;
	}
	
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param d the weight to set
	 */
	public void setWeight(double d) {
		this.weight = d;
	}

	/**
	 * @return the pictureURL
	 */
	public String getPictureURL() {
		return pictureURL;
	}

	/**
	 * @param pictureURL the pictureURL to set
	 */
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	@Override
	public String toString() {
		return "PartRecord [number=" + number + ", description=" + description + ", price=" + price + ", weight="
				+ weight + ", pictureURL=" + pictureURL + "]";
	}	
}
