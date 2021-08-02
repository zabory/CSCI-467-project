package Database.Records;

import org.json.JSONObject;

public interface Record {

	/**
	 * 
	 * @return SQL command to insert record into table
	 */
	public String insert();
	
	/**
	 * 
	 * @return SQL command to update record in the table
	 */
	public String update();
	
	/**
	 * 
	 * @return SQL command to delete the record from the table
	 */
	public String delete();
	
	/**
	 * 
	 * @return Record in JSONObject
	 */
	public JSONObject toJSONObject();
	
	/**
	 * 
	 * @return Updates record from JSONObject
	 */
	public void updateFromJSONObjcet(JSONObject update);
}
