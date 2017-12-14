package Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Bean.SearAHRVO;
import Bean.SearAReviewVO;
import Bean.SearAUrgeVO;
import Bean.SearOHRVO;
import Bean.SearOReviewVO;
import JDBC.SearAHRJDBC;
import JDBC.ReviewHRJDBC;
import JDBC.UrgeHRJDBC;
import JDBC.SendMail;


public class ManagerPageController extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 String ManagerHome = "/WEB-INF/Views/Manager/ManagerHome.jsp";
	 String Review = "/WEB-INF/Views/Manager/Review.jsp";
	 String Review2 = "/WEB-INF/Views/Manager/Review2.jsp";
	 String Urge = "/WEB-INF/Views/Manager/Urge.jsp";
	 String SearchEmployee = "/WEB-INF/Views/Manager/SearchEmployee.jsp";
	 String SearchEmployee2 = "/WEB-INF/Views/Manager/SearchEmployee2.jsp";
	 String EditPwdMan ="/WEB-INF/Views/Manager/EditPwdMan.jsp";
	
	 private SearAHRJDBC hrJDBC = new SearAHRJDBC();
	 private ReviewHRJDBC rvhrJDBC = new ReviewHRJDBC();
	 private UrgeHRJDBC ughrJDBC = new UrgeHRJDBC();
	 private SendMail smJDBC = new SendMail();
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost (request , response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = null;
        String action =	(String) request.getParameter("action");
        switch (action) {
        	case "ManagerHome":
        		path = ManagerHome;
        		break;
            case "EditPwdMan":
            	path = EditPwdMan;
            	break;        		
            	///////////////////////////////////////////
            	////////////URGE WORK HOUR/////////////////   
            case "Urge":
                path = doFindAllUrgeAction(request);
                break;
            case "whichpageUHR":
            	path = doFindAllUrgeAction(request);
                break;
            case "UrgeHR":
                path = doUrgeAction(request);
                break;            	
            	///////////////////////////////////////////
            	///////////Review WORK HOUR////////////////       	
            case "Review":
                path = doFindAllReviewAction(request);
                break;
            case "whichpageRHR":
            	path = doFindAllReviewAction(request);
            	break;    	
            case "ReviewHRD":
            	path = doFindOneReviewAction(request);
            	break;    
            case "UpdateReview":
            	path = doUpdateReviewAction(request);
            	break;
            
            	///////////////////////////////////////////
            	///////////SEARCH WORK HOUR////////////////
            case "SearchEmployee":
                path = doFindAllAction(request);
                break;
            case "whichpageSHR":
            	path = doFindAllAction(request);
            	break;    
            case "FilterHR":
            	path = doFilterAction(request);
            	break;
            case "SearHRD":
            	path = doFindOneAction(request);
            	break;
            case "autoGetMon":
        		String existyear = request.getParameter("year");
        		/***** 開始查詢資料 *****/
        		List<String> exmonList = hrJDBC.autoGetMon(existyear);
        		
        		/***** 轉成JSON並print *****/
        		Gson gson = new Gson();
        		String json = gson.toJson(exmonList);
        		response.getWriter().println(json);
            	break;
            case "exportExcel":
            	path = doExportExcelAction(request);
            	break;
            	///////////////////////////////////////////
            	////////////////DEFAULT////////////////////
            default:
            	break;
        }    
         

        if(!action.equals("autoGetMon"))
        {
        	RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);
        }
        	          
    }
	private String doFindAllUrgeAction(HttpServletRequest request){
		/***** 建立上週全員工未填寫HR-Header *****/
		List<String> emidList = ughrJDBC.getEmidList();
		for(int i=0;i<emidList.size();i++){
			ughrJDBC.creatHeader(emidList.get(i));
		}
		/***** 開始查詢資料 *****/
		List<SearAUrgeVO> ughrList = ughrJDBC.findAllUrge();
		request.setAttribute("ughrList", ughrList);
		
		return Urge;
	}
	
	private String doUrgeAction(HttpServletRequest request){
		String hsid = request.getParameter("hsid");
		String emid = request.getParameter("emid");
		String ename = request.getParameter("ename");
		Integer urgecnt = Integer.parseInt(request.getParameter("urgecnt"));
		urgecnt++;
		/***** 進行催交 *****/
		ughrJDBC.doUrge(urgecnt, hsid, emid);
		
		/***** 開始查詢資料 *****/
		List<SearAUrgeVO> ughrList = ughrJDBC.findAllUrge();
		request.setAttribute("ughrList", ughrList);
		
		/***** 寄送催交MAIL *****/
		String email = smJDBC.getEmail(emid);
		smJDBC.urgeMail(email, hsid, emid, ename);
		return Urge;
	}
	
	
	private String doUpdateReviewAction(HttpServletRequest request){
		Integer status = Integer.parseInt(request.getParameter("status"));
		String hsid = request.getParameter("hsid");
		String emid = request.getParameter("emid");
		String ename = request.getParameter("ename");
		String reasonMsg = request.getParameter("reasonMsg");
		/***** 開始更新資料 *****/
		rvhrJDBC.updateReview(status, hsid, emid);
		
		/***** 返回資料清單 *****/
		List<SearAReviewVO> rvhrList = rvhrJDBC.findAllReview();
		request.setAttribute("rvhrList", rvhrList);
		
		/***** 寄送退回MAIL *****/
		if(status == 3){
			String email = smJDBC.getEmail(emid);
			smJDBC.reviewMail(email, hsid, emid, ename, reasonMsg);
		}
		return Review;
	}
	

	private String doFindOneReviewAction(HttpServletRequest request){
		/***** 取得篩選資料 *****/
		String hsid = request.getParameter("hsid");
		String emid = request.getParameter("emid");

		/***** 開始查詢資料 *****/
		SearAReviewVO rvhrVO = rvhrJDBC.findOofAReview(hsid, emid);
		request.setAttribute("rvhrVO", rvhrVO);
		
		List<SearOReviewVO> rvOhrList = rvhrJDBC.findOneReview(hsid, emid);
		request.setAttribute("rvOhrList", rvOhrList);
		
		return Review2;
	}
	
	private String doFindAllReviewAction(HttpServletRequest request){

		/***** 開始查詢資料 *****/
		List<SearAReviewVO> rvhrList = rvhrJDBC.findAllReview();
		request.setAttribute("rvhrList", rvhrList);
		
		return Review;
	}
	
	private String doFindAllAction(HttpServletRequest request){
		
		/***** 開始查詢資料 *****/
		List<SearAHRVO> sahrList = hrJDBC.findAll();
		request.setAttribute("sahrList", sahrList);
		
		/***** 讀入年份資料 *****/
		List<String> exyearList = hrJDBC.getExYear();
		request.setAttribute("exyearList", exyearList);
		
		/***** 讀入月份資料 *****/
		List<String> exmonList = hrJDBC.autoGetMon(exyearList.get(0));
		request.setAttribute("exmonList", exmonList);
		
		return SearchEmployee;
	}
	
	private String doExportExcelAction(HttpServletRequest request){
		
		String Emid = (String)request.getSession().getAttribute("Employee");
		String Ename = (String)request.getSession().getAttribute("Name");
		
		/***** 儲存Select選擇項目 *****/
		String filYear = request.getParameter("year");
		String filMon = request.getParameter("month");
		request.setAttribute("yearSelect", filYear);
		request.setAttribute("monSelect", filMon);
		
		/***** 開始查詢資料 *****/
		List<SearAHRVO> fahrList = hrJDBC.filterAll(filYear,filMon);
		request.setAttribute("sahrList", fahrList);
		
		int cnt=1;
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		for(int i = 0; i<fahrList.size(); i++){
			String currentHsid = fahrList.get(i).getHsid();
			String currentEmid = fahrList.get(i).getEmid();
			List<SearOHRVO> sohrList = hrJDBC.findOne(currentHsid, currentEmid);
			
			/***** 儲存ExcelDate *****/
			data = hrJDBC.writeExcel(data, fahrList.get(i), sohrList, cnt);
			cnt = cnt + sohrList.size();
			cnt+=4;
		}
		/***** 產生Excel *****/
		hrJDBC.generateExcel(data);

		
		/***** 寄出Email *****/
		String Email = smJDBC.getEmail(Emid);
		smJDBC.exportExcel(Email, Emid, Ename);
		
		

		/***** 讀入年份資料 *****/
		List<String> exyearList = hrJDBC.getExYear();
		request.setAttribute("exyearList", exyearList);
		/***** 讀入月份資料 *****/
		List<String> exmonList = hrJDBC.autoGetMon(filYear);
		request.setAttribute("exmonList", exmonList);
		
		return SearchEmployee;
	}
	
	private String doFilterAction(HttpServletRequest request){
		/***** 儲存Select選擇項目 *****/
		String filYear = request.getParameter("year");
		String filMon = request.getParameter("month");
		request.setAttribute("yearSelect", filYear);
		request.setAttribute("monSelect", filMon);
		
		/***** 開始查詢資料 *****/
		List<SearAHRVO> fahrList = hrJDBC.filterAll(filYear,filMon);
		request.setAttribute("sahrList", fahrList);

		/***** 讀入年份資料 *****/
		List<String> exyearList = hrJDBC.getExYear();
		request.setAttribute("exyearList", exyearList);
	
		/***** 讀入月份資料 *****/
		List<String> exmonList = hrJDBC.autoGetMon(filYear);
		request.setAttribute("exmonList", exmonList);
		return SearchEmployee;
		
	}

	private String doFindOneAction(HttpServletRequest request){
		String hsid = request.getParameter("hsid");
		String emid = request.getParameter("emid");
		/***** 開始查詢資料 *****/
		List<SearOHRVO> sohrList = hrJDBC.findOne(hsid, emid);
		request.setAttribute("sohrList", sohrList);
		return SearchEmployee2;
	}
	
}
