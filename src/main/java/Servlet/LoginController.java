package Servlet;


import static Utils.VaildateUtils.*;
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

import sun.rmi.runtime.Log;

import JDBC.JDBCLogin;
import Bean.Employee;


/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String Employee_ID;
	private String Password;
	String Admin_Page="/WEB-INF/Views/Admin/AdminHome.jsp";
	String Manager_Page="/WEB-INF/Views/Manager/ManagerHome.jsp";
	String Empolyee_Page="/WEB-INF/Views/Employee/EmployeeHome.jsp";
	String Login_Page ="/index.jsp";
	int status = -1;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
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
		ServletContext context = getServletContext();
		String link ;

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Employee_ID = request.getParameter("emid");
		Password = request.getParameter("password");
		System.out.println(request.getServletPath()+request.getServerPort());
		Map<String, String> errorMsgs = new HashMap<>();
		Map<String, String> loginMsgs = new HashMap<>();

		retrieveLoginController(request, errorMsgs);	
		if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            RequestDispatcher rd = request.getRequestDispatcher(Login_Page);
			rd.forward(request, response);;
        }
		
		JDBCLogin db = new JDBCLogin();
		status = db.VerifyEmployee(Employee_ID, Password);

		
		
		
		if (status == 0){
			int autid = 0;	
			request.setAttribute("Emid", Employee_ID);
			session.setAttribute("Employee",Employee_ID);
			session.setAttribute("Autid", autid);
			 link = this.Admin_Page;
			
//			RequestDispatcher rd = request.getRequestDispatcher(link);
//			rd.forward(request, response);
		
		}else if (status == 1){
			int autid = 1; //主管
			String name = db.getName(Employee_ID);
			
			request.setAttribute("Emid", Employee_ID);
			session.setAttribute("Employee",Employee_ID);
			session.setAttribute("Autid", autid);
			session.setAttribute("Name", name);
			link = this.Manager_Page;
		    request.setAttribute("loginMsgs", loginMsgs);

//			RequestDispatcher rd = request.getRequestDispatcher(link);
//			rd.forward(request, response);
			
		}else if (status == 2){
			int autid = 2; //員工
			String name = db.getName(Employee_ID);
			
			request.setAttribute("Emid", Employee_ID);
			session.setAttribute("Employee",Employee_ID);
			session.setAttribute("Autid", autid);
			session.setAttribute("Name", name);
			link = this.Empolyee_Page;
	        request.setAttribute("loginMsgs", loginMsgs);

//			RequestDispatcher rd = request.getRequestDispatcher(link);
//			rd.forward(request, response); 
			
		}else if (status ==3){
			int autid = 3; //未設定
			String name = db.getName(Employee_ID);
			
			request.setAttribute("Emid", Employee_ID);
			session.setAttribute("Employee",Employee_ID);
			session.setAttribute("Autid", autid);
			session.setAttribute("Name", name);
			request.setAttribute("status", status);
			 link = this.Login_Page;
			if(status == 3){
				loginMsgs.put("error", "很抱歉，您的身分尚未被系統管理員設定");
			}
	        request.setAttribute("loginMsgs", loginMsgs);

//			session.setAttribute("login_status", status);
//			response.sendRedirect("index.jsp?status=" + status + "#Login");
		}
		
		else {
			if(status == 4){
				loginMsgs.put("error", "員工編號或密碼錯誤");
			}else if (status == 5){
				loginMsgs.put("error", "無此員工編號");
			}else if (status == 6){
				loginMsgs.put("error", "系統發生錯誤");
			}
			 link = this.Login_Page;

//		    request.setAttribute("loginMsgs", loginMsgs);
//
//			request.setAttribute("status", status);
//			response.sendRedirect("index.jsp?status=" + status + "#Login");
		}
		
        request.setAttribute("loginMsgs", loginMsgs);
		RequestDispatcher rd = request.getRequestDispatcher(link);
		rd.forward(request, response);

	}
	private void retrieveLoginController(HttpServletRequest request,Map<String,String> errorMsgs)
	{

		String Employee_ID = request.getParameter("emid");
		if(isBlank(Employee_ID)){
			errorMsgs.put("emid","請填寫員工編號");
		}
		
		String Password = request.getParameter("password");		
		if(isBlank(Password)){
			errorMsgs.put("password","請填寫密碼");
		}
		
		
	}
	
//	public static void main(String[] args){
//		JDBCLogin test = new JDBCLogin();
//		String emid ="a";
//		String password = "admin";
//		System.out.print(test.VerifyEmployee(emid, password));
//		
//		
//	}
	
	

}
