package JDBC;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URLEncoder;
import java.nio.ShortBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.crypto.NoSuchPaddingException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendMail extends JDBCConnection {
	PreparedStatement pstmt = null;
	ResultSet rs = null ;
	
	private static final String update_Password="Update Employee set passwd=? where emid=?";
	private static final String GET_EMAIL = "SELECT email FROM employee WHERE emid = ?";

	private static String host = "smtp.gmail.com";
	private static int port = 587;
	private static final String user= "iisi.timesheet@gmail.com";
	private static final String passwd= "iisiiisi";
	private static final String projectPath="TimesheetV20";
	private static final String CHECK_CODE = "checkCode";  
	
	
	public int sendForgetMail(String Name,String Email,String Emid){
		int mail_status = 0;
		StringBuffer newPwd = new StringBuffer();

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, passwd);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("iisi.timesheet@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Email));
			message.setSubject("Timesheet-員工忘記密碼通知");

			newPwd = RandomPwd();

			message.setText("親愛的 " + Name +"(員工編號:"+ Emid + ") 您好,\n\n您的新密碼為:" + newPwd
					+ "\n\n請重新登入並修改密碼\n\nTimesheet Systems");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, user, passwd);

			transport.sendMessage(message, message.getAllRecipients());

			System.out.println("Done");
			mail_status = 5;
		} catch (MessagingException e) {
			mail_status = 6;
			throw new RuntimeException(e);
		}

		PreparedStatement pstmt = null;
		
		
		try {

			Connection conn= connections();

			String newpasswd = newPwd.toString();

			pstmt = conn.prepareStatement(update_Password);
			pstmt.setString(1, newpasswd);
			pstmt.setString(2, Emid);
			pstmt.executeUpdate();

			mail_status = 7;// 成功修改

			pstmt.close();
			conn.close();
		}

		catch (Exception e) {
			mail_status = 8;// 更新密碼時發生錯誤
		}

		return mail_status;
	}
	
	public boolean Send_Start_Mail(String emid, String name, String email, String link,int serverPort) throws Exception{
		int status = 0;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, passwd);
			}	});
			
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("iisi.timesheet@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
				message.setSubject("Timesheet-員工帳號啟用通知");
				String dir = System.getProperty("user.dir");

				String connect_link = "http://localhost:" + serverPort+"/"+ projectPath + "/EmployeeController?action=verify&link="+link;
		
				String html = "<h4>親愛的" + name + "(員工編號:"+ emid + ") 您好,\n\n 請點擊連結啟用帳號:</h4>"+ "<a href="+connect_link+"/>啟用帳號連結</a>";

				message.setContent(html,"text/html; charset=utf-8" );
				


				Transport transport = session.getTransport("smtp");
				transport.connect(host, port, user, passwd);

				transport.sendMessage(message, message.getAllRecipients());

				System.out.println("Done");
				
			} catch (MessagingException e) {
				status = 1;
				throw new RuntimeException("錯誤:"+ e.getMessage(),e);
			

			}
			
			
	
		if(status == 1){
			return false;
		}else{
			return true;
		}
			
	}
	
	
	public String getEmail(String emid){
		String mail = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
    		conn = DriverManager.getConnection(JDBCData.URI,JDBCData.USERNAME,JDBCData.PASSWORD);
    		pstmt = conn.prepareStatement(GET_EMAIL);
    		pstmt.setString(1, emid);
    		rs = pstmt.executeQuery();
    		while(rs.next()){
    			mail = rs.getString("email");
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
        return mail;
	}
	public void reviewMail(String email, String hsid, String emid, String ename, String reasonMsg){
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, passwd);
			}
		});
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("iisi.timesheet@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject("Timesheet-審核退回通知");

			
			message.setText("親愛的 " + ename + " (員工編號: "+ emid + " ) 您好，"
					+ "\n\n您於週別： "+ hsid +" 所填寫之工時，"
					+ "\n\n經主管審核後，因有誤而遭退回，請盡速修正！"
					+ "\n\n原因如下：" + reasonMsg 
					+ "\n\nTimesheet Systems");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, user, passwd);

			transport.sendMessage(message, message.getAllRecipients());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	public void urgeMail(String email, String hsid, String emid, String ename){
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, passwd);
			}
		});
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("iisi.timesheet@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject("Timesheet-工時催交通知");

			
			message.setText("親愛的 " +ename+ " (員工編號 : "+ emid + ") 您好，"
					+ "\n\n您於週別： "+ hsid +" 所應繳交工時紀錄尚未繳交，"
					+ "\n\n請盡速填寫或修改後送交主管審核！"
					+ "\n\nTimesheet Systems");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, user, passwd);

			transport.sendMessage(message, message.getAllRecipients());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	public void exportExcel(String email, String emid, String ename) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, passwd);
			}
		});
		
		try {
			// 附件檔案
	        MimeBodyPart filePart = new MimeBodyPart();
	        // 檔案位置
	        Path currentRelativePath = Paths.get("");
        	String s = currentRelativePath.toAbsolutePath().toString();
	        filePart.attachFile( s +"\\workhour.xlsx");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("iisi.timesheet@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
			message.setSubject("Timesheet-工時匯出通知");

			
			message.setText("親愛的 " +ename+ " (員工編號 : "+ emid + ") 主管您好，"
					+ "這是您匯出的工時。"
					+ "\n\nTimesheet Systems");

	        // 把附件檔案加入
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(filePart);
			message.setContent(multipart);
	
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, user, passwd);

			transport.sendMessage(message, message.getAllRecipients());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}catch(IOException i){
			throw new RuntimeException(i);
		}

	}
	
	public StringBuffer RandomPwd(){
		StringBuffer newPwd = new StringBuffer();

		int[] pwd = new int[8];
		int mod;
		for (int i = 0; i < 8; i++) {
			mod = (int) ((Math.random() * 7) % 3);

			if (mod == 1) {
				pwd[i] = (int) ((Math.random() * 10) + 48);
			} else if (mod == 2) {
				pwd[i] = (char) ((Math.random() * 26) + 65);

			} else {
				pwd[i] = (char) ((Math.random() * 26) + 97);

			}
		}
		for (int j = 0; j < 8; j++) {
			newPwd.append((char) pwd[j]);
		}
		
		return newPwd;
	}
	
