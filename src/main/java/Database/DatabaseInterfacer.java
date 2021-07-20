package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Database.Records.Record;


public class DatabaseInterfacer {
	
	public static final String TABLE_NAME = "";
	
	private Connection DBConnection;
	
	public DatabaseInterfacer () throws SQLException {
		DBConnection = DriverManager.getConnection("Database.db");
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
    
    /**
     * 
     * @throws SQLException
     */
    public void saveClose() throws SQLException {
    	DBConnection.commit();
    	DBConnection.close();
    }
}
