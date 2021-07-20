package Database.Records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRecord implements Record {

	private int id;
	private String name;
	private String city;
	private String street;
	private String contact;
	
	/**
	 * @param rs Result set holding the database record
	 * @throws SQLException
	 */
	public CustomerRecord(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		name = rs.getString("name");
		city = rs.getString("city");
		street = rs.getString("street");
		contact = rs.getString("contact");
	}

	/**
	 * @param id
	 * @param name
	 * @param city
	 * @param street
	 * @param contact
	 */
	public CustomerRecord(int id, String name, String city, String street, String contact) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.street = street;
		this.contact = contact;
	}
	
	public String insert() {
		return "INSERT INTO customers VALUES (" + id + ",'" + name + "','" + city + "','" + street + "','" + contact + "')";
	}

	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

}
