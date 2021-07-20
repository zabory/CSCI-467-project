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
    
    public void update(Record record) {
    	//TODO implement method
    }
    
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
    /**
     * 
     * @throws SQLException
     */
    public void saveClose() throws SQLException {
    	DBConnection.commit();
    	DBConnection.close();
    }
}
