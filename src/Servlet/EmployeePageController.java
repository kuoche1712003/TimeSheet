package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmployeePageController
 */

public class EmployeePageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 
	 String EmployeeHome = "/WEB-INF/Views/Employee/EmployeeHome.jsp";
	 String WriteHr = "/WriteHr";
	 String Modifyhr = "/Modifyhr";
	 String SearchHr = "/SearchHr";
	 String EditPwd = "/WEB-INF/Views/Employee/EditPwd.jsp";

	 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeePageController() {
        super();
       
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String contextpath =  request.getContextPath();
		 String path = null;
	        String action =	(String) request.getParameter("action");
	        switch (action) {
	            // 轉交至新增頁面
	        	case "EmployeeHome":
	        		path = EmployeeHome;
	        		break;
	            case "WriteHr":
	                path = WriteHr;
	                request.setAttribute("action", "view");
	                break;
	            case "Modifyhr":
	                path = Modifyhr;
	                request.setAttribute("action", "view");
	                break;
	            case "SearchHr":
	                path = SearchHr;
	                request.setAttribute("action", "view");
	                break;
	            case "EditPwd":
	            	path = EditPwd;
	            	break;
	           
	        }    
	         RequestDispatcher dispatcher = request.getRequestDispatcher(path);
	         dispatcher.forward(request, response);    
	        
	        
	 
	}

}
