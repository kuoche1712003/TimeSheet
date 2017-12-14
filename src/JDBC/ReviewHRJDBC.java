package JDBC;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import Bean.SearAReviewVO;
import Bean.SearOReviewVO;
import JDBC.JDBCData;

public class ReviewHRJDBC {
	private static final String GET_ALL_REVIEW_STMT = "SELECT t.*, 56-NVL(SUM(h.hdhr),0) mweekhr FROM   (SELECT w.hsid, e.emid, e.name, SUM(NVL(wd.monhr,0)) + SUM(NVL(wd.tueshr,0)) +   SUM(NVL(wd.wedhr,0)) + SUM(NVL(wd.thurhr,0)) + SUM(NVL(wd.frihr,0)) +  SUM(NVL(wd.sathr,0)) + SUM(NVL(wd.sunhr,0)) rweekhr , w.stdes, w.revtm FROM workhour w JOIN workhourdetail wd ON w.hsid = wd.hsdeid AND w.emid = wd.emid JOIN employee e ON w.emid = e.emid GROUP BY w.hsid, e.emid ,e.name, w.stdes, w.revtm) t LEFT JOIN holiday h ON t.hsid = h.hdweek WHERE t.stdes = 2 GROUP BY t.hsid, t.emid ,t.name, t.stdes, t.revtm, t.rweekhr ORDER BY 1,2";
	private static final String GET_OofA_REVIEW_STMT = "SELECT t.*, 56-NVL(SUM(h.hdhr),0) mweekhr FROM   (SELECT w.hsid, e.emid, e.name, SUM(NVL(wd.monhr,0)) + SUM(NVL(wd.tueshr,0)) +   SUM(NVL(wd.wedhr,0)) + SUM(NVL(wd.thurhr,0)) + SUM(NVL(wd.frihr,0)) +  SUM(NVL(wd.sathr,0)) + SUM(NVL(wd.sunhr,0)) rweekhr , w.stdes, w.revtm FROM workhour w JOIN workhourdetail wd ON w.hsid = wd.hsdeid AND w.emid = wd.emid JOIN employee e ON w.emid = e.emid GROUP BY w.hsid, e.emid ,e.name, w.stdes, w.revtm) t LEFT JOIN holiday h ON t.hsid = h.hdweek WHERE t.hsid = ? AND t.emid = ? AND (t.stdes = 2 OR t.stdes = 3) GROUP BY t.hsid, t.emid ,t.name, t.stdes, t.revtm, t.rweekhr";
	private static final String GET_ONE_REVIEW_STMT = "SELECT wd.hsdeid, wd.emid, e.name, wd.hscontent, NVL(wd.monhr,0) monhr, NVL(wd.tueshr,0) tueshr, NVL(wd.wedhr,0) wedhr, NVL(wd.thurhr,0) thurhr, NVL(wd.frihr,0) frihr, NVL(wd.sathr,0) sathr, NVL(wd.sunhr,0) sunhr, NVL(wd.monoverhr,0) monoverhr, NVL(wd.tuesoverhr,0) tuesoverhr, NVL(wd.wedoverhr,0) wedoverhr, NVL(wd.thuroverhr,0) thuroverhr, NVL(wd.frioverhr,0) frioverhr, NVL(wd.satoverhr,0) satoverhr, NVL(wd.sunoverhr,0) sunoverhr FROM workhourdetail wd JOIN employee e	ON e.emid = wd.emid	WHERE wd.hsdeid = ? AND wd.emid = ?";
	private static final String REVIEW_STATUS_UPDATE = "UPDATE workhour SET stdes = ?,revtm = (SELECT SYSDATE FROM dual) WHERE hsid = ? AND emid = ?";	
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
	
