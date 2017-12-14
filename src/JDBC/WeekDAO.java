package JDBC;


import Bean.*;

import java.sql.*;

public class WeekDAO {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public WeekDAO() throws Exception{
		this.conn = ConnectionHelper.getConnection();
		this.stmt = conn.createStatement();
	}
	public Week createHolidayObject(ResultSet rs) throws Exception{
		Week week = new Week();
		week.setMon(8-rs.getInt("2"));
		week.setTues(8-rs.getInt("3"));
		week.setWed(8-rs.getInt("4"));
		week.setThur(8-rs.getInt("5"));
		week.setFri(8-rs.getInt("6"));
		week.setSat(8-rs.getInt("7"));
		week.setSun(8-rs.getInt("1"));
		return week;
	}
	public Week createObject(ResultSet rs) throws Exception{
		Week week = new Week();
		week.setMon(rs.getInt("1"));
		week.setTues(rs.getInt("2"));
		week.setWed(rs.getInt("3"));
		week.setThur(rs.getInt("4"));
		week.setFri(rs.getInt("5"));
		week.setSat(rs.getInt("6"));
		week.setSun(rs.getInt("7"));
		return week;
	}
	public Week getHoli()throws Exception{
		Week week = new Week();
		String sql = "select * from (select to_char(hddate,'D')as a , hdhr from HOLIDAY where HOLIDAY.HDWEEK like (select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual))pivot(sum(hdhr)  for a in (1,2,3,4,5,6,7))" ;

		this.rs = stmt.executeQuery(sql);
		while(rs.next()){
			week = createHolidayObject(rs);
		}
		close();
		return week;
	}
	public Week getHoli(String hsid)throws Exception{
		Week week = new Week();
		String sql = "select * from (select to_char(hddate,'D')as a , hdhr from HOLIDAY where HOLIDAY.HDWEEK like ?)pivot(sum(hdhr)  for a in (1,2,3,4,5,6,7))";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, hsid);
 		this.rs = pstmt.executeQuery();
 		while(rs.next())
 		{
 			week = createHolidayObject(rs); 
 		}
		close();
		return week;
	}
	
	
	
	
	
	public Week getWeekhr(String emid)throws Exception{
		Week week = new Week();
		String sql = "select sum(monhr) \"1\", sum(tueshr) \"2\" , sum(wedhr) \"3\",sum(thurhr) \"4\",sum(frihr) \"5\",sum(sathr) \"6\",sum(sunhr) \"7\" from workhourdetail where hsdeid like (select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual) and emid like ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
 		this.rs = pstmt.executeQuery();
 		while(rs.next())
 		{
 			week = createObject(rs);
 		}
 		close();
		return week;
	}
	
	public Week getWeekhr(String emid, String hsid)throws Exception{
		Week week = new Week();
		String sql = "select sum(monhr) \"1\", sum(tueshr) \"2\" , sum(wedhr) \"3\",sum(thurhr) \"4\",sum(frihr) \"5\",sum(sathr) \"6\",sum(sunhr) \"7\" from workhourdetail where hsdeid like ? and emid like ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,hsid);
		pstmt.setString(2, emid);
 		this.rs = pstmt.executeQuery();
 		while(rs.next())
 		{
 			week = createObject(rs);
 		}
 		close();
		return week;
	}
	
	
	
	
	
	private void close() throws Exception {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
}
