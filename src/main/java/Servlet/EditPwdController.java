package Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import JDBC.JDBCEditPwd;
import JDBC.JDBCForgetPwd;
import Utils.AES;
import Utils.Link;
import static Utils.VaildateUtils.isPasswd;

/**
 * Servlet implementation class EditPwdController
 */
public class EditPwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String editPwdMan ="/WEB-INF/Views/Manager/EditPwdMan.jsp";	
	String editPwd ="/WEB-INF/Views/Employee/EditPwd.jsp";	

    String StartEditPwd = "/WEB-INF/Views/Employee/StartEditPwd.jsp";
    String loginPage = "/index.jsp";
	String Password;
	String NewPassword;
	String NewPassword2;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPwdController() {
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
//		ServletContext context = getServletContext();
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		
		String path = null;
        String action =	(String) request.getParameter("action");
        switch (action) {
            // 轉交至新增頁面
        	case "starteditpwd":
        		doStartEditPwd(request,  response);
        		break;
            case "editpwdman":
                doEditPwdMan( request,  response);
                break;
            case "editpwd":
            	doEditPwd(request,  response);
            	break;
           
        }    
        
//         RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//         dispatcher.forward(request, response);    
        
		
//		context.log(Password);
//		context.log(newpassword);
//		context.log(newpassword2);
		
		
		
	}
	
	
	public void doStartEditPwd( HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();

		NewPassword = request.getParameter("newpassword");
		NewPassword2 = request.getParameter("newpassword2");
		int editpwd_status = 0;
//		Link link = new Link();
//		AES aes = new AES();
		String emid=request.getParameter("emid");
		Map<String, String> Msgs = new HashMap<>();
		
		
		
		
		System.out.println(emid);
//		String emid = link.getEmid();
		JDBCEditPwd editpwd = new JDBCEditPwd();
		if(isPasswd(NewPassword) && isPasswd(NewPassword2)){

		if(editpwd.JDBCEditPwdComparsion(NewPassword, NewPassword2)){
			if(editpwd.JDBCEditPasswd(emid, NewPassword)){
				Msgs.put("success", "設定密碼成功，請重新登入");
				request.setAttribute("Msgs", Msgs);
	            RequestDispatcher rd = request.getRequestDispatcher(loginPage);
				rd.forward(request, response);;
				editpwd_status = 1; //更新成功
//				response.sendRedirect("index.jsp?editpwd_status=" + editpwd_status + "#EditPwd");
			}else{
				editpwd_status = 2; //更新失敗
				Msgs.put("error", "修改密碼失敗");
				request.setAttribute("Msgs", Msgs);
	            RequestDispatcher rd = request.getRequestDispatcher(StartEditPwd);
				rd.forward(request, response);
//				RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status );
//		        dispatcher.forward(request, response);
//				response.sendRedirect(EditPwdMan+"?editpwd_status=" + editpwd_status + "#EditPwd");
			}
		}else{
			
				editpwd_status = 4; //兩次新密碼不相同
				Msgs.put("error", "兩次新密碼不相同");
				request.setAttribute("Msgs", Msgs);

			
			   RequestDispatcher rd = request.getRequestDispatcher(StartEditPwd);
			rd.forward(request, response);
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status);
//			dispatcher.forward(request, response);					
		}}else{
			Msgs.put("error", "密碼最少8位數,並包含英數字母");
			request.setAttribute("Msgs", Msgs);
			RequestDispatcher rd = request.getRequestDispatcher(StartEditPwd);
			rd.forward(request, response);
		}
		
		
		
	}
	
	public void doEditPwdMan( HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		Password = request.getParameter("password");
		NewPassword = request.getParameter("newpassword");
		NewPassword2 = request.getParameter("newpassword2");
		HttpSession session = request.getSession();
		Map<String, String> Msgs = new HashMap<>();

		int editpwd_status = 0;

		String Emid = (String) session.getAttribute("Employee");
		
		System.out.println(Emid);
		
		
		
		JDBCEditPwd editpwd = new JDBCEditPwd();
		
		
		if(isPasswd(NewPassword) && isPasswd(NewPassword2)){
		if(editpwd.JDBCEditPwdVerify(Emid, Password)){
			if(editpwd.JDBCEditPwdComparsion(NewPassword, NewPassword2)){
				if(editpwd.JDBCEditPwdVerify2(Password, NewPassword)){
					if(editpwd.JDBCEditPasswd(Emid, NewPassword)){
						editpwd_status = 1; //更新成功
						Msgs.put("success", "修改密碼成功，請重新登入");
						request.setAttribute("Msgs", Msgs);
			            RequestDispatcher rd = request.getRequestDispatcher(loginPage);
						rd.forward(request, response);
//						response.sendRedirect("index.jsp?editpwd_status=" + editpwd_status + "#EditPwd");
					}else{
						editpwd_status = 2; //更新失敗
						Msgs.put("error", "修改密碼失敗");
						request.setAttribute("Msgs", Msgs);
			            RequestDispatcher rd = request.getRequestDispatcher(editPwdMan);
						rd.forward(request, response);
//						RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status );
//				        dispatcher.forward(request, response);
//						response.sendRedirect(EditPwdMan+"?editpwd_status=" + editpwd_status + "#EditPwd");
					}
				}else{
					editpwd_status = 3; //新舊密碼相同，錯誤
					Msgs.put("error", "新舊密碼相同");
					request.setAttribute("Msgs", Msgs);
		            RequestDispatcher rd = request.getRequestDispatcher(editPwdMan);
					rd.forward(request, response);
//					RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status);
//			        dispatcher.forward(request, response);		
					}
					
				
			}else{
				
				editpwd_status = 4; //兩次新密碼不相同
				Msgs.put("error", "兩次新密碼不相同");
				request.setAttribute("Msgs", Msgs);
	            
				
				RequestDispatcher rd = request.getRequestDispatcher(editPwdMan);
				rd.forward(request, response);
//				RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status);
//				dispatcher.forward(request, response);					
			}
				
			
		}else{
			editpwd_status = 5; //舊密碼錯誤
			Msgs.put("error", "舊密碼錯誤");
			request.setAttribute("Msgs", Msgs);
            RequestDispatcher rd = request.getRequestDispatcher(editPwdMan);
			rd.forward(request, response);
//			RequestDispatcher dispatcher = request.getRequestDispatcher(EditPwd + editpwd_status);
//			dispatcher.forward(request, response);					
		
		}}else{
			Msgs.put("error", "密碼最少8位數,並包含英數字母");
			request.setAttribute("Msgs", Msgs);
			RequestDispatcher rd = request.getRequestDispatcher(editPwdMan);
			rd.forward(request, response);
			
		}
		
		
	}
	
	
	public void doEditPwd( HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		Password = request.getParameter("password");
		NewPassword = request.getParameter("newpassword");
		NewPassword2 = request.getParameter("newpassword2");
		HttpSession session = request.getSession();
		Map<String, String> Msgs = new HashMap<>();

		int editpwd_status = 0;

		String Emid = (String) session.getAttribute("Employee");
		
		System.out.println(Emid);
		
		JDBCEditPwd editpwd = new JDBCEditPwd();
		System.out.println(NewPassword);
		System.out.println(NewPassword2);

		
		if(isPasswd(NewPassword) && isPasswd(NewPassword2)){
		if(editpwd.JDBCEditPwdVerify(Emid, Password)){
			if(editpwd.JDBCEditPwdComparsion(NewPassword, NewPassword2)){
				if(editpwd.JDBCEditPwdVerify2(Password, NewPassword)){
					if(editpwd.JDBCEditPasswd(Emid, NewPassword)){
						editpwd_status = 1; //更新成功
						Msgs.put("success", "修改密碼成功，請重新登入");
						request.setAttribute("Msgs", Msgs);
			            RequestDispatcher rd = request.getRequestDispatcher(loginPage);
						rd.forward(request, response);
//						response.sendRedirect("index.jsp?editpwd_status=" + editpwd_status + "#EditPwd");
					}else{
						editpwd_status = 2; //更新失敗
						Msgs.put("error", "修改密碼失敗");
						request.setAttribute("Msgs", Msgs);
			            RequestDispatcher rd = request.getRequestDispatcher(editPwd);
						rd.forward(request, response);
//						RequestDispatcher dispatcher = request.getRequestDispatcher(editPwdMan + editpwd_status );
//				        dispatcher.forward(request, response);
//						response.sendRedirect(EditPwdMan+"?editpwd_status=" + editpwd_status + "#EditPwd");
					}
				}else{
					editpwd_status = 3; //新舊密碼相同，錯誤
					Msgs.put("error", "新舊密碼相同");
					request.setAttribute("Msgs", Msgs);
		            RequestDispatcher rd = request.getRequestDispatcher(editPwd);
					rd.forward(request, response);
//					RequestDispatcher dispatcher = request.getRequestDispatcher(editPwdMan + editpwd_status);
//			        dispatcher.forward(request, response);			
					}
//					
				
			}else{
				
				Msgs.put("error", "兩次新密碼不相同");
				request.setAttribute("Msgs", Msgs);
	            RequestDispatcher rd = request.getRequestDispatcher(editPwd);
				rd.forward(request, response);
				
				editpwd_status = 4; //兩次新密碼不相同
//				RequestDispatcher dispatcher = request.getRequestDispatcher(editPwdMan + editpwd_status);
//				dispatcher.forward(request, response);					
			}
				
			
		}else{
			editpwd_status = 5; //舊密碼錯誤
			Msgs.put("error", "舊密碼錯誤");
			request.setAttribute("Msgs", Msgs);
            RequestDispatcher rd = request.getRequestDispatcher(editPwd);
			rd.forward(request, response);
			
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher(editPwdMan + editpwd_status);
//			dispatcher.forward(request, response);					
		
		}	
		}else{
			Msgs.put("error", "密碼最少8位數,並包含英數字母");
			request.setAttribute("Msgs", Msgs);
            RequestDispatcher rd = request.getRequestDispatcher(editPwd);
			rd.forward(request, response);
		}
		
		
		
	}
	
	
//	public static void main(String[] args){
//	EditPwdController test = new EditPwdController();
//	String emid ="admin";
//	String password = "admin";
//	System.out.print(test.VerifyEmployee(emid, password));
//	
//	
//}
}
