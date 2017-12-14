package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Bean.EmpHeader;
import Bean.Week;
import Bean.WorkHour;
import Bean.WorkHourDetail;
import JDBC.EmpHeaderDAO;
import JDBC.WeekDAO;
import JDBC.WorkHourDAO;
import JDBC.WorkHourDetailDAO;

/**
 * Servlet implementation class SearchHr
 */
public class SearchHr extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_SEARCHHR = "/WEB-INF/Views/Employee/SearchHr.jsp";
	private static final String PAGE_SEARCHHR_DETAIL ="/WEB-INF/Views/Employee/SearchHrDetail.jsp";
	private static final Gson GSON = new Gson();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHr() {
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
		//PrintWriter out = response.getWriter();
	
		
		String action = request.getParameter("action");
		String path = null;
		switch(action == null ? "query" : action)
		{
			case "view":
				path = doFindAction(request);	
				break;
			case "viewdetail":
				path = doViewDetail(request);
				break;
			case "getmonth":
				path = getExistMonth(request);
				response.getWriter().println(path);
				break;
			case "filter":
				path = doFilter(request);
				break;
			case "whichpageSH":
				path = doFindAction(request);
				break;
			case "query":
			default:
				path = doFindAction(request);
		}
		if(!(action.equals("getmonth")))
		{
			
		
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
			
		
		
		
		
	}
	private String doFindAction(HttpServletRequest request){
		List<EmpHeader> empheader = new ArrayList<EmpHeader>();
		String emid =  (String)request.getSession().getAttribute("Employee");
		try{
			empheader = new EmpHeaderDAO().findCompleteHeader(emid);				
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		request.setAttribute("empheader", empheader);		
		List<String> existyear = new ArrayList<>();
		try{
			existyear = new WorkHourDAO().findExistYear(emid);
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("existyear", existyear);	
		
	
		
		return PAGE_SEARCHHR;
	}
	
	private String doViewDetail( HttpServletRequest request ){
		WorkHour workhour = new WorkHour();
		Week week = new Week();
		List<WorkHourDetail> workhourdetail = new ArrayList<WorkHourDetail>();
		String emid =  (String)request.getSession().getAttribute("Employee");
		String hsid = (String) request.getParameter("hsid");
		System.out.println(hsid);
		try{
			week = new WeekDAO().getHoli(hsid);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			workhour = new WorkHourDAO().findworkhour(emid,hsid);
							
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		try{
			workhourdetail = new WorkHourDetailDAO().findbyemidhsid(hsid, emid);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.setAttribute("workhourdetail", workhourdetail);
		request.setAttribute("hsid", hsid);
		request.setAttribute("week", week);
		request.setAttribute("workhour", workhour);
		
		
		
		return PAGE_SEARCHHR_DETAIL;
	}
	private String getExistMonth(HttpServletRequest request){
		String emid =  (String)request.getSession().getAttribute("Employee");
		String year = request.getParameter("existyear");
		System.out.println(year);
		List<String> existmonth = new ArrayList<>();
		try{
			existmonth = new WorkHourDAO().findExistMonth(year,emid);
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(existmonth.size());
		String monthjson = GSON.toJson(existmonth);
		System.out.println(monthjson);
		return monthjson;
	}
	
	private String doFilter(HttpServletRequest request){
		List<EmpHeader> empheader = new ArrayList<EmpHeader>();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String yearmonth = year+month+"%";
		System.out.println(yearmonth);
		String emid =  (String)request.getSession().getAttribute("Employee");
		try{
			empheader = new EmpHeaderDAO().findCompleteHeader(emid,yearmonth);				
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		
		request.setAttribute("empheader", empheader);		
		List<String> existyear = new ArrayList<>();
		try{
			existyear = new WorkHourDAO().findExistYear(emid);
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("existyear", existyear);	
		
		return PAGE_SEARCHHR;
	}
}
