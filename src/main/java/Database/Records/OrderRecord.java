package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderRecord implements Record {
	
	
	private String date;
	private int ID;
	private int customerID;
	
	// <part ID, number of part>
	private HashMap<Integer, Integer> parts;
	
	// 0: unauthed, 1: authed, 2: shipped
	private int authorization;

	public OrderRecord(ResultSet rs) throws SQLException {
		ID = rs.getInt("id");
		date = rs.getString("date");
		authorization = rs.getInt("authorization");
		customerID = rs.getInt("customerID");
		
		parts = new HashMap<Integer, Integer>();
		
		try {
			JSONArray jsonParts = new JSONArray(rs.getString("parts"));
			
			for(int i = 0; i < jsonParts.length(); i++) {
				parts.put(jsonParts.getJSONObject(i).getInt("partID"), jsonParts.getJSONObject(i).getInt("amount"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param date
	 * @param iD
	 * @param parts
	 * @param authorization
	 */
	public OrderRecord(String date, int iD, HashMap<Integer, Integer> parts, int authorization, int customerID) {
		this.date = date;
		ID = iD;
		this.parts = parts;
		this.customerID = customerID;
		this.authorization = authorization;
	}



	public String insert() {
		return "INSERT INTO orders VALUES (" + ID + ",'" + partsToJSONString() + "','" + date + "'," + authorization + "," + customerID + ")";
	}

	public String update() {
		return "UPDATE orders SET parts = '" + partsToJSONString() + "', date= '" + date + "', authorization= " + authorization + ", customerID= " + customerID +" where id=" + ID;
	}

	public String delete() {
		return "delete from orders where number=" + ID;
	}

	private String partsToJSONString() {
		JSONArray out = new JSONArray();
		
		for(Integer x : parts.keySet()) {
			JSONObject current = new JSONObject();
			
			try {
				
				current.put("partID", x);
				current.put("amount", parts.get(x));
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			out.put(current);
		}
		
		return out.toString();
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}



	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}



	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}



	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}



	/**
	 * @return the parts
	 */
	public HashMap<Integer, Integer> getParts() {
		return parts;
	}



	/**
	 * @param parts the parts to set
	 */
	public void setParts(HashMap<Integer, Integer> parts) {
		this.parts = parts;
	}



	/**
	 * @return the authorization
	 */
	public int getAuthorization() {
		return authorization;
	}



	/**
	 * @param authorization the authorization to set
	 */
	public void setAuthorization(int authorization) {
		this.authorization = authorization;
	}



	@Override
	public String toString() {
		return "OrderRecord [date=" + date + ", ID=" + ID + ", parts=" + parts + ", authorization=" + authorization
				+ "]";
	}
	
	

}
