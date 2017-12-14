package JDBC;

import Bean.*;

import java.util.*;
import java.sql.*;



public class WorkHourDAO {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public WorkHourDAO() throws Exception{
		this.conn = ConnectionHelper.getConnection();
		this.stmt = conn.createStatement();
	}
	
	public WorkHour createObject(ResultSet rs) throws Exception{
		WorkHour workhour = new WorkHour();
		workhour.setHsid(rs.getString("hsid"));
		workhour.setEmid(rs.getString("emid"));
		workhour.setRevtm(rs.getDate("revtm"));
		workhour.setStdes(rs.getInt("stdes"));
		workhour.setUrgtimes(rs.getInt("urgtimes"));
		workhour.setUrgtm(rs.getDate("urgtm"));
		return workhour;
	}
	
	public WorkHour findworkhour(String emid ) throws Exception{
		WorkHour workhour = new WorkHour();
		String sql = "select * from WORKHOUR where HSID like (select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual) and emid like ?" ;
		
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
 		this.rs = pstmt.executeQuery();
 		while(rs.next())
 		{
 			workhour = createObject(rs); 
 		}
		close();
		return workhour;
	}
	
	public WorkHour findworkhour(String emid, String hsid ) throws Exception{
		WorkHour workhour = new WorkHour();
		String sql = "select * from WORKHOUR where HSID like ? and emid like ?" ;
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hsid);
        pstmt.setString(2, emid);
 		this.rs = pstmt.executeQuery();
 		while(rs.next())
 		{
 			workhour = createObject(rs); 
 		}
		close();
		return workhour;
	}
	
	
	public void insertworkhour(String emid ) throws Exception{
		
		String sql = "insert into WORKHOUR (hsid, emid) values ((select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual),?)" ;
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
 		pstmt.executeQuery();
		close();
	}
	public void updateState(int stdes, String emid ) throws Exception{
		String sql = "update WORKHOUR set stdes = ? where emid = ? and hsid = (select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual)";
		pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, stdes);
        pstmt.setString(2, emid);
 		pstmt.executeQuery();
		close();
	}
	
	public void updateState(int stdes, String emid, String hsid ) throws Exception{
		String sql = "update WORKHOUR set stdes = ? where emid = ? and hsid = ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, stdes);
        pstmt.setString(2, emid);
        pstmt.setString(3, hsid);
 		pstmt.executeQuery();
		close();
	}
	
	public List<String> findExistYear(String emid) throws Exception{
		List<String> existyear = new ArrayList<>();
		String sql="select distinct SUBSTR(hsid, 1,4 ) existyear from WORKHOUR where emid like ? and stdes = 1";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
		rs = pstmt.executeQuery();
		while(rs.next()){
			existyear.add(rs.getString("existyear"));
		}
		close();
		return existyear;
	}
	
	public List<String> findExistMonth(String year, String emid) throws Exception{
		List<String> existmonth = new ArrayList<>();
		String sql="select distinct  SUBSTR(hsid,5,2) existmonth from WORKHOUR where SUBSTR(hsid, 1,4 ) like ? and emid like ? and stdes = 1";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, year);
        pstmt.setString(2, emid);
        rs = pstmt.executeQuery();
		while(rs.next()){
			
			existmonth.add(rs.getString("existmonth"));
		}
		close();
		return existmonth;
	}
	
	
	private void close() throws Exception {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
}
