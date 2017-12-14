package JDBC;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import Bean.SearAHRVO;
import Bean.SearOHRVO;
import JDBC.JDBCData;

public class SearAHRJDBC{

	private static final String GET_ALL_STMT = "SELECT t.*, 56-NVL(SUM(h.hdhr),0) mweekhr FROM ( SELECT w.hsid, e.name, w.emid ,SUM(NVL(wd.monhr,0)) + SUM(NVL(wd.tueshr,0)) + SUM(NVL(wd.wedhr,0)) + SUM(NVL(wd.thurhr,0)) + SUM(NVL(wd.frihr,0)) + SUM(NVL(wd.sathr,0)) + SUM(NVL(wd.sunhr,0)) rweekhr, SUM(NVL(wd.monoverhr,0)) + SUM(NVL(wd.tuesoverhr,0)) + SUM(NVL(wd.wedoverhr,0))+ SUM(NVL(wd.thuroverhr,0)) + SUM(NVL(wd.frioverhr,0)) + SUM(NVL(wd.satoverhr,0))+ SUM(NVL(wd.sunoverhr,0)) overtime ,w.stdes FROM  workhour w, workhourdetail wd, employee e WHERE w.hsid = wd.hsdeid AND w.emid = wd.emid AND w.EMID = e.emid AND w.stdes = 1 GROUP BY w.hsid, w.emid, e.name, w.stdes) t LEFT JOIN holiday h ON t.hsid = h.hdweek GROUP BY t.hsid, t.name, t.emid, t.rweekhr, t.overtime, t.stdes ORDER BY t.hsid,t.emid";
	private static final String GET_FILTER_STMT = "SELECT t.*, 56-NVL(SUM(h.hdhr),0) mweekhr FROM ( SELECT w.hsid, e.name, w.emid ,SUM(NVL(wd.monhr,0)) + SUM(NVL(wd.tueshr,0)) + SUM(NVL(wd.wedhr,0)) +  SUM(NVL(wd.thurhr,0)) + SUM(NVL(wd.frihr,0)) + SUM(NVL(wd.sathr,0)) + SUM(NVL(wd.sunhr,0)) rweekhr, SUM(NVL(wd.monoverhr,0)) + SUM(NVL(wd.tuesoverhr,0)) + SUM(NVL(wd.wedoverhr,0)) + SUM(NVL(wd.thuroverhr,0)) + SUM(NVL(wd.frioverhr,0)) + SUM(NVL(wd.satoverhr,0))  + SUM(NVL(wd.sunoverhr,0)) overtime  FROM  workhour w, workhourdetail wd, employee e         WHERE w.hsid = wd.hsdeid AND w.emid = wd.emid AND w.EMID = e.emid         GROUP BY w.hsid, w.emid, e.name) t LEFT JOIN holiday h ON t.hsid = h.hdweek WHERE SUBSTR(TO_CHAR(t.hsid),1,4) like ? AND SUBSTR(TO_CHAR(t.hsid),5,2) like ? GROUP BY t.hsid, t.name, t.emid, t.rweekhr ,t.overtime ORDER BY t.hsid,t.emid";
	private static final String GET_ONE_STMT = "SELECT wd.hsdeid, wd.emid, e.name, wd.hscontent, NVL(wd.monhr,0) monhr, NVL(wd.tueshr,0) tueshr, NVL(wd.wedhr,0) wedhr, NVL(wd.thurhr,0) thurhr, NVL(wd.frihr,0) frihr, NVL(wd.sathr,0) sathr, NVL(wd.sunhr,0) sunhr, NVL(wd.monoverhr,0) monoverhr, NVL(wd.tuesoverhr,0) tuesoverhr, NVL(wd.wedoverhr,0) wedoverhr, NVL(wd.thuroverhr,0) thuroverhr, NVL(wd.frioverhr,0) frioverhr, NVL(wd.satoverhr,0) satoverhr, NVL(wd.sunoverhr,0) sunoverhr FROM workhourdetail wd JOIN employee e	ON e.emid = wd.emid	WHERE wd.hsdeid = ? AND wd.emid = ?";
	private static final String GET_EXISTYEAR_STMT = "SELECT DISTINCT SUBSTR(hsid,1,4) existyear, stdes FROM workhour WHERE stdes = 1 ORDER BY 1 DESC";
	private static final String GET_EXISTMON_STMT = "SELECT DISTINCT SUBSTR(hsid,5,2) existmon, stdes FROM workhour WHERE SUBSTR(hsid,1,4) = ? AND stdes = 1 ORDER BY 1 ASC";	
	
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
	public void generateExcel(Map<Integer, Object[]> data){
		//Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");
        
        //Iterate over data and write to sheet
        Set<Integer> keyset = ((TreeMap<Integer, Object[]>) data).keySet();
        int rownum = 0;
        //int lastRowNum = sheet.getLastRowNum();
        for (Integer key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            //Row row = sheet.getRow(lastRowNum+1);
            Object [] objArr = ((TreeMap<Integer, Object[]>) data).get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
        	Path currentRelativePath = Paths.get("");
        	String s = currentRelativePath.toAbsolutePath().toString();
            FileOutputStream out = new FileOutputStream(new File(s+"\\workhour.xlsx" ));
            workbook.write(out);
            out.close();
            workbook.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}
	public Map<Integer, Object[]> writeExcel(Map<Integer, Object[]> data, SearAHRVO header, List<SearOHRVO> sohrList, Integer cnt){
//        //Blank workbook
//        XSSFWorkbook workbook = new XSSFWorkbook(); 
//         
//        //Create a blank sheet
//        XSSFSheet sheet = workbook.createSheet("Employee Data");
//          
        //This data needs to be written (Object[])
        //Map<String, Object[]> data = new TreeMap<String, Object[]>();
		
        data.put(cnt, new Object[] {"週別編號", "員工編號", "員工姓名", "本週應填時數", "本週實際時數", "加班總計"});
        data.put(cnt+1, new Object[] {header.getHsid(), header.getEmid(),header.getEname(),header.getMweekhr(), header.getRweekhr(), header.getOvertime()});	        
        data.put(cnt+2, new Object[] {"工作內容", "週一時數", "週二時數", "週三時數", "週四時數", "週五時數", "週六時數", "週日時數", "週一加班時數", "週二加班時數", "週三加班時數", "週四加班時數", "週五加班時數", "週六加班時數", "週日加班時數"});
        for(int i=0; i<sohrList.size();i++){
        	data.put(cnt+3+i , new Object[] {sohrList.get(i).getHscontent(), sohrList.get(i).getMonhr(),sohrList.get(i).getTueshr(),sohrList.get(i).getWedhr(), sohrList.get(i).getThurhr(), sohrList.get(i).getFrihr(), sohrList.get(i).getSathr(), sohrList.get(i).getSunhr(), sohrList.get(i).getMonoverhr(), sohrList.get(i).getTuesoverhr(), sohrList.get(i).getWedoverhr(), sohrList.get(i).getThuroverhr(), sohrList.get(i).getFrioverhr(), sohrList.get(i).getSatoverhr(), sohrList.get(i).getSunoverhr()});
        }
        data.put(cnt+3+sohrList.size(),new Object[]{});
        return data;
//        //Iterate over data and write to sheet
//        Set<String> keyset = ((TreeMap<String, Object[]>) data).keySet();
//        int rownum = cnt;
//        //int lastRowNum = sheet.getLastRowNum();
//        //System.out.println(lastRowNum);
//        for (String key : keyset)
//        {
//            Row row = sheet.createRow(rownum++);
//            //Row row = sheet.getRow(lastRowNum+1);
//            Object [] objArr = ((TreeMap<String, Object[]>) data).get(key);
//            int cellnum = 0;
//            for (Object obj : objArr)
//            {
//               Cell cell = row.createCell(cellnum++);
//               if(obj instanceof String)
//                    cell.setCellValue((String)obj);
//                else if(obj instanceof Integer)
//                    cell.setCellValue((Integer)obj);
//            }
//        }
//        try
//        {
//            //Write the workbook in file system
//        	Path currentRelativePath = Paths.get("");
//        	String s = currentRelativePath.toAbsolutePath().toString();
//            FileOutputStream out = new FileOutputStream(new File(s+"\\workhour.xlsx" ));
//            workbook.write(out);
//            out.close();
//            workbook.close();
//        } 
//        catch (Exception e) 
//        {
//            e.printStackTrace();
//        }
        
	}
	
	public List<SearAHRVO> findAll(){
    	List<SearAHRVO> list = new ArrayList<>();
    	SearAHRVO sahrVO = null;
    	
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ALL_STMT);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			sahrVO = new SearAHRVO();
    			sahrVO.setHsid(rs.getString("hsid"));
    			sahrVO.setEmid(rs.getString("emid"));
    			sahrVO.setEname(rs.getString("name"));
    			sahrVO.setMweekhr(rs.getInt("mweekhr"));
    			sahrVO.setRweekhr(rs.getInt("rweekhr"));
    			sahrVO.setOvertime(rs.getInt("overtime"));
    			list.add(sahrVO);
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
	
	public List<SearAHRVO> filterAll(String filYear, String filMon){
    	List<SearAHRVO> list = new ArrayList<>();
    	SearAHRVO sahrVO = null;
    	Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_FILTER_STMT);
    		pstmt.setString(1, filYear);
    		pstmt.setString(2, filMon);
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()){
    			sahrVO = new SearAHRVO();
    			sahrVO.setHsid(rs.getString("hsid"));
    			sahrVO.setEmid(rs.getString("emid"));
    			sahrVO.setEname(rs.getString("name"));
    			sahrVO.setMweekhr(rs.getInt("mweekhr"));
    			sahrVO.setRweekhr(rs.getInt("rweekhr"));
    			sahrVO.setOvertime(rs.getInt("overtime"));
    			list.add(sahrVO);
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
	
	public List<SearOHRVO> findOne(String hsid, String emid){
		List<SearOHRVO> list = new ArrayList<>();
		SearOHRVO sohrVO = null;
		
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_ONE_STMT);
    		pstmt.setString(1, hsid);
    		pstmt.setString(2, emid);
    		
    		rs = pstmt.executeQuery();
    		while(rs.next()){

    			sohrVO = new SearOHRVO();
    			sohrVO.setHsid(rs.getString("hsdeid"));
    			sohrVO.setEmid(rs.getString("emid"));
    			sohrVO.setEname(rs.getString("name"));
    			sohrVO.setHscontent(rs.getString("hscontent"));
    			sohrVO.setMonhr(rs.getInt("monhr"));
    			sohrVO.setTueshr(rs.getInt("tueshr"));
    			sohrVO.setWedhr(rs.getInt("wedhr"));
    			sohrVO.setThurhr(rs.getInt("thurhr"));
    			sohrVO.setFrihr(rs.getInt("frihr"));
    			sohrVO.setSathr(rs.getInt("sathr"));
    			sohrVO.setSunhr(rs.getInt("sunhr"));
    			sohrVO.setMonoverhr(rs.getInt("monoverhr"));
    			sohrVO.setTuesoverhr(rs.getInt("tuesoverhr"));
    			sohrVO.setWedoverhr(rs.getInt("wedoverhr"));
    			sohrVO.setThuroverhr(rs.getInt("thuroverhr"));
    			sohrVO.setFrioverhr(rs.getInt("frioverhr"));
    			sohrVO.setSatoverhr(rs.getInt("satoverhr"));
    			sohrVO.setSunoverhr(rs.getInt("sunoverhr"));
    			list.add(sohrVO);
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
	
	
	
	public List<String> getExYear(){
		List<String> list = new ArrayList<>();
		String exyearStr = null;
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_EXISTYEAR_STMT);
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()){
    			exyearStr = rs.getString("existyear");
    			list.add(exyearStr);
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
	
	public List<String> autoGetMon(String existyear){
		List<String> list = new ArrayList<>();
		String exmonStr = null;
		Connection conn = null;
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	
    	try{
    		conn = DriverManager.getConnection(url,username,passwd);
    		pstmt = conn.prepareStatement(GET_EXISTMON_STMT);
    		pstmt.setString(1, existyear);
    		rs = pstmt.executeQuery();
    		
    		while(rs.next()){
    			exmonStr = rs.getString("existmon");
    			list.add(exmonStr);
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
