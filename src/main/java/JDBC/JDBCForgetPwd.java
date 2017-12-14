package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Bean.Employee;


public class JDBCForgetPwd extends JDBCConnection{

	private static final String forget_Query="select email,name from employee where emid=?";
	
	public Employee ForgetPwd(String Emid, Map<String,String> errorMsgs){
		int mailStatus = 0;
		Employee emp = new Employee();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		
		try{
			Connection conn= connections();
			pstmt = conn.prepareStatement(forget_Query);
			pstmt.setString(1,Emid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getString("email") !=null){
					mailStatus = 1; //有找到email
					String email = rs.getString("email");
					String name = rs.getString("name");
					emp.setEmail(email);
					emp.setName(name);
					
					errorMsgs.put("mail_status", String.valueOf(mailStatus));
				}
				else {
					mailStatus = 2; //沒有找到信箱
					errorMsgs.put("errorMsgs", "系統查無此信箱");
					errorMsgs.put("mail_status", String.valueOf(mailStatus));


					
				}
				
			}else{
				
				  mailStatus = 3; //無此員工編號
				  errorMsgs.put("mail_status", String.valueOf(mailStatus));
				  errorMsgs.put("errorMsgs", "系統查無此員工編號");

			}
		}catch (Exception e) {
			mailStatus = 4;  //發生錯誤
			errorMsgs.put("mail_status", String.valueOf(mailStatus));
			errorMsgs.put("errorMsgs", "系統發生錯誤");

		}
			
			
//		return forget_mail_status;
		return emp;
	}
	
	
	
	
	
	
	
}
