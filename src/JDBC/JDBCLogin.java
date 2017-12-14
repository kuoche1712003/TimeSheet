package JDBC;

import com.oracle.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;



public class JDBCLogin extends JDBCConnection  {

	
	private static final String JDBC_Query="select passwd, autid, email, name from employee where emid=?";
	private static final String JDBC_getAutid="select autid from employee where emid=?";
	private static final String JDBC_getName="select name from employee where emid=?";
	private static final String JDBC_getEmail  = "select email from employee where emid =?";
	
	
	public int VerifyEmployee(String Emid,String Password) {
		int status = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		String AdminID = "admin";
		String AdminPassword = "admin";
		
		
		try{
			Connection conn= connections();
			pstmt = conn.prepareStatement(JDBC_Query);
			pstmt.setString(1,Emid);
			
			rs = pstmt.executeQuery();
			
			
			
			
			if  (rs.next()) {
				if (rs.getString("PASSWD").equals(Password)) {
						if(rs.getInt("AUTID")==1){
							status = 1; //帳號密碼正確 主管身分
						}
						else if(rs.getInt("AUTID")==2){
							status = 2;   //帳號密碼正確 員工身分
						} 
						else if (rs.getInt("AUTID")==3){
							status =3; //帳號密碼正確 身分未設定
						}
				} else {
						status = 4;// 帳號或密碼錯誤
				}
	
			} else {
				if(Emid.equals(AdminID)  && AdminPassword.equals(Password) )
				{
				status = 0;//系統管理員
				}
				else{
					status = 5;
					// 無此帳號
				}
			}
			rs.close();
			pstmt.close();
			conn.close();

		}catch (Exception e) {
			status = 6;  //發生錯誤
		}

		return status;
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
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
		}catch (Exception e) {
				autid = 0;
			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
			
		}
		return autid;
		
	}
	
	public String getName(String Emid){
		String name="";
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		try {
				Connection conn= connections();
				pstmt = conn.prepareStatement(JDBC_getName);
				pstmt.setString(1,Emid);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					name = rs.getString("name");
				}
				
				rs.close();
				pstmt.close();
				conn.close();
				
			}catch (Exception e) {
					name = "???????";
				throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
				
			}
		return name;
	}
	
	public String getEmail(String Emid){
		String Email="";
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		try {
				Connection conn= connections();
				pstmt = conn.prepareStatement(JDBC_getEmail);
				pstmt.setString(1,Emid);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
					
					Email = rs.getString("email");
				}
				rs.close();
				pstmt.close();
				conn.close();
				
			}catch (Exception e) {
					Email = "???????";
				throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
				
			}
		return Email;
		
		
	}
	
//	public static void main(String[] args){
//		JDBCLogin test = new JDBCLogin();
//		String emid ="admin";
//		String password = "admin";
//		System.out.print(test.VerifyEmployee(emid, password));
//		
//		
//	}
	
}





