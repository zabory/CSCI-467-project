package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRecord {
	
	private String PID;
	private String description;
	private float weight;
	private String pictureLink;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public ProductRecord(ResultSet rs) throws SQLException {
		PID = rs.getString("pid");
		description = rs.getString("description");
		weight = rs.getFloat("weight");
		pictureLink = rs.getString("pictureLink");
	}
	
	/**
	 * @param PID Product ID
	 * @param description Product description
	 * @param weight Product weight
	 * @param pictureLink Product picture link
	 */
	public ProductRecord(String PID, String description, float weight, String pictureLink) {
		this.PID = PID;
		this.description = description;
		this.weight = weight;
		this.pictureLink = pictureLink;
	}
	
	public ProductRecord() {
		
	}

	/**
	 * @return the pID
	 */
	public String getPID() {
		return PID;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return the weight
	 */
	public float getWeight() {
		return weight;
	}
	
	/**
	 * @return the pictureLink
	 */
	public String getPictureLink() {
		return pictureLink;
	}
	
	
}