/*public  String generateResetPwdLink(String email) { 
		 
	        return "http://localhost:8080/TimesheetV10/EditPwd.jsp?forget_email="   
	                + email + "&" + CHECK_CODE + "=" + generateCheckcode(email);  
	    }  
*/	
	 public  String generateCheckcode(String email) {  
		 	
		 SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();
		 
		 Calendar cal= Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.HOUR_OF_DAY, 3);
		 
		 String strDate = sdFormat.format(cal.getTime());
		 

		 return md5(email);
	    }  
	 
	 
	 private static String md5(String string) {  
	        MessageDigest md = null;  
	        try {  
	            md = MessageDigest.getInstance("md5");  
	            md.update(string.getBytes());  
	            byte[] md5Bytes = md.digest();  
	            return bytes2Hex(md5Bytes);  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        }  
	          
	        return null;  
	    }  
	 
	 private static String bytes2Hex(byte[] byteArray)  
	    {  
	        StringBuffer strBuf = new StringBuffer();  
	        for (int i = 0; i < byteArray.length; i++)  
	        {  
	            if(byteArray[i] >= 0 && byteArray[i] < 16)  
	            {  
	                strBuf.append("0");  
	            }  
	            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));  
	        }  
	        return strBuf.toString();  
	    }  
	 
//		public static void main(String[] args) throws Exception{
//		
//			ServerSocket ss = new ServerSocket();
////			System.out.println（request.getServletPath()+request.getServerPort());
//			System.out.println(ss.getLocalSocketAddress());
//			System.out.println(ss.getLocalPort());
//			System.out.println("伺服器啟動於 : " + ss.getInetAddress().getHostAddress());
//		}
////			  	String dir = System.getProperty("user.dir");
//		        System.out.println("current dir = " + dir);
//		        String[] sp = dir.split("\\\\");
//		        System.out.println(sp[2]);
//		        String link="aaa";
//				String connect_link = "http://localhost:8080/"+sp[2]+"/EmployeeController?action=verify&link="+link;
//				System.out.println(connect_link);
		       
//		
		
//	}
	 
	 
//	 public UrgeMail(String email,String emid,){
//		 
//		 
//		 
//	 }
	 
}
