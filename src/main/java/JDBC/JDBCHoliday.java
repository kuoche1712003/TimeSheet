package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Bean.Holiday;

public class JDBCHoliday extends JDBCConnection {

	public String AddYearHd_6(String year){
		int _year = Integer.parseInt(year);
		StringBuffer addyearhd_6 = new StringBuffer("INSERT ALL ");

		
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Calendar cal_6 = Calendar.getInstance();
		
		cal_6.set(_year, 0, 1);
		//設定年度
		switch(cal_6.get(Calendar.DAY_OF_WEEK)){
		
		case Calendar.SUNDAY:
			cal_6.add(cal_6.DAY_OF_YEAR,  6 );
			break;
		case Calendar.MONDAY:
			cal_6.add(cal_6.DAY_OF_YEAR,  5);
			break;
		case Calendar.TUESDAY:
			cal_6.add(cal_6.DAY_OF_YEAR, 4 );
			break;
		case Calendar.WEDNESDAY:
			cal_6.add(cal_6.DAY_OF_YEAR,  3  );
			break;
		case Calendar.THURSDAY:
			cal_6.add(cal_6.DAY_OF_YEAR,  2 );
			break;
		case Calendar.FRIDAY:
			cal_6.add(cal_6.DAY_OF_YEAR, 1);
			break;
		case Calendar.SATURDAY:
			cal_6.add(cal_6.DAY_OF_YEAR,  0);
			break;
		}
		String month1 = String.format("%02d", (cal_6.get(cal_6.MONTH)+1));
		String weekend1 = String.format("%02d", cal_6.get(cal_6.WEEK_OF_YEAR));
		addyearhd_6.append("INTO HOLIDAY VALUES ( TO_DATE( "+ sd.format(cal_6.getTime())+",'yyyymmdd'" +")" +",1,8,"+ "to_char(trunc(TO_DATE(" +  sd.format(cal_6.getTime()) + ",'yyyymmdd'), 'IW'),'yyyymmiw'))" );

		
		Calendar cal_8 = Calendar.getInstance();
		cal_8.set(_year, 11,31);
		int date1 = Integer.parseInt(sd.format(cal_8.getTime()));

		while(cal_6.getWeekYear() <=_year){
			cal_6.add(cal_6.DAY_OF_YEAR,7);
			int date2 = Integer.parseInt(sd.format(cal_6.getTime()));
			if(date1 - date2>=0){
				String month = String.format("%02d", (cal_6.get(cal_6.MONTH)+1));
				String weekend = String.format("%02d", cal_6.get(cal_6.WEEK_OF_YEAR));
				//(select to_char(trunc(sysdate, 'IW'),'yyyymmiw') from dual) 
//				addyearhd_6.append("INTO HOLIDAY VALUES ( TO_DATE( "+ sd.format(cal_6.getTime())+",'yyyymmdd'" +")" +",1,8,"+ (year +  month + weekend)+")" );
				addyearhd_6.append("INTO HOLIDAY VALUES ( TO_DATE( "+ sd.format(cal_6.getTime())+",'yyyymmdd'" +")" +",1,8,"+ "to_char(trunc(TO_DATE(" +  sd.format(cal_6.getTime()) + ",'yyyymmdd'), 'IW'),'yyyymmiw'))" );

			}
		}

		addyearhd_6.append("SELECT 1 FROM DUAL");

		
		System.out.println(addyearhd_6.toString());
		
		return addyearhd_6.toString();
	}
	public String AddYearHd_7(String year){
		int _year = Integer.parseInt(year);
		StringBuffer addyearhd_7 = new StringBuffer("INSERT ALL ");

		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Calendar cal_7 = Calendar.getInstance();
		cal_7.set(_year, 0, 1);

		
		switch(cal_7.get(Calendar.DAY_OF_WEEK)){
		
		case Calendar.SUNDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  0 );
			break;
		case Calendar.MONDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  6);
			break;
		case Calendar.TUESDAY:
			cal_7.add(cal_7.DAY_OF_YEAR, 5 );
			break;
		case Calendar.WEDNESDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  4 );
			break;
		case Calendar.THURSDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  3 );
			break;
		case Calendar.FRIDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  2);
			break;
		case Calendar.SATURDAY:
			cal_7.add(cal_7.DAY_OF_YEAR,  1);
			break;
		}
		
		String month1 = String.format("%02d", (cal_7.get(cal_7.MONTH)+1));
		String weekend1 = String.format("%02d", cal_7.get(cal_7.WEEK_OF_YEAR));
		addyearhd_7.append("INTO HOLIDAY VALUES ( TO_DATE( "+ sd.format(cal_7.getTime())+",'yyyymmdd'" +")" +",1,8,"+ "to_char(trunc(TO_DATE(" +  sd.format(cal_7.getTime()) + ",'yyyymmdd'), 'IW'),'yyyymmiw'))" );


		
		Calendar cal_8 = Calendar.getInstance();
		cal_8.set(_year, 11,31);
		int date1 = Integer.parseInt(sd.format(cal_8.getTime()));

		while(cal_7.getWeekYear() <=_year){
			cal_7.add(cal_7.DAY_OF_YEAR,7);
			int date2 = Integer.parseInt(sd.format(cal_7.getTime()));
			if(date1 - date2>=0){
//				System.out.println(sd.format(cal_7.getTime()));
				String month = String.format("%02d", (cal_7.get(cal_7.MONTH)+1));
				String weekend = String.format("%02d", cal_7.get(cal_7.WEEK_OF_YEAR));
				addyearhd_7.append("INTO HOLIDAY VALUES ( TO_DATE( "+ sd.format(cal_7.getTime())+",'yyyymmdd'" +")" +",1,8,"+ "to_char(trunc(TO_DATE(" +  sd.format(cal_7.getTime()) + ",'yyyymmdd'), 'IW'),'yyyymmiw'))" );
			}
		}
		
		addyearhd_7.append("SELECT 1 FROM DUAL");
