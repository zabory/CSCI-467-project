package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CartRecord {

	private String CID;
	private HashMap<ProductRecord, Integer> products;
	
	public CartRecord(ResultSet rs) throws SQLException {
		
	}
	
}
