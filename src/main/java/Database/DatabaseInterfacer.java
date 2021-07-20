package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import Database.Records.CustomerRecord;
import Database.Records.PartRecord;
import Database.Records.Record;


public class DatabaseInterfacer {
	
	private Connection DBConnection;
	
	public DatabaseInterfacer () throws SQLException {
		DBConnection = DriverManager.getConnection("jdbc:sqlite:Database.db");
	}
	
	/**
     * execute a query that returns data
     * @param sql sql statement to execute
     * @return the results of query
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        return DBConnection.createStatement().executeQuery(sql);
    }
    
    /**
     * Use this method to create a record on the table
     * @param record to be inserted
     */
    public void insert(Record record) {
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
    public void update(Record record) {
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
    public CustomerRecord getCustomerRecord(int id) {
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
     * get all the customer records
     * @return Linked list of customer records
     */
    public LinkedList<CustomerRecord> getAllCustomerRecords(){
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
    public PartRecord getPartRecord(int id) {
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
     * get all the part records
     * @return Linked list of part records
     */
    public LinkedList<PartRecord> getAllPartRecords(){
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
