package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util. *;

import Bean.*;

public class WorkHourDetailDAO {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public WorkHourDetailDAO() throws Exception{
		this.conn = ConnectionHelper.getConnection();
		this.stmt = conn.createStatement();
	}
	public WorkHourDetail createObject(ResultSet rs) throws Exception{
		WorkHourDetail workhourdetail = new WorkHourDetail();
		workhourdetail.setHsdeid(rs.getString("hsdeid"));
		workhourdetail.setEmid(rs.getString("emid"));
		workhourdetail.setHscontent(rs.getString("hscontent"));
		workhourdetail.setMonhr(rs.getInt("monhr"));
		workhourdetail.setThurhr(rs.getInt("thurhr"));
		workhourdetail.setWedhr(rs.getInt("wedhr"));
		workhourdetail.setTueshr(rs.getInt("tueshr"));
		workhourdetail.setFrihr(rs.getInt("frihr"));
		workhourdetail.setSathr(rs.getInt("sathr"));
		workhourdetail.setSunhr(rs.getInt("sunhr"));
		workhourdetail.setMonoverhr(rs.getInt("monoverhr"));
		workhourdetail.setThuroverhr(rs.getInt("thuroverhr"));
		workhourdetail.setWedoverhr(rs.getInt("wedoverhr"));
		workhourdetail.setTuesoverhr(rs.getInt("tuesoverhr"));
		workhourdetail.setFrioverhr(rs.getInt("frioverhr"));
		workhourdetail.setSatoverhr(rs.getInt("satoverhr"));
		workhourdetail.setSunoverhr(rs.getInt("sunoverhr"));
		return workhourdetail;
	}
	public void insertWorkhourdetail(WorkHourDetail whd) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into workhourdetail(hsdeid, emid, hscontent, monhr, thurhr, wedhr, tueshr, frihr, sathr,sunhr, ");
		sql.append("monoverhr, thuroverhr, wedoverhr, tuesoverhr, frioverhr, satoverhr, sunoverhr) ");
		sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1,whd.getHsdeid());
		pstmt.setString(2,whd.getEmid());
		pstmt.setString(3,whd.getHscontent());
		pstmt.setInt(4,whd.getMonhr());
		pstmt.setInt(5,whd.getThurhr());
		pstmt.setInt(6,whd.getWedhr());
		pstmt.setInt(7,whd.getTueshr());
		pstmt.setInt(8,whd.getFrihr());
		pstmt.setInt(9,whd.getSathr());
		pstmt.setInt(10,whd.getSunhr());
		pstmt.setInt(11,whd.getMonoverhr());
		pstmt.setInt(12,whd.getThuroverhr());
		pstmt.setInt(13,whd.getWedoverhr());
		pstmt.setInt(14,whd.getTuesoverhr());
		pstmt.setInt(15,whd.getFrioverhr());
		pstmt.setInt(16,whd.getSatoverhr());
		pstmt.setInt(17,whd.getSunoverhr());
		pstmt.executeUpdate();
		close();
	}
	public List<WorkHourDetail> findbyemidhsid(String hsdeid, String emid) throws Exception{
		List<WorkHourDetail> workhourdetail = new ArrayList<WorkHourDetail>();
		String sql = "select * from workhourdetail where hsdeid like ? and emid like ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hsdeid);
        pstmt.setString(2, emid);
        this.rs = pstmt.executeQuery();
        while(rs.next()){
			workhourdetail.add(createObject(rs)); 
		}
        close();
		return workhourdetail;
	}
	
	public void delete(String emid, String hscontent) throws Exception{
		String sql = "delete from WORKHOURDETAIL where hsdeid like (select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual) and emid like ? and hscontent like ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, emid);
        pstmt.setString(2, hscontent);
        pstmt.executeUpdate();
        close();
        
	}
	
	public void delete(String emid, String hscontent, String hsid) throws Exception{
		String sql = "delete from WORKHOURDETAIL where hsdeid like ? and emid like ? and hscontent like ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, hsid);
		pstmt.setString(2, emid);
        pstmt.setString(3, hscontent);
        
        pstmt.executeUpdate();
        close();
        
	}
	
	
	public void update(WorkHourDetail whd) throws Exception{
		String sql = "update workhourdetail set hscontent = ?, monhr = ?, tueshr = ?, wedhr = ?, thurhr = ?, frihr = ?, sathr = ?, sunhr = ?, monoverhr = ?, tuesoverhr = ? , wedoverhr = ?, thuroverhr = ?, frioverhr = ?, satoverhr = ?, sunoverhr = ? where hsdeid like ? and emid like ? and hscontent like ?";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,whd.getHscontent());
		pstmt.setInt(2,whd.getMonhr());
		pstmt.setInt(3,whd.getTueshr());
		pstmt.setInt(4,whd.getWedhr());
		pstmt.setInt(5,whd.getThurhr());
		pstmt.setInt(6,whd.getFrihr());
		pstmt.setInt(7,whd.getSathr());
		pstmt.setInt(8,whd.getSunhr());
		pstmt.setInt(9,whd.getMonoverhr());
		pstmt.setInt(10,whd.getTuesoverhr());
		pstmt.setInt(11,whd.getWedoverhr());
		pstmt.setInt(12,whd.getThuroverhr());
		pstmt.setInt(13,whd.getFrioverhr());
		pstmt.setInt(14,whd.getSatoverhr());
		pstmt.setInt(15,whd.getSunoverhr());
		pstmt.setString(16,whd.getHsdeid());
		pstmt.setString(17,whd.getEmid());
		pstmt.setString(18,whd.getOrihscontent());
		pstmt.executeUpdate();
		close();
	}
	
	private void close() throws Exception {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
}
