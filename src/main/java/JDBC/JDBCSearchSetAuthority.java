package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Bean.Employee;

public class JDBCSearchSetAuthority extends JDBCConnection {
	
      private static final String update_Autid = "UPDATE employee set autid=? where emid=?";
	  private static final String JDBC_getAutid="select autid from employee where emid=?";
      private static final String JDBC_getEmData2 ="select emid,name,id,autid from employee where emid like '%?%' or name like '%'||?||'%' or id like '%'||?||'%' or autid in ('||?||')";
      //private static final String JDBC_getEmData ="select emid,name,id,autid from employee where emid =? or name=? or id=? or autid =?";
      private static final String JDBC_getEmData ="select emid,name,id,autid from employee where emid like ? or name like ? or id like ? or autid like ?";
      private static final String JDBC_getOneEm ="select emid,name,id,autid from employee where emid =?";

      

      public int updateAutid(String Emid,Integer Autid) {
    	  //修改權限
    	    int editAutid_status = 0;
	    	PreparedStatement pstmt = null;

	        try {
	        	Connection con= connections();
	            pstmt = con.prepareStatement(update_Autid);
	            pstmt.setInt(1, Autid);
	            pstmt.setString(2, Emid);       
	            pstmt.executeQuery();
	            pstmt.close();
	            
	            editAutid_status = 1; //更新成功
				
			} catch(Exception e){
				
				editAutid_status = 2; //發生錯誤
			}
			
			return editAutid_status;
	    }
      
      public int getAutid(String Emid){
  		int autid = 0;
  		PreparedStatement pstmt = null;
  		ResultSet rs = null ;
  		
  		try{
  			Connection conn= connections();
  			pstmt = conn.prepareStatement(JDBC_getAutid);
  			pstmt.setString(1,Emid);
  			rs = pstmt.executeQuery();
  			
  			while(rs.next()){
  				
  				autid = rs.getInt("autid");
  				pstmt.close();
  			}
  		}catch (Exception e) {
  				autid = 0;
  			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
  			
  		}
  		return autid;
  		
  	} 
      
      public Employee findByKeyWords(String Emid) {
    	    //檢索關鍵字搜尋
    	    Employee empVO = null;
	    	PreparedStatement pstmt = null;
	    	ResultSet rs = null;

          try {
        	  Connection conn= connections();
              pstmt = conn.prepareStatement(JDBC_getOneEm);
              pstmt.setString(1, Emid);
              rs = pstmt.executeQuery();

              while (rs.next()) {
                  empVO = new Employee();
                  empVO.setEmid(rs.getString("Emid"));
                  empVO.setName(rs.getString("name"));
                  empVO.setId(rs.getString("id"));
                  empVO.setAutid(rs.getInt("Autid"));
              }
          }catch (SQLException se) {
              throw new RuntimeException("資料庫讀取錯誤:"+ se.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return empVO;
		
      }
      public List<Employee> findAll(String Emid,String name,String id,Integer Autid) {
          List<Employee> list = new ArrayList<>();
          Employee empVO = null;
          Connection con = null;
          PreparedStatement pstmt = null;
          ResultSet rs = null;

          try {
        	  Connection conn= connections();
        	  pstmt = conn.prepareStatement(JDBC_getEmData);
              pstmt.setString(1, "%"+Emid+"%");
              pstmt.setString(2, "%"+name+"%");
              pstmt.setString(3, "%"+id+"%");
              pstmt.setString(4, "%"+Autid+"%");
              rs = pstmt.executeQuery();

              while (rs.next()) {
                  // empVO 也稱為 Domain objects
            	  empVO = new Employee();
                  empVO.setEmid(rs.getString("Emid"));
                  empVO.setName(rs.getString("name"));
                  empVO.setId(rs.getString("id"));
                  empVO.setAutid(rs.getInt("Autid"));
                  list.add(empVO); // Store the row in the list
              }

              // Handle any SQL errors
          } catch (SQLException se) {
              throw new RuntimeException("A database error occured. " + se.getMessage());
              // Clean up JDBC resources
          } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
          }
          return list;
      }
      
      
    
      
      //測試
      public static void main(String[] args) {
    	  
    	    JDBCSearchSetAuthority test = new JDBCSearchSetAuthority();
    	  //修改
//  		String emid ="em0003";
//  		Integer Autid = 1;
//  		System.out.print(test.updateAutid(emid, Autid));
    	  
//          查詢
//    	  Employee empV1 = test.findByKeyWords("em0001", "em0001", "em0001", 5);
//	      System.out.print(empV1.getEmid() + ",");
//	      System.out.print(empV1.getName() + ",");
//	      System.out.print(empV1.getId() + ",");
//	      System.out.println(empV1.getAutid());
      }
}

	  