	public List<SearAReviewVO> findAllReview(){
		List<SearAReviewVO> list = new ArrayList<>();
		SearAReviewVO rvhrVO = null;
    	
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ALL_REVIEW_STMT);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			rvhrVO = new SearAReviewVO();
    			rvhrVO.setHsid(rs.getString("hsid"));
    			rvhrVO.setEmid(rs.getString("emid"));
    			rvhrVO.setEname(rs.getString("name"));
    			rvhrVO.setMweekhr(rs.getInt("mweekhr"));
    			rvhrVO.setRweekhr(rs.getInt("rweekhr"));
    			rvhrVO.setUrgestatus(rs.getInt("stdes"));
    			rvhrVO.setReviewtime(rs.getDate("revtm"));
    			list.add(rvhrVO);
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
	
	public SearAReviewVO findOofAReview(String hsid,String emid){
		SearAReviewVO rvhrVO = null;
    	
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_OofA_REVIEW_STMT);
    		pstmt.setString(1, hsid);
    		pstmt.setString(2, emid);
    		
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			rvhrVO = new SearAReviewVO();
    			rvhrVO.setHsid(rs.getString("hsid"));
    			rvhrVO.setEmid(rs.getString("emid"));
    			rvhrVO.setEname(rs.getString("name"));
    			rvhrVO.setMweekhr(rs.getInt("mweekhr"));
    			rvhrVO.setRweekhr(rs.getInt("rweekhr"));
    			rvhrVO.setUrgestatus(rs.getInt("stdes"));
    			rvhrVO.setReviewtime(rs.getDate("revtm"));
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
    	return rvhrVO;
	}
	
	public List<SearOReviewVO> findOneReview(String hsid, String emid){
		List<SearOReviewVO> list = new ArrayList<>();
		SearOReviewVO rvhrVO = null;
    	
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ONE_REVIEW_STMT);
    		pstmt.setString(1, hsid);
    		pstmt.setString(2, emid);
    		
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			rvhrVO = new SearOReviewVO();
    			rvhrVO.setHsid(rs.getString("hsdeid"));
    			rvhrVO.setEmid(rs.getString("emid"));
    			rvhrVO.setEname(rs.getString("name"));
    			rvhrVO.setHscontent(rs.getString("hscontent"));
    			rvhrVO.setMonhr(rs.getInt("monhr"));
    			rvhrVO.setTueshr(rs.getInt("tueshr"));
    			rvhrVO.setWedhr(rs.getInt("wedhr"));
    			rvhrVO.setThurhr(rs.getInt("thurhr"));
    			rvhrVO.setFrihr(rs.getInt("frihr"));
    			rvhrVO.setSathr(rs.getInt("sathr"));
    			rvhrVO.setSunhr(rs.getInt("sunhr"));
    			rvhrVO.setMonoverhr(rs.getInt("monoverhr"));
    			rvhrVO.setTuesoverhr(rs.getInt("tuesoverhr"));
    			rvhrVO.setWedoverhr(rs.getInt("wedoverhr"));
    			rvhrVO.setThuroverhr(rs.getInt("thuroverhr"));
    			rvhrVO.setFrioverhr(rs.getInt("frioverhr"));
    			rvhrVO.setSatoverhr(rs.getInt("satoverhr"));
    			rvhrVO.setSunoverhr(rs.getInt("sunoverhr"));
    			list.add(rvhrVO);
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
	
	public void updateReview(Integer status, String hsid, String emid){
		Connection con = null;
    	PreparedStatement pstmt = null;
    	
    	try{
    		con = DriverManager.getConnection(url,username,passwd);
    		pstmt = con.prepareStatement(REVIEW_STATUS_UPDATE);
    
    		pstmt.setInt(1, status);
    		pstmt.setString(2, hsid);
    		pstmt.setString(3, emid);
    		pstmt.executeUpdate();
    	}catch (SQLException se) {
            throw new RuntimeException("A database error occured. " + se.getMessage());
        }finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException se) {
                    se.printStackTrace(System.err);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
		
	}
}
