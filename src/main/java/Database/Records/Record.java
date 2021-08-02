package Database.Records;

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
}
