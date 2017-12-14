package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutContriller
 */
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		   
		//logout sample
			response.setContentType("text/html");  
//           PrintWriter out=response.getWriter();  
			int status = 7;
			 HttpSession session=request.getSession();  
			 String test = (String) session.getAttribute("Employee");
		        System.out.println(test);
	          session.invalidate();
	          
//	          String test1 = (String) session.getAttribute("Employee");
//	          System.out.println(test1);
			response.sendRedirect("index.jsp?status=" + status + "#Logout");
//           request.getRequestDispatcher("login.jsp").include(request, response);  
             
            
             
//           out.print("You are successfully logged out!");  
//             
//           out.close();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//	}

}
