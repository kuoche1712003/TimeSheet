package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminPageController
 */
public class AdminPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     String AddYearHd = "/WEB-INF/Views/Admin/AddYearHd.jsp";
     String AddHd = "/WEB-INF/Views/Admin/AddHd.jsp";
     String AlterHd = "/WEB-INF/Views/Admin/AlterHd.jsp";
     String AddEmployee = "/WEB-INF/Views/Admin/AddEmployee.jsp";
     String SearchEmployee = "/WEB-INF/Views/Admin/SearchEmployee.jsp";
     String SearchSetAuthority = "/WEB-INF/Views/Admin/SearchSetAuthority.jsp";
     String AdminHome="/WEB-INF/Views/Admin/AdminHome.jsp";
     

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPageController() {
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
		
		 	String path = null;
	        String action =	(String) request.getParameter("action");
	        switch (action) {
	            // 轉交至新增頁面
	        	case "AdminHome":
	        		path = AdminHome;
	        		break;
	            case "AddYearHd":
	                path = AddYearHd;
	                break;
	            case "AddHd":
	                path = AddHd;
	                break;
	            case "AlterHd":
	                path = AlterHd;
	                break;
	            case "AddEmployee":
	                path = AddEmployee;
	                break;
	            case "SearchEmployee":
	                path = SearchEmployee ;
	                break;
	            case "SearchSetAuthority":
	                path = SearchSetAuthority;
	                break;
	            case "returnEmployee":
	            	path = SearchEmployee;
	        }    
	         RequestDispatcher dispatcher = request.getRequestDispatcher(path);
	         dispatcher.forward(request, response);    
	        
	        
	     
	}

}
