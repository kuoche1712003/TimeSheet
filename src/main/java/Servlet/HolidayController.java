package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import Bean.Holiday;
import JDBC.JDBCHoliday;

import static Utils.VaildateUtils.*;


/**
 * Servlet implementation class HolidayController
 */
public class HolidayController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    String AddYearHd = "/WEB-INF/Views/Admin/AddYearHd.jsp";
    String AdminHome="/WEB-INF/Views/Admin/AdminHome.jsp";
    String AddHd = "/WEB-INF/Views/Admin/AddHd.jsp";
    String AlterHd= "/WEB-INF/Views/Admin/AlterHd.jsp";
    String AlterHdWrite = "/WEB-INF/Views/Admin/AlterHdWrite.jsp";

	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HolidayController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String path = null;
		String action = (String) request.getParameter("action");
		System.out.println(action);
		switch (action) {
        // 轉交至新增頁面
        case "addyearhd":
            path = doAddYearHd(request, response);
            break;
        case "addhd":
            path = doAddHd(request, response);
            break;
        case "alterhd":
            path = doAlterHd(request, response);
            break;
        case "alterhdwrite":
        	path = doUpdateHdWrite(request,response);
        	break;
     
            
      
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
		
	}

	
	private String doAddYearHd(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> errorMsgs = new HashMap<>();
        Map<String, String> Msgs = new HashMap<>();

		String year = request.getParameter("year");
		System.out.println(year);
		JDBCHoliday holiday = new JDBCHoliday();

		if(isBlank(year)){
			
            errorMsgs.put("prestatus", "年度不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
//            request.setAttribute("year", year);
            return AddYearHd;
			
		}else if(holiday.isYearExist(year)){
			errorMsgs.put("prestatus", "該年度之年度假日已新增");
			request.setAttribute("errorMsgs", errorMsgs);
//            request.setAttribute("year", year);
            return AddYearHd;
		}else if(!holiday.AddYearHd(year)){
			 errorMsgs.put("prestatus", "新增年度假日失敗");
			 request.setAttribute("errorMsgs", errorMsgs);
				return AddYearHd;
			
		}else{
			Msgs.put("status", "新增年度假日成功");
			request.setAttribute("Msgs", Msgs);
			return AddYearHd;
           
		}
		

	}

	
	
	
	
	private String doAddHd(HttpServletRequest request, HttpServletResponse response){
		
		Map<String, String> errorMsgs = new HashMap<>();
        Map<String, String> Msgs = new HashMap<>();

		String date = request.getParameter("hddate");
		String type = request.getParameter("hdtype");
		String hour = request.getParameter("hdhour");
//		int type =  Integer.parseInt( request.getParameter("hd_type")) ;
//		int hour =  Integer.parseInt( request.getParameter("hd_hour")) ;
//		
		System.out.println(date);
		System.out.println(type);
		System.out.println(hour);
		
		JDBCHoliday holiday2 = new JDBCHoliday();

//		String hour_string = Integer.toString(hour);
		if(isBlank(date)){
			
            errorMsgs.put("error", "日期不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
//            request.setAttribute("year", date);
			return AddHd;	

			
		}else if (isBlank(type)){
			errorMsgs.put("error", "假別不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
			return AddHd;	
			
		}else if (isBlank(hour)){
			errorMsgs.put("error", "時數不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
			return AddHd;	
			
		}else if(!isRightHr(hour)){
			errorMsgs.put("error", "時數須介於0~8小時");
			request.setAttribute("errorMsgs", errorMsgs);
			return AddHd;	

			
		}else if(holiday2.AddHd_1(date)){
			
			errorMsgs.put("error", "該例假日已有資料");
			request.setAttribute("errorMsgs", errorMsgs);
			return AddHd;	

            

		}
		int type_int =  Integer.parseInt(type) ;
		int hour_int =  Integer.parseInt(hour) ;
		 if(holiday2.AddHd(date, type_int, hour_int)) {
			
            Msgs.put("status", "新增例假日成功");
			request.setAttribute("Msgs", Msgs);
			return AddHd;	

		}else {
			
			errorMsgs.put("error", "新增假日時遭遇錯誤");
			request.setAttribute("errorMsgs", errorMsgs);
			return AddHd;
		}
		

	}
	
	
	
	private String doAlterHd(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> errorMsgs = new HashMap<>();

		String date = request.getParameter("hddate");
		JDBCHoliday holiday = new JDBCHoliday();
		if(isBlank(date)){
			
            errorMsgs.put("error", "日期不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
//            request.setAttribute("year", date);
			return AlterHd;	
			
		}else if(holiday.AlterHdCheck(date)){
			errorMsgs.put("error", "該日期無資料");
			request.setAttribute("errorMsgs", errorMsgs);
			return AlterHd;	
			
		}else {
			
			Holiday hd = holiday.AlterHd(date);
			System.out.println(hd.getDateString());
			System.out.println(hd.getHdhr());
			System.out.println(hd.getHdtype());
			request.setAttribute("hd", hd);
			return AlterHdWrite;
		}

		
		
	}
	
	private String doUpdateHdWrite(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> errorMsgs = new HashMap<>();
        Map<String, String> Msgs = new HashMap<>();

		String date = request.getParameter("hddate");
		String type = request.getParameter("hdtype");
		String hour = request.getParameter("hdhour");
//		int type =  Integer.parseInt( request.getParameter("hd_type")) ;
//		int hour =  Integer.parseInt( request.getParameter("hd_hour")) ;
//		
		System.out.println(date);
		System.out.println(type);
		System.out.println(hour);
		
		JDBCHoliday holiday2 = new JDBCHoliday();
		Holiday hd = new Holiday();
		int type_int =  Integer.parseInt(type) ;
		int hour_int =  Integer.parseInt(hour) ;
		hd.setDateString(date);
		hd.setHdhr(hour_int);
		hd.setHdtype(type_int);
		
//		String hour_string = Integer.toString(hour);
		if(isBlank(date)){
			
            errorMsgs.put("error", "日期不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
			request.setAttribute("hd", hd);
		
//            request.setAttribute("year", date);
			return AlterHdWrite;	

			
		}else if (isBlank(type)){
			errorMsgs.put("error", "假別不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
			request.setAttribute("hd", hd);

			return AlterHdWrite;	
			
		}else if (isBlank(hour)){
			errorMsgs.put("error", "時數不得為空");
			request.setAttribute("errorMsgs", errorMsgs);
			request.setAttribute("hd", hd);

			return AlterHdWrite;	
			
		}else if(!isRightHr(hour)){
			errorMsgs.put("error", "時數須介於0~8小時");
			request.setAttribute("errorMsgs", errorMsgs);
			request.setAttribute("hd", hd);

			return AlterHdWrite;	

			
		}else{
			
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			
			System.out.println("doUpdateHdWrite "+ date);
			System.out.println("doUpdateHdWrite "+ hour_int);
			System.out.println("doUpdateHdWrite "+ type_int);

			
			JDBCHoliday hdd = new JDBCHoliday();
			hdd.AlterHdWrite(hd);
			Msgs.put("success", "修改例假日成功");
			request.setAttribute("Msgs", Msgs);
			return AdminHome;
		}
		
	}
	
	
}
