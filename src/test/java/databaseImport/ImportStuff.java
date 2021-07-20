package databaseImport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImportStuff {

	public static void main(String[] args) throws SQLException, FileNotFoundException, ParseException, JSONException {
		
		Connection DBConnection = DriverManager.getConnection("jdbc:sqlite:Database.db");
		
		Scanner filein = new Scanner(new File("C:\\Users\\bensh\\Desktop\\customers.txt"));
		
		String customersString = filein.nextLine();
		
		filein.close();
		
		filein = new Scanner(new File("C:\\Users\\bensh\\Desktop\\parts.txt"));
		
		String partsString = filein.nextLine();
		
		filein.close();
		
		JSONArray customerData = new JSONArray(customersString);
		JSONArray partsData = new JSONArray(partsString);
		
		ResultSet tables = DBConnection.getMetaData().getTables(null, null, "%", new String[] {"TABLE"});
		
		while(tables.next()) {
			System.out.println(tables.getString("TABLE_NAME"));
		}
		
		for(int i = 0; i < customerData.length(); i ++) {
			JSONObject customer = (JSONObject) customerData.get(i);
			int id = customer.getInt("id");
			String name = customer.getString("name").replace("'", "''");
			String city = customer.getString("city").replace("'", "''");;
			String street = customer.getString("street").replace("'", "''");;
			String contact = customer.getString("contact").replace("'", "''");;
			
			
			String sql = "INSERT INTO customers VALUES (" + id + ",'" + name + "','" + city + "','" + street + "','" + contact + "')";
			
			Statement statement = DBConnection.createStatement();
			statement.executeUpdate(sql);
		}
		
		for(int i = 0; i < partsData.length(); i ++) {
			JSONObject part = (JSONObject) partsData.get(i);
			int number = part.getInt("number");
			String description = part.getString("description").replace("'", "''");;
			long price = part.getLong("price");
			long weight = part.getLong("weight");
			String pictureURL = part.getString("pictureURL").replace("'", "''");;
			
			String sql = "INSERT INTO parts VALUES (" + number + ",'" + description + "'," + price + "," + weight + ",'" + pictureURL + "')";
			
			Statement statement = DBConnection.createStatement();
			statement.executeUpdate(sql);
		}
		
		DBConnection.close();
		
	}

}