//		System.out.println(addyearhd_7.toString());

		return addyearhd_7.toString();
	}
    private static final String GetYearExist ="select count(to_char(hddate,'yyyy')) from holiday where to_char(hddate,'yyyy')=?";

	public boolean AddYearHd(String year){
//		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
		
		 	PreparedStatement pstmt = null;
		 	
	        int hdstatus = 0;
	        String  saturday_sql = AddYearHd_6(year); 
	        String  subday_sql = AddYearHd_7(year); 

	        System.out.println(saturday_sql);
	        System.out.println(subday_sql);
	        
	        try {
	        	Connection  con = connections();
	            pstmt = con.prepareStatement(saturday_sql);
	            pstmt.executeUpdate();
	            pstmt = con.prepareStatement(subday_sql);
	            pstmt.executeUpdate();

	      	    pstmt.close();
	          
	       
	           
	      	    hdstatus = 1; //新增成功
				
			} catch(Exception e){
				
				hdstatus = 2; //發生錯誤
				  
			}
			
			if(hdstatus == 1){
				return true;
			}else{
				return false;
			}	
		
		
		
		
		
		
		
		
		
//		cal_6.set(_year, 0, 1);
//		//設定年度
//		cal_6.add(cal_6.DAY_OF_YEAR, 7 - cal_6.get(Calendar.DAY_OF_WEEK));
//		System.out.println(sdFormat.format(cal_6.getTime()));
//		System.out.println(cal_6.getWeeksInWeekYear());
////		System.out.println(cal.getWeekYear());
//		
//		while(cal_6.getWeekYear() == _year){
//			
//			cal_6.add(cal_6.DAY_OF_YEAR,7);
//			if(cal_6.getWeekYear() == _year){
//				System.out.println(sdFormat.format(cal_6.getTime()));
//			}
//			
//		}
		
//		for(int i=0; i<=cal.getWeeksInWeekYear();i++){
//			cal.add(cal.DAY_OF_YEAR,7);
//			System.out.println(sdFormat.format(cal.getTime()));
//		}
//		
//		while(cal.YEAR == _year )
//		{
//			cal.add(cal.DAY_OF_YEAR,7);
//			System.out.println(sdFormat.format(cal.getTime()));
//		}
//		
		
//		for  (int i =1 ; i<=53; i++){
//			
//			cal.add(cal.DAY_OF_YEAR,7);
//			System.out.println(sdFormat.format(cal.getTime()));
//		}
		
//		cal.add(cal.DAY_OF_YEAR, 7 - cal.get(Calendar.DAY_OF_WEEK));
//		System.out.println(sdFormat.format(cal.getTime()));
//		System.out.println(Calendar.WEEK_OF_YEAR);
		
	
////		System.out.println(cal.DAY_OF_YEAR);
//		cal.add(cal.DAY_OF_YEAR, 7 - cal.get(Calendar.DAY_OF_WEEK));
//		System.out.println(sdFormat.format(cal.getTime()));
//		System.out.println(cal.WEEK_OF_YEAR);
//		while(cal.WEEK_OF_YEAR <=53)
//		
//		。
		
//		do{
//			
//			System.out.println(sdFormat.format(cal.getTime()));
//			cal.add(cal.DAY_OF_YEAR,7 );
//			System.out.println(cal.get(Calendar.WEEK_OF_YEAR));
//			
//		}while(cal.get(Calendar.WEEK_OF_YEAR) <= 53);
		

		
		
		
		

		
		
		
		
	
		
		
		//取得第一個星期六日
		//迴圈新增
		
		 
		
		  
//		while(cal.get(Calendar.WEEK_OF_YEAR) != 53)
		
//		System.out.println(sdFormat.format(cal.getTime()));
//		
//		String week = String.valueOf(cal.get(Calendar.DAY_OF_WEEK)-1); 
//		System.out.println(week);
	}
	
	public boolean isYearExist(String year){
    	

		PreparedStatement pstmt = null;
		ResultSet rs = null ;
		int count=0;
		try{
			

		Connection con= connections();

		pstmt = con.prepareStatement(GetYearExist);
        pstmt.setString(1, year);

		rs = pstmt.executeQuery();
//        rs = pstmt.getResultSet();
//		rs = pstmt.execute();
//        rs = pstmt.

//		rs.next();
//		count = rs.getInt(1);
		while(rs.next()){
			count = rs.getInt(1);
//			count = rs.getRow();
//			System.out.println(count);

			
			
			
		}
		}catch(Exception e){
			
			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

		}
		
		
		if(count>=1){
			System.out.println("true"+count);
			return true;
		}else{
			System.out.println("false"+count);
			return false;
		}
    					
    }
	
	
	public boolean AddHd_1(String date){     //新增例假日用來找資料庫是否已有相同資料的
		//int _year = Integer.parseInt(year);
		String addhd = "SELECT hddate FROM HOLIDAY WHERE to_char(hddate,'yyyymmdd')=?";
		String vlink[] = date.split("-");
	 	String del_dash = vlink[0]+vlink[1]+vlink[2];
		PreparedStatement pstmt = null;
		ResultSet rs = null;
        int hdstatus = 0;
        boolean flag = false ;
        
        
        try {
        	Connection  con = connections();
            pstmt = con.prepareStatement(addhd);
            pstmt.setString(1,del_dash);
            rs = pstmt.executeQuery();
            if(  rs.next() )
            {
            	flag = true;
            }else{
            	flag =false;
            }
      	    pstmt.close();
          
       
           
      	    hdstatus = 1; //新增成功
			
		} catch(Exception e){
			
			hdstatus = 2; //發生錯誤
			  
		}
			
		if(flag== true)
			return true;
		else
			return false;
		
		
	}
	public boolean AddHd(String date,int type,int hour){   //新增例假日插入資料用的

	 	String vlink[] = date.split("-");
	 	String del_dash = vlink[0]+vlink[1]+vlink[2];
		String INSERT_STMT = "INSERT INTO holiday  VALUES ((to_date(?,'yyyymmdd')),?, ?,to_char(trunc(to_date(?,'yyyymmdd'), 'IW'),'yyyymmiw'))";
	 	PreparedStatement pstmt = null;

	 	System.out.println("sql"+date);
	 	System.out.println(date);
	 	System.out.println(del_dash);
	 	System.out.println(type);
	 	System.out.println(hour);
        int status = 0;
	 	
        try {
        	Connection  con = connections();
            pstmt = con.prepareStatement(INSERT_STMT);
           
        
    	
            pstmt.setString(1,del_dash);
            pstmt.setInt(2,type);
            pstmt.setInt(3,hour);
            pstmt.setString(4,del_dash);
            pstmt.executeQuery();

      	    pstmt.close();
            status = 1; 
			
		} catch(Exception e){
			status = 2;
			
//			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

			  
		}
	
        if(status == 1){
        	return true;
        }else {
        	return false;
        }
}

	public boolean AlterHdCheck(String date){
	     String GetHdExist ="select count(*)  from holiday where to_char(hddate,'yyyymmdd')=?";
	     String vlink[] = date.split("-");
		 String del_dash = vlink[0]+vlink[1]+vlink[2];
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 int count=-1;
		 try{
			 Connection  con = connections();
	            pstmt = con.prepareStatement(GetHdExist);
	            pstmt.setString(1,del_dash);
	            rs = pstmt.executeQuery();
	            
	           while(rs.next()){
	   			 count = rs.getInt(1);
	   			 System.out.println(count);
	   			
	           		}
	   		}catch(Exception e){
	   			
	   			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

	   		}
	   		
	   		
	   		if(count>=1){
	   			return false;
	   		}else{
	   			return true;
	   		}
		 
	     
	     
	}
	
	public Holiday AlterHd(String date){              // 修改假日的檢查
	
		String addhd = "SELECT * FROM HOLIDAY WHERE to_char(hddate,'yyyymmdd')=?";
		String vlink[] = date.split("-");
	 	String hddate = vlink[0]+vlink[1]+vlink[2];
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Holiday hd = new Holiday();
        int status = 0;
        boolean flag = false ;
        
        
        try {
        	Connection  con = connections();
            pstmt = con.prepareStatement(addhd);
            pstmt.setString(1,hddate);
            rs = pstmt.executeQuery();
            if(  rs.next() )
            {
            	hd.setDateString(hddate);
            	hd.setHdtype(rs.getInt("hdtype"));
            	hd.setHdhr(rs.getInt("hdhr"));
            	hd.setHdweek(rs.getString("hdweek"));
            	
            	
//            	flag = true;
            }else{
//            	flag =false;
            }
      	    pstmt.close();
          
			
		} catch(Exception e){
			
			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

		}
			
//		if(flag== true)
//			return true;
//		else
//			return false;
		
        return hd;
		
	}
	public void AlterHdWrite(Holiday hd){              // 修改假日的寫資料到資料庫
		
		String Update_stmt = "update  holiday  set hdtype=?, hdhr=? where hddate = to_date(?,'yyyymmdd')";
//		String INSERT_STMT = "UPDATE holiday() set VALUES (?,?,to_char(trunc(to_date(?,'yyyymmdd'), 'IW'),'yyyymmiw'))";
	 	PreparedStatement pstmt = null;

	 	
        try {
        	Connection  con = connections();
            pstmt = con.prepareStatement(Update_stmt);
           
        
    	
            pstmt.setInt(1,hd.getHdtype());
            pstmt.setInt(2,hd.getHdhr());
            pstmt.setString(3, hd.getDateString());
            
            
            pstmt.executeQuery();

      	    pstmt.close();
             	  
			
		} catch(Exception e){
			
		
			  
		}
		
		
	}
	
	public static void main(String[] args){
		JDBCHoliday test = new JDBCHoliday();
//		test.AddYearHd("2017");

//		test.isYearExist("2020");
//		test.isYearExist("2017");
//
//		test.isYearExist("2018");
//		test.isYearExist("2019");

//		test.AddYearHd_7("2017");

//		test.AddYearHd_7("2018");
//		test.AddYearHd_7("2021");
//		test.AddYearHd_7("2022");
//		test.AddYearHd_7("2023");
//		test.AddYearHd_7("2024");
//		test.AddYearHd_7("2025");
//		test.AddYearHd_7("2026");
//		test.AddYearHd_7("2027");
//		test.AddYearHd_7("2028");
//		test.AddYearHd_7("2029");
//		test.AddYearHd_7("2030");
//		test.AddYearHd_7("2032");
//		test.AddYearHd_7("2033");





		
//		
		
	
	
}
	
}
