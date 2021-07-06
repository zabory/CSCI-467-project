package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRecord {
	
	private String AID;
	private String password;
	private String name;
	private String title;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public AdminRecord(ResultSet rs) throws SQLException {
		AID = rs.getString("aid");
		password = rs.getString("password");
		name = rs.getString("name");
		title = rs.getString("title");
	}
	
	/**
	 * @param AID Admin ID
	 * @param password Admin password
	 * @param name Admin name
	 * @param title Admin title
	 */
	public AdminRecord(String AID, String password, String name, String title) {
		this.AID = AID;
		this.password = password;
		this.name = name;
		this.title = title;
	}

	/**
	 * @return the aID
	 */
	public String getAID() {
		return AID;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	

}
