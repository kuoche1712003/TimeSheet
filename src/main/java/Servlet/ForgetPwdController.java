package Servlet;

import static Utils.VaildateUtils.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Employee;
import JDBC.JDBCForgetPwd;
import JDBC.JDBCLogin;
import JDBC.SendMail;

import javax.mail.*;

/**
 * Servlet implementation class ForgetPwdController
 */
public class ForgetPwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String forgetPwdPage ="/ForgetPwd.jsp";
       String Emid;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetPwdController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		Map<String, String> errorMsgs = new HashMap<>();
		Map<String, String> Msgs = new HashMap<>();
		
		Emid = request.getParameter("emid");
		
		if(isBlank(Emid)){
				errorMsgs.put("error", " 請輸入員工編號");
				request.setAttribute("errorMsgs", errorMsgs);
	            RequestDispatcher rd = request.getRequestDispatcher(forgetPwdPage);
				rd.forward(request, response);;
		}else if(!isEmid(Emid)){
			errorMsgs.put("error", " 員工編號格式錯誤 ");
			request.setAttribute("errorMsgs", errorMsgs);
            RequestDispatcher rd = request.getRequestDispatcher(forgetPwdPage);
			rd.forward(request, response);;
		}
		
		JDBCForgetPwd forgetPwd = new  JDBCForgetPwd();
		SendMail sendmail = new SendMail();
		
		
		Employee emp = forgetPwd.ForgetPwd(Emid,errorMsgs);
//		1 系統找到電子郵件
//		if(!errorMsgs.isEmpty()){
//            request.setAttribute("errorMsgs", errorMsgs);
//            RequestDispatcher rd = request.getRequestDispatcher(forgetPwdPage);
//			rd.forward(request, response);;
//
//		}

		String mail_status = errorMsgs.get("mail_status");
		System.out.println(mail_status);
		

		
		if(Integer.parseInt(mail_status) == 1){
//			JDBCLogin forgetPasswd = new JDBCLogin();
//			String Email = forgetPasswd.getEmail(Emid);
//			String Name = forgetPasswd.getName(Emid);
			
			String Email = emp.getEmail();
			String Name = emp.getName();
			
			
			
			int sendmail_status = sendmail.sendForgetMail(Name, Email, Emid);
						
			if(sendmail_status == 7){
				//寄發電子郵件與更新密碼成功
				String test ="已傳送新密碼至"+ Email+"，\n\n請重新登入並修改密碼。";
				Msgs.put("status", test);
	            request.setAttribute("Msgs", Msgs);
	           
	            System.out.println(Msgs);
	            String page="/ForgetPwd.jsp";
	           
	            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
	            dispatcher.forward(request, response);
//				response.sendRedirect("ForgetPwd.jsp?forget_status=" + sendmail_status + "&Email="+Email);

			}else{
				if(sendmail_status == 6){
					errorMsgs.put("errorMsgs", "郵件設定失敗");
				}else if(sendmail_status == 8){
					errorMsgs.put("errorMsgs", "系統更新密碼時發生錯誤");

				}
				
	            request.setAttribute("errorMsgs", errorMsgs);

				RequestDispatcher dispatcher = request.getRequestDispatcher(forgetPwdPage);
	            dispatcher.forward(request, response);
				
//				response.sendRedirect("ForgetPwd.jsp?forget_status="+ sendmail_status);
//				5郵件設定成功
//				6郵件設定失敗
//				7密碼成功修改且已寄發電子郵件
//				8更新密碼時發生錯誤
			}
		
			
		}else{
//			2 系統沒有找到電子郵件
//			3無此員工編號
//			4發生錯誤
			request.setAttribute("errorMsgs", errorMsgs);
            RequestDispatcher rd = request.getRequestDispatcher(forgetPwdPage);
			rd.forward(request, response);;
//			response.sendRedirect("ForgetPwd.jsp?status="+ mail_status);
		}
		

//		

		
	}

}
