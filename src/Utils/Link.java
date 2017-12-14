package Utils;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Bean.Employee;

public class Link {
	
	private String emid;
	public String StartLink(String emid) throws Exception{
	    	
	    	
	    	StringBuffer link = new StringBuffer(); 
	    	
	    	SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			Calendar cal= Calendar.getInstance();
			String start_date = sdFormat.format(date);
			 
			cal.add(Calendar.DAY_OF_MONTH, +3);
			String end_date = sdFormat.format(cal.getTime());
			 
//			 System.out.println(start_date);
//			 System.out.println(end_date);
			 
			 link.append(start_date);
			 link.append("-");
			 link.append(emid);
			 link.append("-");
			 link.append(end_date);
//			 System.out.println(link);
			 String link_tostring = link.toString();
			 String cKey = "iisi_start_iisi_";
			 String enString = AES.Encrypt(link_tostring, cKey);
			 String enString1 = URLEncoder.encode(enString,"UTF-8");
//			 System.out.println(enString);
	    	
			 return enString1;
	    }
	
	
	public boolean LinkVerify(String link) throws Exception{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	String sKey = "iisi_start_iisi_";

//	String deString1 = URLDecoder.decode(link, "UTF-8");
	String deString = AES.Decrypt(link, sKey);
	
		System.out.println(deString);
		String vlink[] = deString.split("-");
//		for(int i=0; i < vlink.length ;i++){
//			System.out.println(vlink[i]);
//		}
		
		Date now = new Date();
//		System.out.println(now);
		Date start_date = sdf.parse(vlink[0]);
//		System.out.println(start_date);
		Date end_date = sdf.parse(vlink[2]);
//		System.out.println(end_date);
		long difference = end_date.getTime()- now.getTime() ;
//		System.out.println(difference);
		setEmid(vlink[1]);
		if(difference > 0){
			getEmid();
			return true;
		}else{
			return false;
		}
	}

	public String getEmid() {
		return this.emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}
	
public static void main(String[] args) throws Exception {
   Link test = new Link();
   String emid = "em0001";
   String link = test.StartLink(emid);
   test.LinkVerify(link);
	
	
}
}
