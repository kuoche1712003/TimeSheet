package JDBC;

import java.nio.ShortBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Utils.Link;

import Bean.Employee;

public class JDBCEmployee extends JDBCConnection 
{

    private static final String INSERT_STMT = "INSERT INTO employee (emid,name,id,gender,email) VALUES (?,?, ?, ?, ?)";
    private static final String GET_ALL_STMT = "SELECT * FROM employee ORDER BY emid";
    private static final String GET_ALL_BASE_STMT = "SELECT * FROM employee WHERE 1 = 1";
    private static final String GET_ONE_STMT = "SELECT * FROM employee WHERE emid =?";
    private static final String DELETE = "DELETE FROM employee where emid =?";
    private static final String UPDATE = "UPDATE employee set name=?, id=?, gender=?, email=?  where emid = ?";
    private static final String GetEmidExist ="select count(*)  from employee where emid=?";

	private static final String GetEmid ="select emid.NEXTVAL from dual";

	
      
    public  boolean insert(Employee employee,int port) {
//        Connection con = null;
        PreparedStatement pstmt = null;
        int addemp_status = 0;
        
        JDBCEmployee emp = new JDBCEmployee();
        try {
        	Connection  con = connections();
            pstmt = con.prepareStatement(INSERT_STMT);

            String emid = emp.GenerateEmid(); 
            String name = employee.getName();
            String id = employee.getId();
            int gender = employee.getGender();
            String email = employee.getEmail(); 
            
            
            pstmt.setString(1, emid);
            pstmt.setString(2, name);
            pstmt.setString(3, id);
            pstmt.setInt(4, gender);
            pstmt.setString(5, email);
            pstmt.executeUpdate();
      	    pstmt.close();
          
          
      	    doStart(emid,name,email,port);
            
//            pstmt.setString(1, emid);
//            pstmt.setString(2, employee.getName());
//            pstmt.setString(3, employee.getId());
//            pstmt.setInt(4, employee.getGender());
//            pstmt.setString(5, employee.getEmail());

           
      	    addemp_status = 1; //新增成功
			
		} catch(Exception e){
			
			addemp_status = 2; //發生錯誤
		}
		
		if(addemp_status == 1){
			return true;
		}else{
			return false;
		}	
    }
    
// edit employee data
    
    public boolean update(Employee employee) {
        PreparedStatement pstmt = null;
        int editemp_status = 0;
       
       
   
//        JDBCConnection connection = new JDBCConnection();
        try {
        	Connection con= connections(); 
            pstmt = con.prepareStatement(UPDATE);

//            System.out.println(employee.getEmid());
//            System.out.println(employee.getName());
//            
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getId());
            pstmt.setInt(3, employee.getGender());
            pstmt.setString(4, employee.getEmail());
            pstmt.setString(5, employee.getEmid());
//            int  updateCountEMPs = pstmt.executeUpdate();
            pstmt.executeQuery();
            
//            int updateCountEMPs = pstmt.executeUpdate();
//            System.out.println("已更新員工 " + updateCountEMPs + " 人!");
            

            pstmt.close();
            editemp_status = 1; //修改成功
            System.out.println("update susess");
           
		} catch(Exception e){
			editemp_status = 2; //發生錯誤
			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
			
		}
		
		if(editemp_status == 1){
			return true;
		}else{
			return false;
		}	
    }
