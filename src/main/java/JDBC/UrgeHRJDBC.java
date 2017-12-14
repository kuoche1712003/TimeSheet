package JDBC;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;





import Bean.SearAUrgeVO;
import JDBC.JDBCData;

public class UrgeHRJDBC {
	private static final String GET_ALL_URGE_STMT = "SELECT t.*, 56-NVL(SUM(h.hdhr),0) mweekhr FROM   (SELECT w.hsid, e.emid, e.name, SUM(NVL(wd.monhr,0)) + SUM(NVL(wd.tueshr,0)) +  SUM(NVL(wd.wedhr,0)) + SUM(NVL(wd.thurhr,0)) + SUM(NVL(wd.frihr,0)) +  SUM(NVL(wd.sathr,0)) + SUM(NVL(wd.sunhr,0)) rweekhr , NVL(w.stdes,0) stdes, w.revtm, w.urgtimes, w.urgtm FROM workhour w JOIN workhourdetail wd ON w.hsid = wd.hsdeid  AND w.emid = wd.emid JOIN employee e ON w.emid = e.emid GROUP BY w.hsid, e.emid ,e.name, stdes, w.revtm, w.urgtimes, w.urgtm) t LEFT JOIN holiday h ON t.hsid = h.hdweek WHERE (t.stdes != 2 AND t.stdes != 1) GROUP BY t.hsid, t.emid ,t.name, t.stdes, t.revtm, t.urgtimes, t.urgtm, t.rweekhr ORDER BY t.hsid,t.emid";
	private static final String GET_ALL_EMID = "SELECT emid FROM employee where autid=2";
	private static final String HAVE_HEADER ="SELECT hsid FROM workhour WHERE emid = ? AND hsid = (SELECT TO_CHAR(TRUNC(SYSDATE, 'IW')-7,'yyyymmiw') FROM dual)";
	private static final String INSERT_HEADER ="INSERT INTO workhour (hsid, emid) VALUES ((SELECT TO_CHAR(TRUNC(SYSDATE, 'IW')-7,'yyyymmiw') FROM dual), ?)";
	private static final String INSERT_DETAIL ="INSERT INTO workhourdetail (hsdeid, emid, hscontent) VALUES ((SELECT TO_CHAR(TRUNC(SYSDATE, 'IW')-7,'yyyymmiw') FROM dual), ?, '還不快寫')";
	private static final String UPDATE_URGE_DATA ="UPDATE workhour SET urgtimes = ?, urgtm = (SELECT SYSDATE FROM dual) WHERE hsid = ? AND emid = ?";
	
	private static final String driver = JDBCData.DRIVER;
	private static final String url = JDBCData.URI;
	private static final String username = JDBCData.USERNAME;
	private static final String passwd = JDBCData.PASSWORD;
	
	static{
		try{
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		}
	}
	
	public void doUrge(Integer urgecnt, String hsid, String emid){
    	Connection conn = null;
    	PreparedStatement pstmt = null;
		
		try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(UPDATE_URGE_DATA);
    		pstmt.setInt(1, urgecnt);
    		pstmt.setString(2, hsid);
    		pstmt.setString(3, emid);
    		pstmt.executeUpdate();
	
    	}catch (SQLException se){
    		throw new RuntimeException("A database error occured. " + se.getMessage());
    	}finally{
    		if(pstmt != null){
    			try{
    				pstmt.close();
    			}catch(SQLException se){
    				se.printStackTrace(System.err);
    			}
    		}
    		if(conn != null){
    			try{
    				conn.close();
    			}catch(Exception e){
    				e.printStackTrace(System.err);
    			}
    		}
    	}
	}
	
	
	public List<String> getEmidList(){
		List<String> emidList = new ArrayList<>();
		String emid = null;
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ALL_EMID);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			emid = rs.getString("emid");
    			emidList.add(emid);
    		}
    	}catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    	return emidList;
    }
	
	public void creatHeader(String emid){
		
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	int i = 0; 
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(HAVE_HEADER);
    		pstmt.setString(1, emid);
    		
    		rs = pstmt.executeQuery();

    		while(rs.next()){
    			i++;
    		}
    	}catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    	
    	
    	if(i==0){
	    	try{
	    		conn = DriverManager.getConnection(url,username,passwd);
	    		pstmt = conn.prepareStatement(INSERT_HEADER);
	    		pstmt.setString(1, emid);
	    		pstmt.executeUpdate();
	    		
	    		pstmt = conn.prepareStatement(INSERT_DETAIL);
	    		pstmt.setString(1, emid);
	    		pstmt.executeUpdate();
	    		
	    	}catch (SQLException se){
	    		throw new RuntimeException("A database error occured. " + se.getMessage());
	    	}finally{
	    		if(pstmt != null){
	    			try{
	    				pstmt.close();
	    			}catch(SQLException se){
	    				se.printStackTrace(System.err);
	    			}
	    		}
	    		if(conn != null){
	    			try{
	    				conn.close();
	    			}catch(Exception e){
	    				e.printStackTrace(System.err);
	    			}
	    		}
	    	}
    	}
    	
    	
	}
	
	public List<SearAUrgeVO> findAllUrge(){
		List<SearAUrgeVO> list = new ArrayList<>();
		SearAUrgeVO ughrVO = null;
    	
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ALL_URGE_STMT);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			ughrVO = new SearAUrgeVO();
    			ughrVO.setHsid(rs.getString("hsid"));
    			ughrVO.setEmid(rs.getString("emid"));
    			ughrVO.setEname(rs.getString("name"));
    			ughrVO.setMweekhr(rs.getInt("mweekhr"));
    			ughrVO.setRweekhr(rs.getInt("rweekhr"));
    			ughrVO.setUrgestatus(rs.getInt("stdes"));
    			ughrVO.setReviewtime(rs.getDate("revtm"));
    			ughrVO.setUrgecnt(rs.getInt("urgtimes"));
    			ughrVO.setUrgetime(rs.getDate("urgtm"));
    			list.add(ughrVO);
    		}
    	}catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    	return list;
	}
}
