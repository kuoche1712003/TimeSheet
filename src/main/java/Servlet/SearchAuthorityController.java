package Servlet;

import static Utils.VaildateUtils.isBlank;
import static Utils.VaildateUtils.isEmail;
import static Utils.VaildateUtils.isId;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.Employee;
import JDBC.JDBCSearchSetAuthority;



/**
 * Servlet implementation class EditPwdController
 */
public class SearchAuthorityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_LIST_EMP = "/WEB-INF/Views/Admin/SearchSetAuthority.jsp";
	private static final String PAGE_UPDATE_EMP = "/WEB-INF/Views/Admin/SetAuthority.jsp";
       

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAuthorityController() {
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
	 * @return 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		ServletContext context = getServletContext();
		request.setCharacterEncoding("utf-8");
		String path = null;
		String action = (String) request.getParameter("action");
		System.out.print(action);
		switch (action) {
        // 轉交至更新頁面
        case "preUpdate":
            path = doFindOneAction(request);
            break;
        // 執行更新
        case "update":
            path = doUpdateAction(request);
            break;
         // 執行查詢
        case "query":
        	path = doFindAction(request);
            break;
      
	   }
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);

	}

	private String doFindAction(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		
		Map<String, String> errorMsgs = new HashMap<>();
    	Employee emp = retrieveEmployee(request,errorMsgs);
    	System.out.print(errorMsgs);
    	if (!errorMsgs.isEmpty()) {
            request.setAttribute("errorMsgs", errorMsgs);
            request.setAttribute("employee", emp);

            return PAGE_LIST_EMP;
        }

        /*************************** 開始查詢資料 ***************************/
 
		String Emid = request.getParameter("search_authority");
		System.out.print(Emid);
		
			String name = request.getParameter("search_authority");
			String id = request.getParameter("search_authority");
			Integer autid = 4;
			if( "主管".equals(request.getParameter("search_authority")) || "主".equals(request.getParameter("search_authority")) || "管".equals(request.getParameter("search_authority"))){
				autid = 1;
			}else if("員工".equals(request.getParameter("search_authority")) || "員".equals(request.getParameter("search_authority")) || "工".equals(request.getParameter("search_authority"))){
				autid = 2;
			}
			else if("未設定".equals(request.getParameter("search_authority")) || "未".equals(request.getParameter("search_authority"))){
				autid = 3;
			}
			
			request.setAttribute("Emid",Emid);
			request.setAttribute("name",name);
			request.setAttribute("id",id);
			request.setAttribute("autid",autid);
		    /*************************** 開始查詢資料 ***************************/
			List<Employee> emps = new JDBCSearchSetAuthority().findAll(Emid, name, id, autid);
		    request.setAttribute("empList", emps);
		    System.out.print(emps);
        
        return PAGE_LIST_EMP;
		
	}

	private String doUpdateAction(HttpServletRequest request) throws UnsupportedEncodingException {
		 Map<String, String> Msgs = new HashMap<>();
		request.setCharacterEncoding("utf-8");
		String emid = request.getParameter("Emid");
		String autid1 = request.getParameter("authority");
		Integer autid = Integer.parseInt(autid1);
		
		JDBCSearchSetAuthority setAuth = new JDBCSearchSetAuthority();
		int editAutid_status = setAuth.updateAutid(emid, autid);
		System.out.print("================"+editAutid_status);
		//進行更新
		if(editAutid_status == 1){
			setAuth.updateAutid(emid, autid);
			 Msgs.put("success", "修改權限成功");
             request.setAttribute("Msgs", Msgs);
			 return PAGE_LIST_EMP;
		}else{
			 return PAGE_UPDATE_EMP;
		}
	}

	private String doFindOneAction(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		String emid = request.getParameter("emid");
		System.out.print(emid);
        /*************************** 開始查詢資料 ***************************/
        Employee empVO = new JDBCSearchSetAuthority().findByKeyWords(emid);
        request.setAttribute("empVO", empVO);
        return PAGE_UPDATE_EMP;
	}
	

    private Employee retrieveEmployee(HttpServletRequest request, final Map<String, String> errorMsgs) {
    	
    	Employee employee = new Employee();
    	
    	 String test = request.getParameter("search_authority");
         if (isBlank(test)) {
             errorMsgs.put("Emid", "檢索關鍵字不能為空值");
         } else {
        	 employee.setEmid("Emid".trim());

         }
    	return employee;
    }
	
	


}
