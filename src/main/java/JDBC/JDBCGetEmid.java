package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class JDBCGetEmid extends JDBCConnection  {

	private static final String GetEmid ="select emid.NEXTVAL from dual";
	private static final String GetCurrEmid = "select LAST_NUMBER from user_sequences where sequence_name='emid'";
	long num =0;
//	
//	public long start(){
//		PreparedStatement pstmt = null;
//		ResultSet rs = null ;
//		long num=0;
//		try{
//		Connection conn= connections();
//
//		pstmt = conn.prepareStatement(GetEmid);
//		rs = pstmt.executeQuery();
//
//		while(rs.next()){
//			
//			num = rs.getLong(1);
//			
////			 deptVO = new DeptVO();
////        	 deptVO.setDeptno(rs.getInt("deptno"));
//			
//		}
//		}catch(Exception e){
//			
//			throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
//
//		}
//		
//		return num;
//		}
//	
//	public long getemid(){
//	
//	PreparedStatement pstmt = null;
//	ResultSet rs = null ;
//	
//	
//	try{
//		Connection conn= connections();
//		
//		pstmt = conn.prepareStatement(GetCurrEmid);
//		rs = pstmt.executeQuery();
//		
//		while(rs.next()){
//			
//			num = rs.getLong("LAST_NUMBER");
//			
////			 deptVO = new DeptVO();
////        	 deptVO.setDeptno(rs.getInt("deptno"));
//			
//		}
//	}catch(Exception e){
//		
//		throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);
//
//	}
//	
//	return num;
////	System.out.println(num);
//	INSERT INTO dept2 (deptno,dname, loc) VALUES (dept2_seq.NEXTVAL,?, ?)
//	}
	
	public String GenerateEmid(){
		   String emid;
		   StringBuffer em = new StringBuffer();
		   PreparedStatement pstmt = null;
			ResultSet rs = null ;
			long emid_int = 0;
			try{
				

			Connection conn= connections();

			pstmt = conn.prepareStatement(GetEmid);
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				emid_int = rs.getLong(1);
		
				
			}
			}catch(Exception e){
				
				throw new RuntimeException("資料庫讀取錯誤:"+ e.getMessage(),e);

			}
			
			DecimalFormat df1 = new DecimalFormat("####"); 
			
			df1.format(emid_int);
			
			
			em.append("em");
			em.append(String.format("%04d", emid_int));
				
		   return em.toString();
	   }
	
	
	public static void main(String[] args){
		
		JDBCGetEmid test = new JDBCGetEmid();
		String a = test.GenerateEmid();
		System.out.println(a);
		
//	JDBCGetEmid test = new JDBCGetEmid();
	
////	long b = test.start();
//	System.out.println(b);
//
//	
////	long a = test.getemid();
//	System.out.println(a);
	
}
	
	
}
