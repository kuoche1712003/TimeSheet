package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection 
{
	
	private static final String Driver = JDBCData.DRIVER;
	private static final String url = JDBCData.URI;
	private static final String userid = JDBCData.USERNAME;
	private static final String passwd = JDBCData.PASSWORD;
	
	// implements JDBCData to connect iisi database.
	
	
	public  Connection connections() throws Exception{
		Connection conn;
		
		try{
			
			Class.forName(Driver);
			conn=DriverManager.getConnection(url,userid,passwd);
			return conn;
		}
		catch(SQLException se){
			throw new RuntimeException("資料庫錯誤:"+ se.getMessage(),se);
		}
	
}
}