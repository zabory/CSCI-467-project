package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRecord {

	private String UID;
	private String password;
	private String email;
	private String address;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public UserRecord(ResultSet rs) throws SQLException {
		UID = rs.getString("UID");
		password = rs.getString("password");
		email = rs.getString("email");
		address = rs.getString("address");
	}

	/**
	 * @param uID User ID
	 * @param password User password
	 * @param email User email
	 * @param address User address
	 */
	public UserRecord(String UID, String password, String email, String address) {
		this.UID = UID;
		this.password = password;
		this.email = email;
		this.address = address;
	}

	/**
	 * @return the uID
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	

}
