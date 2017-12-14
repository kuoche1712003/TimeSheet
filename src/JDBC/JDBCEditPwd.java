package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCEditPwd extends JDBCConnection {

	private static final String password_Query="select passwd from employee where emid=?";
	private static final String password_Update="update employee set passwd=? where emid=?";
	

	public boolean JDBCEditPwdVerify(String Emid,String Password){
		//員工編號 密碼 與資料庫驗證
		int pwd_status = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		
		try{
			Connection conn= connections();
			pstmt = conn.prepareStatement(password_Query);
			pstmt.setString(1,Emid);
			
			rs = pstmt.executeQuery();
			if (rs.next()){
				
				if(rs.getString("PASSWD").equals(Password)){
					 pwd_status = 1; //密碼正確
					 
				}
			}else{
				
					pwd_status = 2; //密碼錯誤
					
			}
		}catch (Exception e) {
			pwd_status = 3;  //發生錯誤
			
		}
		
		if(pwd_status == 1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean JDBCEditPwdComparsion(String NewPassword,String NewPassword2){
		//兩次新密碼驗證
		int compare_status = 0;
		
		if(NewPassword.equals(NewPassword2)){
			compare_status = 1;    //兩次新密碼相同
		}
		else {
			compare_status = 2;  //兩次新密碼不同
		}
		
		if(compare_status == 1){
			return true;
		}else{
			return false;
		}
			
	
		
	}
	
	public boolean JDBCEditPasswd(String Emid, String NewPassword){
		//更新密碼
		int editpasswd_status = 0;
		
		PreparedStatement pstmt = null;
		
		try{
			Connection conn= connections();
			pstmt = conn.prepareStatement(password_Update);
			pstmt.setString(1, NewPassword);
			pstmt.setString(2, Emid);
			pstmt.executeUpdate();
			
			pstmt.close();
			
			editpasswd_status = 1; //更新成功
			
		} catch(Exception e){
			
			editpasswd_status = 2; //發生錯誤
		}
		
		if(editpasswd_status == 1){
			return true;
		}else{
			return false;
		}	
		
	}

	public boolean JDBCEditPwdVerify2(String Password,String NewPassword)
	{
		//新舊密碼密碼比較
		int pwd_status_ = 0;
		
		if(Password.equals(NewPassword)){
			pwd_status_ = 1;  //新舊密碼相同，錯誤
			
		}else{
			pwd_status_ = 2;  //新舊密碼不同
		}
			
		if(pwd_status_ == 2){
			return true;
		}else{
			return false;
		}
		
	}
//	public static void main(String[] args){
//		JDBCEditPwd test = new JDBCEditPwd();
//		String emid ="em0002";
//		String password = "0000";
//		System.out.print(test.JDBCEditPasswd(emid, password));
//		
//		
//	}
}
