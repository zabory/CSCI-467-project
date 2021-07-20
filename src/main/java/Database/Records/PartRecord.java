package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartRecord implements Record {
	
	private int number;
	private String description;
	private long price;
	private long weight;
	private String pictureURL;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public PartRecord(ResultSet rs) throws SQLException {
		number = rs.getInt("number");
		description = rs.getString("description");
		price = rs.getLong("price");
		weight = rs.getLong("weight");
		pictureURL = rs.getString("pictureLink");
	}

	/**
	 * @param number
	 * @param description
	 * @param price
	 * @param weight
	 * @param pictureURL
	 */
	public PartRecord(int number, String description, long price, long weight, String pictureURL) {
		this.number = number;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.pictureURL = pictureURL;
	}
	
	public String insert() {
		return "INSERT INTO parts VALUES (" + number + ",'" + description + "'," + price + "," + weight + ",'" + pictureURL + "')";
	}

	public String update() {
		// TODO make update record method work
		return null;
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
	public long getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(long price) {
		this.price = price;
	}

	/**
	 * @return the weight
	 */
	public long getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(long weight) {
		this.weight = weight;
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
	

	
	
}
