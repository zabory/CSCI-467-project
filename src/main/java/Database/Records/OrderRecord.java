package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Database.DatabaseInterfacer;
import application.App;

public class OrderRecord implements Record {
	
	private String date;
	private int ID;
	private int customerID;
	
	private DatabaseInterfacer DBInterfacer;
	
	// <part ID, number of part>
	private HashMap<Integer, Integer> parts;



	// 0: unauthed, 1: authed, 2: shipped
	private int authorization;

	public OrderRecord(ResultSet rs) throws SQLException {
		ID = rs.getInt("id");
		date = rs.getString("date");
		authorization = rs.getInt("authorization");
		customerID = rs.getInt("customerID");
		
		DBInterfacer = App.getDatabaseInterfacer();
		
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
	 * @param customerID
	 */
	public OrderRecord(String date, int iD, HashMap<Integer, Integer> parts, int authorization, int customerID) {
		DBInterfacer = App.getDatabaseInterfacer();
		this.date = date;
		ID = iD;
		this.parts = parts;
		this.customerID = customerID;
		this.authorization = authorization;
	}



	public OrderRecord() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	public String insert() {
		return "INSERT INTO orders VALUES (" + ID + ",'" + partsToJSONString() + "','" + date + "'," + authorization + "," + customerID + ")";
	}

	public String update() {
		return "UPDATE orders SET parts = '" + partsToJSONString() + "', date= '" + date + "', authorization= " + authorization + ", customerID= " + customerID +" where id=" + ID;
	}

	public String delete() {
		return "delete from orders where id=" + ID;
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

	public String getPartDisplay()
	{
		String str = "";
		
		for(Integer key: parts.keySet())
		{
			str += DBInterfacer.getPartRecord(key).getDescription();
			str += " = count: ";
			str += parts.get(key) + "\n";
		}
		return str;
	}

	public String getOrderWeight()
	{
		double tWeight = 0;
		
		for(Integer key: parts.keySet())
		{
			tWeight += DBInterfacer.getPartRecord(key).getWeight() * parts.get(key);
		}
		
		return tWeight + "";	
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

	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	@Override
	public String toString() {
		return "OrderRecord [date=" + date + ", ID=" + ID + ", parts=" + parts + ", authorization=" + authorization
				+ "]";
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject record = new JSONObject();
		
		try {
			record.put("id", ID);
			record.put("date", date);
			record.put("parts", partsToJSONString());
			record.put("authorization", authorization);
		} catch (JSONException e) {
			
		}
		
		return record;
	}

	@Override
	public void updateFromJSONObjcet(JSONObject update) {
		
	}
}
