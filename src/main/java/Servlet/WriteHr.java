package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static Utils.VaildateUtils.*;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;

import JDBC.*;
import Bean.*;
/**
 * Servlet implementation class WriterHr
 */
@WebServlet(urlPatterns={"/WriteHr"})
public class WriteHr extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private static final String PAGE_UPDATE_WriteHr = "/WEB-INF/Views/Employee/WriteHr.jsp";
	private static final String PAGE_EMPLOYEE_HOME = "/WEB-INF/Views/Employee/EmployeeHome.jsp";
	private static final Gson GSON = new Gson();
	/**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("action");
		String path = null;
		switch(action == null ? "query" : action)
		{
			case "view":
				path = doFindAction(request);	
				break;
			case "temporay":
				path = doTemporay(request);
				break;
			case "submit":
				path = doSubmit(request);
				
				break;
			case "query":
			default:
				path = doFindAction(request);
		}
//		if(action.equals("temporay"))
//		{
//			
//			PrintWriter out = response.getWriter();
//			
//			out.println(path);
//			
//		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
//		}
			
	}
	private String doFindAction(HttpServletRequest request){
		WorkHour workhour = new WorkHour();
		Week week = new Week();
		List<WorkHourDetail> workhourdetail = new ArrayList<WorkHourDetail>();
		String emid =  (String)request.getSession().getAttribute("Employee");
		try{
			workhour = new WorkHourDAO().findworkhour(emid);
							
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
			
		if(workhour.getEmid()==null)
		{
			try{
				new WorkHourDAO().insertworkhour(emid);
			}catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			try{
				workhour = new WorkHourDAO().findworkhour(emid);	
			}catch(Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		if( workhour.getStdes() == 2 || workhour.getStdes() == 3 || workhour.getStdes() == 1)
		{
			request.setAttribute("complete", "本周工時已送出");
			return PAGE_EMPLOYEE_HOME;
		}
		request.setAttribute("workhour", workhour);
		try{
			week = new WeekDAO().getHoli();
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("week", week);
		
		try{
			workhourdetail = new WorkHourDetailDAO().findbyemidhsid(workhour.getHsid(), workhour.getEmid());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.setAttribute("workhourdetail", workhourdetail);
		
	
		
		return PAGE_UPDATE_WriteHr;
	}
	
	private String doTemporay(HttpServletRequest request) throws UnsupportedEncodingException{
		WorkHour workhour = new WorkHour();
		Week week = new Week();
		String emid =  (String)request.getSession().getAttribute("Employee");
		List<WorkHourDetailJSON> workhourdetailjson = GSON.fromJson(request.getParameter("AlterHr"), new TypeToken<List<WorkHourDetailJSON>>() {}.getType()); 
		List<WorkHourDetailJSON> newworkhourdetailjson = GSON.fromJson(request.getParameter("WriteHr"), new TypeToken<List<WorkHourDetailJSON>>() {}.getType()); 
		Set<String> isnull = new HashSet<String>();
		List<WorkHourDetail> workhourdetail = new ArrayList<WorkHourDetail>();
		List<WorkHourDetail> newworkhourdetail = new ArrayList<WorkHourDetail>();
		List<Map<String, String>> errorMsgsList = new ArrayList<>();
		List<Map<String, String>> newerrorMsgsList = new ArrayList<>();
		int errorcnt = 0;
		
		try{
			workhour = new WorkHourDAO().findworkhour(emid);
							
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		try{
			week = new WeekDAO().getHoli();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		//刪除資料
		if( request.getParameterValues("delete")!=null )
		{
			String [] delete = request.getParameterValues("delete");
			try{
				for(String hscontent : delete)
				{
					new WorkHourDetailDAO().delete(emid, hscontent);
				}
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("errorMsg", "刪除失敗");
				return PAGE_UPDATE_WriteHr;
			}
		}
		
		//檢查修改資料格式
		
		for (WorkHourDetailJSON json : workhourdetailjson) {
			WorkHourDetail whd = new WorkHourDetail();
			Map<String, String> errorMsgs = new HashMap<>();
			
			String hscontent = json.getHscontent();
			
			if(isBlank(hscontent)){
				errorMsgs.put("hscontent", "不可為空");
			}else{
				whd.setHscontent(hscontent.trim());
			}
			
			String hsdeid = json.getHsdeid();
			if(isBlank(hsdeid)){
				errorMsgs.put("hsdeid", "請填寫週別");
			}else{
				whd.setHsdeid(hsdeid.trim());
			}
			
			String  alteremid = json.getEmid();
			if(isBlank(alteremid)){
				errorMsgs.put("emid", "請填寫員工編號");
			}else{
				whd.setEmid(alteremid.trim());
			}
			
			String orihscontent = json.getOrihscontent();
			if(isBlank(orihscontent)){
				errorMsgs.put("orihscontent", "請填寫工作內容");
			}else{
				whd.setOrihscontent(orihscontent.trim());
			}
			
			String monhr = json.getMonhr();
			if( !isNumber(monhr) ){
				errorMsgs.put("monhr", "請填寫數字");
			}else if( isBlank(monhr) ){
				whd.setMonhr(0);
			}else{
				whd.setMonhr(Integer.parseInt(json.getMonhr()));
			}
			
			String tueshr = json.getTueshr();
			if( !isNumber(tueshr) ){
				errorMsgs.put("tueshr", "請填寫數字");
			}else if( isBlank(tueshr) ){
				whd.setTueshr(0);
			}else{
				whd.setTueshr(Integer.parseInt(json.getTueshr()));
			}
			
			String wedhr = json.getWedhr();
			if( !isNumber(wedhr) ){
				errorMsgs.put("wedhr", "請填寫數字");
			}else if( isBlank(wedhr) ){
				whd.setWedhr(0);
			}else{
				whd.setWedhr(Integer.parseInt(json.getWedhr()));
			}
			
			String thurhr = json.getThurhr();
			if( !isNumber(thurhr) ){
				errorMsgs.put("thur", "請填寫數字");
			}else if( isBlank(thurhr) ){
				whd.setThurhr(0);
			}else{
				whd.setThurhr(Integer.parseInt(json.getThurhr()));
			}
			
			String frihr = json.getFrihr();
			if( !isNumber(frihr) ){
				errorMsgs.put("frihr", "請填寫數字");
			}else if( isBlank(frihr) ){
				whd.setFrihr(0);
			}else{
				whd.setFrihr(Integer.parseInt(json.getFrihr()));
			}
			
			String sathr = json.getSathr();
			if( !isNumber(sathr) ){
				errorMsgs.put("sathr", "請填寫數字");
			}else if( isBlank(sathr) ){
				whd.setSathr(0);
			}else{
				whd.setSathr(Integer.parseInt(json.getSathr()));
			}
			
			String sunhr = json.getSunhr();
			if( !isNumber(sunhr) ){
				errorMsgs.put("sun", "請填寫數字");
			}else if( isBlank(sunhr) ){
				whd.setSunhr(0);
			}else{
				whd.setSunhr(Integer.parseInt(json.getSunhr()));
			}
			
			String monoverhr = json.getMonoverhr();
			if( !isNumber(monoverhr) ){
				errorMsgs.put("monoverhr", "請填寫數字");
			}else if( isBlank(monoverhr) ){
				whd.setMonoverhr(0);
			}else{
				whd.setMonoverhr(Integer.parseInt(json.getMonoverhr()));
			}
			
			String tuesoverhr = json.getTuesoverhr();
			if( !isNumber(tuesoverhr) ){
				errorMsgs.put("tuesoverhr", "請填寫數字");
			}else if( isBlank(tuesoverhr) ){
				whd.setTuesoverhr(0);
			}else{
				whd.setTuesoverhr(Integer.parseInt(json.getTuesoverhr()));
			}
			
			String wedoverhr = json.getWedoverhr();
			if( !isNumber(wedoverhr) ){
				errorMsgs.put("wedoverhr", "請填寫數字");
			}else if( isBlank(wedoverhr) ){
				whd.setWedoverhr(0);
			}else{
				whd.setWedoverhr(Integer.parseInt(json.getWedoverhr()));
			}
			
			String thuroverhr = json.getThuroverhr();
			if( !isNumber(thuroverhr) ){
				errorMsgs.put("thuroverhr", "請填寫數字");
			}else if( isBlank(thuroverhr) ){
				whd.setThuroverhr(0);
			}else{
				whd.setThuroverhr(Integer.parseInt(json.getThuroverhr()));
			}
			
			String frioverhr = json.getFrioverhr();
			if( !isNumber(frioverhr) ){
				errorMsgs.put("frioverhr", "請填寫數字");
			}else if( isBlank(frioverhr) ){
				whd.setFrioverhr(0);
			}else{
				whd.setFrioverhr(Integer.parseInt(json.getFrioverhr()));
			}
			
			String satoverhr = json.getSatoverhr();
			if( !isNumber(satoverhr) ){
				errorMsgs.put("satoverhr", "請填寫數字");
			}else if( isBlank(satoverhr) ){
				whd.setSatoverhr(0);
			}else{
				whd.setSatoverhr(Integer.parseInt(json.getSatoverhr()));
			}
			
			String sunoverhr = json.getSunoverhr();
			if( !isNumber(sunoverhr) ){
				errorMsgs.put("sunoverhr", "請填寫數字");
			}else if( isBlank(sunoverhr) ){
				whd.setSunoverhr(0);
			}else{
				whd.setSunoverhr(Integer.parseInt(json.getSunoverhr()));
			}
			workhourdetail.add(whd);
			if (!errorMsgs.isEmpty()) {
				errorcnt++;
			}
			 errorMsgsList.add(errorMsgs);
		}
		//檢查新增資料格式
		for (WorkHourDetailJSON newjson : newworkhourdetailjson) {
			WorkHourDetail whd = new WorkHourDetail();
			Map<String, String> errorMsgs = new HashMap<>();
			
			String hscontent = newjson.getHscontent();
			if(isBlank(hscontent)){
				errorMsgs.put("hscontent", "不可為空");
			}else{
				whd.setHscontent(hscontent.trim());
			}
			
			String hsdeid = newjson.getHsdeid();
			if(isBlank(hsdeid)){
				errorMsgs.put("hsdeid", "請填寫週別");
			}else{
				whd.setHsdeid(hsdeid.trim());
			}
			
			String  alteremid = newjson.getEmid();
			if(isBlank(alteremid)){
				errorMsgs.put("emid", "請填寫員工編號");
			}else{
				whd.setEmid(alteremid.trim());
			}
			/*
			String orihscontent = newjson.getOrihscontent();
			if(isBlank(orihscontent)){
				errorMsgs.put("orihscontent", "請填寫工作內容");
			}else{
				whd.setOrihscontent(orihscontent.trim());
			}
			*/
			String monhr = newjson.getMonhr();
			if( !isNumber(monhr) ){
				errorMsgs.put("monhr", "請填寫數字");
			}else if( isBlank(monhr) ){
				whd.setMonhr(0);
			}else{
				whd.setMonhr(Integer.parseInt(newjson.getMonhr()));
			}
			
			String tueshr = newjson.getTueshr();
			if( !isNumber(tueshr) ){
				errorMsgs.put("tueshr", "請填寫數字");
			}else if( isBlank(tueshr) ){
				whd.setTueshr(0);
			}else{
				whd.setTueshr(Integer.parseInt(newjson.getTueshr()));
			}
			
			String wedhr = newjson.getWedhr();
			if( !isNumber(wedhr) ){
				errorMsgs.put("wedhr", "請填寫數字");
			}else if( isBlank(wedhr) ){
				whd.setWedhr(0);
			}else{
				whd.setWedhr(Integer.parseInt(newjson.getWedhr()));
			}
			
			String thurhr = newjson.getThurhr();
			if( !isNumber(thurhr) ){
				errorMsgs.put("thur", "請填寫數字");
			}else if( isBlank(thurhr) ){
				whd.setThurhr(0);
			}else{
				whd.setThurhr(Integer.parseInt(newjson.getThurhr()));
			}
			
			String frihr = newjson.getFrihr();
			if( !isNumber(frihr) ){
				errorMsgs.put("frihr", "請填寫數字");
			}else if( isBlank(frihr) ){
				whd.setFrihr(0);
			}else{
				whd.setFrihr(Integer.parseInt(newjson.getFrihr()));
			}
			
			String sathr = newjson.getSathr();
			if( !isNumber(sathr) ){
				errorMsgs.put("sathr", "請填寫數字");
			}else if( isBlank(sathr) ){
				whd.setSathr(0);
			}else{
				whd.setSathr(Integer.parseInt(newjson.getSathr()));
			}
			
			String sunhr = newjson.getSunhr();
			if( !isNumber(sunhr) ){
				errorMsgs.put("sun", "請填寫數字");
			}else if( isBlank(sunhr) ){
				whd.setSunhr(0);
			}else{
				whd.setSunhr(Integer.parseInt(newjson.getSunhr()));
			}
			
			String monoverhr = newjson.getMonoverhr();
			if( !isNumber(monoverhr) ){
				errorMsgs.put("monoverhr", "請填寫數字");
			}else if( isBlank(monoverhr) ){
				whd.setMonoverhr(0);
			}else{
				whd.setMonoverhr(Integer.parseInt(newjson.getMonoverhr()));
			}
			
			String tuesoverhr = newjson.getTuesoverhr();
			if( !isNumber(tuesoverhr) ){
				errorMsgs.put("tuesoverhr", "請填寫數字");
			}else if( isBlank(tuesoverhr) ){
				whd.setTuesoverhr(0);
			}else{
				whd.setTuesoverhr(Integer.parseInt(newjson.getTuesoverhr()));
			}
			
			String wedoverhr = newjson.getWedoverhr();
			if( !isNumber(wedoverhr) ){
				errorMsgs.put("wedoverhr", "請填寫數字");
			}else if( isBlank(wedoverhr) ){
				whd.setWedoverhr(0);
			}else{
				whd.setWedoverhr(Integer.parseInt(newjson.getWedoverhr()));
			}
			
			String thuroverhr = newjson.getThuroverhr();
			if( !isNumber(thuroverhr) ){
				errorMsgs.put("thuroverhr", "請填寫數字");
			}else if( isBlank(thuroverhr) ){
				whd.setThuroverhr(0);
			}else{
				whd.setThuroverhr(Integer.parseInt(newjson.getThuroverhr()));
			}
			
			String frioverhr = newjson.getFrioverhr();
			if( !isNumber(frioverhr) ){
				errorMsgs.put("frioverhr", "請填寫數字");
			}else if( isBlank(frioverhr) ){
				whd.setFrioverhr(0);
			}else{
				whd.setFrioverhr(Integer.parseInt(newjson.getFrioverhr()));
			}
			
			String satoverhr = newjson.getSatoverhr();
			if( !isNumber(satoverhr) ){
				errorMsgs.put("satoverhr", "請填寫數字");
			}else if( isBlank(satoverhr) ){
				whd.setSatoverhr(0);
			}else{
				whd.setSatoverhr(Integer.parseInt(newjson.getSatoverhr()));
			}
			
			String sunoverhr = newjson.getSunoverhr();
			if( !isNumber(sunoverhr) ){
				errorMsgs.put("sunoverhr", "請填寫數字");
			}else if( isBlank(sunoverhr) ){
				whd.setSunoverhr(0);
			}else{
				whd.setSunoverhr(Integer.parseInt(newjson.getSunoverhr()));
			}
			newworkhourdetail.add(whd);
			if (!errorMsgs.isEmpty()) {
				errorcnt++;
			}
			 newerrorMsgsList.add(errorMsgs);
		}
		//有錯誤訊息
		System.out.println(errorMsgsList.isEmpty());
		if (errorcnt>0) {
			 request.setAttribute("errorMsgsList", errorMsgsList);
			        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			 request.setAttribute("workhourdetail", workhourdetail);
			       // 將頁面轉交回原本新增頁面
			 request.setAttribute("newerrorMsgsList", newerrorMsgsList);
		        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			 request.setAttribute("newworkhourdetail", newworkhourdetail);
			 request.setAttribute("workhour", workhour);
			 request.setAttribute("week", week);
		       // 將頁面轉交回原本新增頁面
			 System.out.println(newerrorMsgsList);
			 
			 
			 return PAGE_UPDATE_WriteHr;
		}
		
		//檢查工作內容有沒有重複
		for( WorkHourDetailJSON aa : workhourdetailjson )
		{
			isnull.add(aa.getHscontent());
		}
		for( WorkHourDetailJSON aa : newworkhourdetailjson )
		{
			isnull.add(aa.getHscontent());
		}
		if(isnull.size()!=workhourdetailjson.size()+newworkhourdetailjson.size())
		{
			request.setAttribute("errorMsg", "工作內容不可重複");
			request.setAttribute("errorMsgsList", errorMsgsList);
	        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			request.setAttribute("workhourdetail", workhourdetail);
			       // 將頁面轉交回原本新增頁面
			request.setAttribute("newerrorMsgsList", newerrorMsgsList);
		        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			request.setAttribute("newworkhourdetail", newworkhourdetail);
		       // 將頁面轉交回原本新增頁面
			 request.setAttribute("workhour", workhour);
			 request.setAttribute("week", week);
			System.out.println("工作內容不可重複");
			return PAGE_UPDATE_WriteHr;
		}
		
		
		//執行修改
		for( WorkHourDetail aa : workhourdetail )
		{
			try{
				new WorkHourDetailDAO().update(aa);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		//執行新增
		for( WorkHourDetail aa : newworkhourdetail )
		{
			try{
				new WorkHourDetailDAO().insertWorkhourdetail(aa);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		try{
			new WorkHourDAO().updateState(4, emid);
			request.setAttribute("tmpsave", "工時暫存成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return PAGE_EMPLOYEE_HOME;
	}
	private String doSubmit(HttpServletRequest request) throws UnsupportedEncodingException{
		String emid =  (String)request.getSession().getAttribute("Employee");
		System.out.println(emid);
		Map<String, String> hourerrorMsg = new HashMap<>();
		Week thisweek = new Week();
		Week holiday = new Week();
		WorkHour workhour = new WorkHour();
		Week week = new Week();
		List<WorkHourDetailJSON> workhourdetailjson = GSON.fromJson(request.getParameter("AlterHr"), new TypeToken<List<WorkHourDetailJSON>>() {}.getType()); 
		List<WorkHourDetailJSON> newworkhourdetailjson = GSON.fromJson(request.getParameter("WriteHr"), new TypeToken<List<WorkHourDetailJSON>>() {}.getType()); 
		Set<String> isnull = new HashSet<String>();
		List<WorkHourDetail> workhourdetail = new ArrayList<WorkHourDetail>();
		List<WorkHourDetail> newworkhourdetail = new ArrayList<WorkHourDetail>();
		List<Map<String, String>> errorMsgsList = new ArrayList<>();
		List<Map<String, String>> newerrorMsgsList = new ArrayList<>();
		int errorcnt = 0;
		
		try{
			workhour = new WorkHourDAO().findworkhour(emid);
							
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		try{
			week = new WeekDAO().getHoli();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		//刪除資料
		if( request.getParameterValues("delete")!=null )
		{
			String [] delete = request.getParameterValues("delete");
			try{
				for(String hscontent : delete)
				{
					new WorkHourDetailDAO().delete(emid, hscontent);
				}
			}catch(Exception e){
				e.printStackTrace();
				request.setAttribute("errorMsg", "刪除失敗");
				return PAGE_UPDATE_WriteHr;
			}
		}
		
		//檢查修改資料格式
		
		for (WorkHourDetailJSON json : workhourdetailjson) {
			WorkHourDetail whd = new WorkHourDetail();
			Map<String, String> errorMsgs = new HashMap<>();
			
			String hscontent = json.getHscontent();
			
			if(isBlank(hscontent)){
				errorMsgs.put("hscontent", "不可為空");
			}else{
				whd.setHscontent(hscontent.trim());
			}
			
			String hsdeid = json.getHsdeid();
			if(isBlank(hsdeid)){
				errorMsgs.put("hsdeid", "請填寫週別");
			}else{
				whd.setHsdeid(hsdeid.trim());
			}
			
			String  alteremid = json.getEmid();
			if(isBlank(alteremid)){
				errorMsgs.put("emid", "請填寫員工編號");
			}else{
				whd.setEmid(alteremid.trim());
			}
			
			String orihscontent = json.getOrihscontent();
			if(isBlank(orihscontent)){
				errorMsgs.put("orihscontent", "請填寫工作內容");
			}else{
				whd.setOrihscontent(orihscontent.trim());
			}
			
			String monhr = json.getMonhr();
			if(isBlank(monhr)||!isNumber(monhr)){
				errorMsgs.put("monhr", "不可為空,\n0~8小時");
			}else{
				whd.setMonhr(Integer.parseInt(json.getMonhr()));
			}
			
			String tueshr = json.getTueshr();
			if(isBlank(tueshr)||!isNumber(tueshr)){
				errorMsgs.put("tueshr", "不可為空,\n0~8小時");
			}else{
				whd.setTueshr(Integer.parseInt(json.getTueshr()));
			}
			
			String wedhr = json.getWedhr();
			if(isBlank(wedhr)||!isNumber(wedhr)){
				errorMsgs.put("wedhr", "不可為空,\n0~8小時");
			}else{
				whd.setWedhr(Integer.parseInt(json.getWedhr()));
			}
			
			String thurhr = json.getThurhr();
			if(isBlank(thurhr)||!isNumber(thurhr)){
				errorMsgs.put("thurhr", "不可為空,\n0~8小時");
			}else{
				whd.setThurhr(Integer.parseInt(json.getThurhr()));
			}
			
			String frihr = json.getFrihr();
			if(isBlank(frihr)||!isNumber(frihr)){
				errorMsgs.put("frihr", "不可為空,\n0~8小時");
			}else{
				whd.setFrihr(Integer.parseInt(json.getFrihr()));
			}
			
			String sathr = json.getSathr();
			if(isBlank(sathr)||!isNumber(sathr)){
				errorMsgs.put("sathr", "不可為空,\n0~8小時");
			}else{
				whd.setSathr(Integer.parseInt(json.getSathr()));
			}
			
			String sunhr = json.getSunhr();
			if(isBlank(sunhr)||!isNumber(sunhr)){
				errorMsgs.put("sunhr", "不可為空,\n0~8小時");
			}else{
				whd.setSunhr(Integer.parseInt(json.getSunhr()));
			}
			
			String monoverhr = json.getMonoverhr();
			if(isBlank(monoverhr)||!isRightOverHr(monoverhr)){
				errorMsgs.put("monoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setMonoverhr(Integer.parseInt(json.getMonoverhr()));
			}
			
			String tuesoverhr = json.getTuesoverhr();
			if(isBlank(tuesoverhr)||!isRightOverHr(tuesoverhr)){
				errorMsgs.put("tuesoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setTuesoverhr(Integer.parseInt(json.getTuesoverhr()));
			}
			
			String wedoverhr = json.getWedoverhr();
			if(isBlank(wedoverhr)||!isRightOverHr(wedoverhr)){
				errorMsgs.put("wedoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setWedoverhr(Integer.parseInt(json.getWedoverhr()));
			}
			
			String thuroverhr = json.getThuroverhr();
			if(isBlank(thuroverhr)||!isRightOverHr(thuroverhr)){
				errorMsgs.put("thuroverhr", "不可為空,\n0~4小時");
			}else{
				whd.setThuroverhr(Integer.parseInt(json.getThuroverhr()));
			}
			
			String frioverhr = json.getFrioverhr();
			if(isBlank(frioverhr)||!isRightOverHr(frioverhr)){
				errorMsgs.put("frioverhr", "不可為空,\n0~4小時");
			}else{
				whd.setFrioverhr(Integer.parseInt(json.getFrioverhr()));
			}
			
			String satoverhr = json.getSatoverhr();
			if(isBlank(satoverhr)||!isRightOverHr(satoverhr)){
				errorMsgs.put("satoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setSatoverhr(Integer.parseInt(json.getSatoverhr()));
			}
			
			String sunoverhr = json.getSunoverhr();
			if(isBlank(sunoverhr)||!isRightOverHr(sunoverhr)){
				errorMsgs.put("sunoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setSunoverhr(Integer.parseInt(json.getSunoverhr()));
			}
			workhourdetail.add(whd);
			if (!errorMsgs.isEmpty()) {
				errorcnt++;
			}
			 errorMsgsList.add(errorMsgs);
		}
		//檢查新增資料格式
		for (WorkHourDetailJSON newjson : newworkhourdetailjson) {
			WorkHourDetail whd = new WorkHourDetail();
			Map<String, String> errorMsgs = new HashMap<>();
			
			String hscontent = newjson.getHscontent();
			if(isBlank(hscontent)){
				errorMsgs.put("hscontent", "不可為空");
			}else{
				whd.setHscontent(hscontent.trim());
			}
			
			String hsdeid = newjson.getHsdeid();
			if(isBlank(hsdeid)){
				errorMsgs.put("hsdeid", "請填寫週別");
			}else{
				whd.setHsdeid(hsdeid.trim());
			}
			
			String  alteremid = newjson.getEmid();
			if(isBlank(alteremid)){
				errorMsgs.put("emid", "請填寫員工編號");
			}else{
				whd.setEmid(alteremid.trim());
			}
			/*
			String orihscontent = newjson.getOrihscontent();
			if(isBlank(orihscontent)){
				errorMsgs.put("orihscontent", "請填寫工作內容");
			}else{
				whd.setOrihscontent(orihscontent.trim());
			}
			*/
			String monhr = newjson.getMonhr();
			if(isBlank(monhr)||!isNumber(monhr)){
				errorMsgs.put("monhr", "不可為空,\n0~8小時");
			}else{
				whd.setMonhr(Integer.parseInt(newjson.getMonhr()));
			}
		
			String tueshr = newjson.getTueshr();
			if(isBlank(tueshr)||!isNumber(tueshr)){
				errorMsgs.put("tueshr", "不可為空,\n0~8小時");
			}else{
				whd.setTueshr(Integer.parseInt(newjson.getTueshr()));
			}
			
			String wedhr = newjson.getWedhr();
			if(isBlank(wedhr)||!isNumber(wedhr)){
				errorMsgs.put("wedhr", "不可為空,\n0~8小時");
			}else{
				whd.setWedhr(Integer.parseInt(newjson.getWedhr()));
			}
			
			String thurhr = newjson.getThurhr();
			if(isBlank(thurhr)||!isNumber(thurhr)){
				errorMsgs.put("thurhr", "不可為空,\n0~8小時");
			}else{
				whd.setThurhr(Integer.parseInt(newjson.getThurhr()));
			}
			
			String frihr = newjson.getFrihr();
			if(isBlank(frihr)||!isNumber(frihr)){
				errorMsgs.put("frihr", "不可為空,\n0~8小時");
			}else{
				whd.setFrihr(Integer.parseInt(newjson.getFrihr()));
			}
			
			String sathr = newjson.getSathr();
			if(isBlank(sathr)||!isNumber(sathr)){
				errorMsgs.put("sathr", "不可為空,\n0~8小時");
			}else{
				whd.setSathr(Integer.parseInt(newjson.getSathr()));
			}
			
			String sunhr = newjson.getSunhr();
			if(isBlank(sunhr)||!isNumber(sunhr)){
				errorMsgs.put("sunhr", "不可為空,\n0~8小時");
			}else{
				whd.setSunhr(Integer.parseInt(newjson.getSunhr()));
			}
			
			String monoverhr = newjson.getMonoverhr();
			if(isBlank(monoverhr)||!isRightOverHr(monoverhr)){
				errorMsgs.put("monoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setMonoverhr(Integer.parseInt(newjson.getMonoverhr()));
			}
			
			String tuesoverhr = newjson.getTuesoverhr();
			if(isBlank(tuesoverhr)||!isRightOverHr(tuesoverhr)){
				errorMsgs.put("tuesoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setTuesoverhr(Integer.parseInt(newjson.getTuesoverhr()));
			}
			
			String wedoverhr = newjson.getWedoverhr();
			if(isBlank(wedoverhr)||!isRightOverHr(wedoverhr)){
				errorMsgs.put("wedoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setWedoverhr(Integer.parseInt(newjson.getWedoverhr()));
			}
			
			String thuroverhr = newjson.getThuroverhr();
			if(isBlank(thuroverhr)||!isRightOverHr(thuroverhr)){
				errorMsgs.put("thuroverhr", "不可為空,\n0~4小時");
			}else{
				whd.setThuroverhr(Integer.parseInt(newjson.getThuroverhr()));
			}
			
			String frioverhr = newjson.getFrioverhr();
			if(isBlank(frioverhr)||!isRightOverHr(frioverhr)){
				errorMsgs.put("frioverhr", "不可為空,\n0~4小時");
			}else{
				whd.setFrioverhr(Integer.parseInt(newjson.getFrioverhr()));
			}
			
			String satoverhr = newjson.getSatoverhr();
			if(isBlank(satoverhr)||!isRightOverHr(satoverhr)){
				errorMsgs.put("satoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setSatoverhr(Integer.parseInt(newjson.getSatoverhr()));
			}
			
			String sunoverhr = newjson.getSunoverhr();
			if(isBlank(sunoverhr)||!isRightOverHr(sunoverhr)){
				errorMsgs.put("sunoverhr", "不可為空,\n0~4小時");
			}else{
				whd.setSunoverhr(Integer.parseInt(newjson.getSunoverhr()));
			}
			newworkhourdetail.add(whd);
			if (!errorMsgs.isEmpty()) {
				errorcnt++;
			}
			 newerrorMsgsList.add(errorMsgs);
		}
		//有錯誤訊息
		System.out.println(errorMsgsList.isEmpty());
		if (errorcnt>0) {
			 request.setAttribute("errorMsgsList", errorMsgsList);
			        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			 request.setAttribute("workhourdetail", workhourdetail);
			       // 將頁面轉交回原本新增頁面
			 request.setAttribute("newerrorMsgsList", newerrorMsgsList);
		        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			 request.setAttribute("newworkhourdetail", newworkhourdetail);
			 request.setAttribute("workhour", workhour);
			 request.setAttribute("week", week);
		       // 將頁面轉交回原本新增頁面
			 System.out.println(newerrorMsgsList);
			 
			 
			 return PAGE_UPDATE_WriteHr;
		}
		
		//檢查工作內容有沒有重複
		for( WorkHourDetailJSON aa : workhourdetailjson )
		{
			isnull.add(aa.getHscontent());
		}
		for( WorkHourDetailJSON aa : newworkhourdetailjson )
		{
			isnull.add(aa.getHscontent());
		}
		if(isnull.size()!=workhourdetailjson.size()+newworkhourdetailjson.size())
		{
			request.setAttribute("errorMsg", "工作內容不可重複");
			request.setAttribute("errorMsgsList", errorMsgsList);
	        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			request.setAttribute("workhourdetail", workhourdetail);
			       // 將頁面轉交回原本新增頁面
			request.setAttribute("newerrorMsgsList", newerrorMsgsList);
		        // 含有輸入格式錯誤的empVO物件s，也存入 HttpServletRequest
			request.setAttribute("newworkhourdetail", newworkhourdetail);
		       // 將頁面轉交回原本新增頁面
			 request.setAttribute("workhour", workhour);
			 request.setAttribute("week", week);
			System.out.println("工作內容不可重複");
			return PAGE_UPDATE_WriteHr;
		}
		
		
		//執行修改
		for( WorkHourDetail aa : workhourdetail )
		{
			try{
				new WorkHourDetailDAO().update(aa);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		//執行新增
		for( WorkHourDetail aa : newworkhourdetail )
		{
			try{
				new WorkHourDetailDAO().insertWorkhourdetail(aa);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		try{
			thisweek = new WeekDAO().getWeekhr(emid);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try{
			holiday = new WeekDAO().getHoli();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.print(thisweek.getFri());
		if( !thisweek.getMon().equals(holiday.getMon()) )
		{
			hourerrorMsg.put("mon", "總時數不符合");
		}
		if( !thisweek.getTues().equals(holiday.getTues()) )
		{
			hourerrorMsg.put("tues", "總時數不符合");
		}
		if( !thisweek.getWed().equals(holiday.getWed()) )
		{
			hourerrorMsg.put("wed", "總時數不符合");
		}
		if( !thisweek.getThur().equals(holiday.getThur()) )
		{
			hourerrorMsg.put("thur", "總時數不符合");
		}
		if( !thisweek.getFri().equals(holiday.getFri()) )
		{
			hourerrorMsg.put("fri", "總時數不符合");
		}
		if( !thisweek.getSat().equals(holiday.getSat()) )
		{
			hourerrorMsg.put("sat", "總時數不符合");
		}
		if( !thisweek.getSun().equals(holiday.getSun()) )
		{
			hourerrorMsg.put("sun", "總時數不符合");
		}
		if (!hourerrorMsg.isEmpty()) {
			request.setAttribute("hourerrorMsg", hourerrorMsg);
	        return doFindAction(request);
	    }
		try{
			new WorkHourDAO().updateState(2, emid);
			request.setAttribute("save", "工時送出成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return PAGE_EMPLOYEE_HOME;
	}
	
}
	
