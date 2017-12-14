package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import static Utils.VaildateUtils.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Map;

import Bean.Employee;
import JDBC.JDBCEmployee;
import JDBC.JDBCSearchSetAuthority;
import Utils.AES;
import Utils.Link;

/**
 * Servlet implementation class AddEmployeeController
 */
public class EmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String name;
	String gender;
	String email;
	String id;
    String AdminHome="/WEB-INF/Views/Admin/AdminHome.jsp";
	String AddEmployee = "/WEB-INF/Views/Admin/AddEmployee.jsp";
    String SearchEmployee = "/WEB-INF/Views/Admin/SearchEmployee.jsp";
    String AlterEmployee = "/WEB-INF/Views/Admin/AlterEmployee.jsp";
    String StartEditPwd = "/WEB-INF/Views/Employee/StartEditPwd.jsp";
    String LoginPage = "index.jsp";
    
	JDBCEmployee emp = new JDBCEmployee();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		PrintWriter out =  response.getWriter();
		HttpSession session = request.getSession();
		String path = null;
		int port = request.getServerPort();
		String action = (String) request.getParameter("action");
//		System.out.print(action);
		switch (action) {
        // 轉交至新增
        case "addemp":
        	path = doAddAction(request, response,port);
			break;
      // query
        case "searemp":
            path = SearchEmployee(request);
            break;
        case "updatemp":
            path = doUpdateAction(request, response);
            break;
        case "verify":
        	path  = doVerify(request);
        	break;
     
      
	}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
		
//		if(action!="updatemp")
//		{
//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//	        dispatcher.forward(request, response);
//		}
		

	}


        private String doUpdateAction(HttpServletRequest request, HttpServletResponse response) {
        	
//        	request.setCharacterEncoding("UTF-8");
//        	response.setCharacterEncoding("UTF-8");
        	Map<String, String> errorMsgs = new HashMap<>();
            Map<String, String> Msgs = new HashMap<>();

            /***************** 接收請求參數 並做 輸入格式的錯誤處理 *****************/
            Employee employee = retrieveEmployee(request, errorMsgs);
        	String emid = request.getParameter("emid");
        	
        	JDBCEmployee em = new JDBCEmployee();
        	
        	if(!isEmid(emid)){
        		errorMsgs.put("error", "員工編號格式錯誤");
        	}else if (em.isEmidExist(emid)){
        		errorMsgs.put("error", "無此員工編號");        		
        	}
        	
        	employee.setEmid(emid.trim());

        	
            if (!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                request.setAttribute("empVO", employee);
                return AlterEmployee;
            }
            

            /*************************** 開始修改資料 ***************************/
    		emp.update(employee);
    		 Msgs.put("success", "員工資料修改成功");
             request.setAttribute("Msgs", Msgs);
    		
			return AdminHome;
	}

        
        
		private String SearchEmployee(HttpServletRequest request)   {
//			request.setCharacterEncoding("UTF-8");
			String emid = request.getParameter("search_employee");
			JDBCEmployee em = new JDBCEmployee();
        	Map<String, String> errorMsgs = new HashMap<>();
        	System.out.println(emid);

        	if(!isEmid(emid)){
        		errorMsgs.put("error", "員工編號格式錯誤");
        	}else if (em.isEmidExist(emid)){
        		errorMsgs.put("error", "無此員工編號");        		
        	}
        	 if (!errorMsgs.isEmpty()) {
                 request.setAttribute("errorMsgs", errorMsgs);
                 return SearchEmployee;
             }
        	 
			System.out.print(emid);
	        /*************************** 開始查詢資料 ***************************/
	        Employee empVO = new JDBCEmployee().findByPrimaryKey(emid);
	        System.out.println(empVO.getName());
	        System.out.println(empVO.getGender());
	        request.setAttribute("empVO", empVO);
	        return AlterEmployee;
		}
		 
	

		/*************************** 開始新增資料 
		 * @throws Exception ***************************/

//	
    private String doAddAction(HttpServletRequest request, HttpServletResponse response,int port)   {
        Map<String, String> errorMsgs = new HashMap<>();
        Map<String, String> Msgs = new HashMap<>();

    	Employee employee = retrieveEmployee(request,errorMsgs);
    	
    	 if (!errorMsgs.isEmpty()) {
             request.setAttribute("errorMsgs", errorMsgs);
             // 含有輸入格式錯誤的empVO物件，也存入 HttpServletRequest
             request.setAttribute("employee", employee);
             // 將頁面轉交回原本新增頁面
             
             return AddEmployee;
//             RequestDispatcher dispatcher = request.getRequestDispatcher(AddEmployee);
//	         dispatcher.forward(request, response);    
         }

         /*************************** 開始新增資料 ***************************/
    	 boolean status =  emp.insert(employee,port);
    
    	
    	
    	 
    	 if(status){
    		 Msgs.put("success", "員工新增成功");
             request.setAttribute("Msgs", Msgs);

    		 return AdminHome;
//    		 RequestDispatcher dispatcher = request.getRequestDispatcher(AdminHome);
//	         dispatcher.forward(request, response);
	         
    		 
    	 }else{
    		 errorMsgs.put("error", "員工新增失敗");
             request.setAttribute("errorMsgs", errorMsgs);

    		 return AddEmployee;
//    		 RequestDispatcher dispatcher = request.getRequestDispatcher(AddEmployee);
//	         dispatcher.forward(request, response);
//	         int employee_status =1;
//	         request.setAttribute("employee_status", employee_status);
    	 }
    	 
    	 
    	 
//    	 return AdminHome;
//         empService.insert(empVO);
//         return doFindAction(request);
    }
    
    private Employee retrieveEmployee(HttpServletRequest request, Map<String, String> errorMsgs) {
    	try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Employee employee = new Employee();
    	
    
    	
    	 String name = null;
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	 System.out.println("name="+name);
         if (isBlank(name)) {
             errorMsgs.put("name", "請填寫員工姓名");
         } else {
        	 employee.setName(name.trim());

         }
         
         
         
         
         String email = request.getParameter("email");
         if(isEmail(email) && !isBlank(email)){
        	 employee.setEmail(email.trim());
         } else{
        	 errorMsgs.put("email", "請填寫正確格式的電子郵件");

         }
         
//         int gender = Integer.parseInt(request.getParameter("gender"));
         String gender = request.getParameter("gender");

         if( gender.equals(1) || gender.equals(2) || !isBlank(gender) ){
        	 employee.setGender(Integer.parseInt(request.getParameter("gender")));
         } else{
        	 errorMsgs.put("gender", "請填寫性別");
         }
         
         String id = request.getParameter("id");
         if(isId(id) && !isBlank(id)){
        	 employee.setId(id);
         }else{
        	 errorMsgs.put("id", "請填寫正確的身分證字號格式");

         }
         
    	System.out.println(employee);
    	return employee;

    	
    }
    
    
    public String doVerify(HttpServletRequest request) {
    	String verify_link = request.getParameter("link");
    	Link link = new Link();
    	try {

			if(link.LinkVerify(verify_link)){
//				String test = StartEditPwd +"?emid="+ aes.Encrypt(link.getEmid(), "iisiiisiiisiiisi");
				String test = StartEditPwd +"?emid="+ link.getEmid();
				System.out.println(test);
				return test;
			}else{
				return LoginPage;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return LoginPage;
    }
    
   
	

}
