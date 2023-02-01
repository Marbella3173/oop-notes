package uap1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;


public class Connect {
	
	private Connection connection;
	private static Connect connect;
	
	private Connect() {
		MysqlDataSource source = new MysqlDataSource();
		source.setServerName("localhost");
		source.setUser("root");
		source.setPassword("");
		source.setDatabaseName("binus_dessert_check");
		try {
			connection = source.getConnection();
			System.out.println("connected");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connect getConnection() {
		if(connect == null) {
			synchronized (Connect.class) {
				if(connect == null) connect = new Connect();
			}
		}
		return connect;
	}
	
	public ResultSet query(String sql) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean execute(String sql) {
		try {
			Statement statement = connection.createStatement();
			return statement.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;		
	}
	
	public PreparedStatement prepare(String sql) {
		try {			
			return connection.prepareStatement(sql);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
