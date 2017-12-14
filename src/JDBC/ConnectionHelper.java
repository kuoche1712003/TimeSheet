package JDBC;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
public class ConnectionHelper {
	public static Connection getConnection() throws Exception  {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/OracleXE");
		Connection conn = ds.getConnection();
		return conn;
	}
}