// primary key query
    public Employee findByPrimaryKey(String emid)  {
        Employee empVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        	Connection con = connections();
            pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setString(1, emid);
            rs = pstmt.executeQuery();
           
            while (rs.next()) {
                // empVo 也稱為 Domain objects
                empVO = new Employee();
                empVO.setEmid(rs.getString("emid"));
                empVO.setName(rs.getString("name"));
                empVO.setId(rs.getString("id"));
                empVO.setAutid(rs.getInt("autid"));
                empVO.setEmail(rs.getString("email"));
                empVO.setPasswd(rs.getString("passwd"));
                empVO.setEmstatus(rs.getInt("emstatus"));
                empVO.setEmacstatus(rs.getInt("emacstatus"));
                empVO.setEmailkey(rs.getString("emailkey"));
                empVO.setLeavedate(rs.getDate("leavedate"));
                empVO.setStartdate(rs.getDate("startdate"));
                empVO.setGender(rs.getInt("gender"));
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
        return empVO;

           
    }
    
//query
    private List<Employee> doFindAll(String sql) {
        List<Employee> list = new ArrayList<>();
        Employee empVO = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	Connection con1 = connections();
            pstmt = con1.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // empVO 也稱為 Domain objects
            	empVO = new Employee();
                empVO.setEmid(rs.getString("emid"));
                empVO.setName(rs.getString("name"));
                empVO.setId(rs.getString("id"));
                empVO.setAutid(rs.getInt("autid"));
                empVO.setEmail(rs.getString("email"));
                empVO.setPasswd(rs.getString("passwd"));
                empVO.setEmstatus(rs.getInt("emstatus"));
                empVO.setEmacstatus(rs.getInt("emacstatus"));
                empVO.setEmailkey(rs.getString("emailkey"));
                empVO.setLeavedate(rs.getDate("leavedate"));
                empVO.setStartdate(rs.getDate("startdate"));
                empVO.setGender(rs.getInt("gender"));
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
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return list;
    }
  
    private String GenerateEmid(){
		   String emid;
		   StringBuffer em = new StringBuffer();
		   PreparedStatement pstmt = null;
			ResultSet rs = null ;
			long emid_int = 0;
			try{
				

			Connection conn= connections();

			pstmt = conn.prepareStatement(GetEmid);
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				emid_int = rs.getLong(1);
		
				
			}
			}catch(Exception e){
				
				throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

			}
			
			DecimalFormat df1 = new DecimalFormat("####"); 
			
			df1.format(emid_int);
			
			
			em.append("em");
			em.append(String.format("%04d", emid_int));
				
		   return em.toString();
	   }
	
    public void doStart(String emid, String name, String email,int port) throws Exception{
    	Link link = new Link();
    	 
    	 
    	 String link_string = link.StartLink(emid);
    	 SendMail mail = new SendMail();
    	 mail.Send_Start_Mail(emid, name, email, link_string,port);
//    	 
    	 
    	 System.out.println(emid+" "+name+ " "+ email+" "+link_string );
    	
    	 
    	 
    }
    
    public boolean isEmidExist(String emid){
    	

		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		int count= -1;
		try{
			

		Connection con= connections();

		pstmt = con.prepareStatement(GetEmidExist);
        pstmt.setString(1, emid);

		rs = pstmt.executeQuery();

		while(rs.next()){
			 count = rs.getInt(1);
			 System.out.println(count);
			
		}
		}catch(Exception e){
			
			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

		}
		
		
		if(count>=1){
			return false;
		}else{
			return true;
		}
		
    					
    }
    
    
    
//  test
    public static void main(String[] args) {
        Employee dao = new Employee();

/*         TODO 03-2. 測試新增
        JDBCAddEmp empVO = new JDBCAddEmp();
        dao.setName("安安");
        dao.setId("E123456789");
        dao.setGender(1);
        dao.setEmail("1@1333");
        empVO.insert(dao);                    */
        
/*         TODO 04-2. 測試修改
        JDBCAddEmp empVO = new JDBCAddEmp();
        dao.setName("小明");
        dao.setId("A123456789");
        dao.setGender(1);
        dao.setEmail("1@55");
        empVO.update(dao);                     */
        
/*         TODO 06-2. 測試主鍵查詢
        JDBCAddEmp empVO = new JDBCAddEmp();
        Employee empV2 = empVO.findByPrimaryKey("em0004");
        System.out.print(empV2.getEmid() + ",");
        System.out.print(empV2.getName() + ",");                         */
        
     // TODO 07-2. 測試查詢
//        JDBCEmployee empVO = new JDBCEmployee();
//     List<Employee> list = empVO.doFindAll(null);
//      for (Employee aDept : list) {
//          System.out.print(aDept.getEmid() + ",");
//          System.out.print(aDept.getName() + ",");
//          System.out.println();
//    }  
 
}
}