package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipmentRecord {
	
	private String SID;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public ShipmentRecord(ResultSet rs) throws SQLException {
		SID = rs.getString("SID");
	}

	/**
	 * @param sID
	 */
	public ShipmentRecord(String SID) {
		this.SID = SID;
	}

	/**
	 * @return the sID
	 */
	public String getSID() {
		return SID;
	}
	
	

}
