package Database.Records;

public class AdminRecord {
	
	private String AID;
	private String password;
	private String name;
	private String title;
	
	
	
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
