package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import Database.Records.CustomerRecord;
import Database.Records.OrderRecord;
import Database.Records.PartRecord;
import Database.Records.Record;


public class DatabaseInterfacer {
	
	private Connection DBConnection;
	
	public DatabaseInterfacer () {
		try {
			DBConnection = DriverManager.getConnection("jdbc:sqlite:Database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Deletes record from database
     * @param record to delete from database
     */
    public synchronized void delete(Record record) {
    	String sql = record.delete();
		Statement statement;
		try {
			statement = DBConnection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Use this method to create a record on the table
     * @param record to be inserted
     */
    public synchronized void insert(Record record) {
    	String sql = record.insert();
		Statement statement;
		try {
			statement = DBConnection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Use this method to update a record on the table
     * @param record to be updated
     */
    public synchronized void update(Record record) {
    	String sql = record.update();
		Statement statement;
		try {
			statement = DBConnection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Get a customer record by ID
     * @param id of the customer record
     * @return customer record
     */
    public synchronized CustomerRecord getCustomerRecord(int id) {
    	String sql = "select * from customers where id=" + id;
    	try {
			Statement statement = DBConnection.createStatement();
			return new CustomerRecord(statement.executeQuery(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Get a order record by ID
     * @param id of the order record
     * @return order record
     */
    public synchronized OrderRecord getOrderRecord(int id) {
    	String sql = "select * from orders where id=" + id;
    	try {
			Statement statement = DBConnection.createStatement();
			return new OrderRecord(statement.executeQuery(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * get all the customer records
     * @return Linked list of customer records
     */
    public synchronized LinkedList<OrderRecord> getAllOrderRecords(){
    	String sql = "select * from orders";
    	try {
			Statement statement = DBConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			LinkedList<OrderRecord> records = new LinkedList<OrderRecord>();
			while(rs.next()) {
				records.add(new OrderRecord(rs));
			}
			return records;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * get all the customer records
     * @return Linked list of customer records
     */
    public synchronized LinkedList<CustomerRecord> getAllCustomerRecords(){
    	String sql = "select * from customers";
    	try {
			Statement statement = DBConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			LinkedList<CustomerRecord> records = new LinkedList<CustomerRecord>();
			while(rs.next()) {
				records.add(new CustomerRecord(rs));
			}
			return records;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Get a part record by ID
     * @param id of the part record
     * @return part record
     */
    public synchronized PartRecord getPartRecord(int id) {
    	String sql = "select * from parts where number=" + id;
    	try {
			Statement statement = DBConnection.createStatement();
			return new PartRecord(statement.executeQuery(sql));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Returns first record to match a customer name
     * @param name Name of customer
     * @return Customer record
     */
    public synchronized CustomerRecord getCustomer(String name) {
    	String sql = "select * from customers where name='" + name.replace("'", "''") + "'";
    	
    	try {
			Statement statement = DBConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if(!rs.isClosed()) {
				return new CustomerRecord(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * get all the part records
     * @return Linked list of part records
     */
    public synchronized LinkedList<PartRecord> getAllPartRecords(){
    	String sql = "select * from parts";
    	try {
			Statement statement = DBConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			LinkedList<PartRecord> records = new LinkedList<PartRecord>();
			while(rs.next()) {
				records.add(new PartRecord(rs));
			}
			return records;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public void close() throws SQLException {
    	DBConnection.close();
    }
}
