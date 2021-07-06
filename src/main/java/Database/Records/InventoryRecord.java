package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryRecord {
	
	private String WHID;
	private String address;
	
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public InventoryRecord(ResultSet rs) throws SQLException {
		WHID = rs.getString("whid");
		address = rs.getString("address");
	}
	
	/**
	 * @param wHID
	 * @param address
	 */
	public InventoryRecord(String WHID, String address) {
		this.WHID = WHID;
		this.address = address;
	}
	/**
	 * @return the wHID
	 */
	public String getWHID() {
		return WHID;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	
}
