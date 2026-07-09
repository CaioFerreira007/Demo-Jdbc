package db;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;;
public class DB {

	private static Connection connection = null;
	
	public static Connection getConnection() {
			try {
		if(connection == null) {
			Properties  props = loadProperties();
			String url = props.getProperty("dburl");
		connection = DriverManager.getConnection(url,props);
		System.out.println("Conexão realizada!");
		}
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		return connection;
		
	}
	
	private static Properties loadProperties() {
		
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
			connection.close();
			}
			catch(SQLException e) {
			throw new DbException(e.getMessage());	
			}
			}
	}
	
	public static void closeResultSet(ResultSet rs) {
		try {
			if(rs!=null) {
				rs.close();
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
	

	public static void closeStatement(Statement st) {
	try {
		if(st != null) {
			st.close();
		}
	}catch(SQLException e) {
		throw new DbException(e.getMessage());
	}
	
}
}