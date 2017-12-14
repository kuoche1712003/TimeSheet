package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import Bean.*;

public class EmpHeaderDAO {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public EmpHeaderDAO() throws Exception{
		this.conn = ConnectionHelper.getConnection();
		this.stmt = conn.createStatement();
	}
	public EmpHeader createObject(ResultSet rs) throws Exception{
		EmpHeader empheader = new EmpHeader();
		empheader.setHsid(rs.getString("hsid"));
		empheader.setEmid(rs.getString("emid"));
		empheader.setStdes(rs.getInt("stdes"));
		empheader.setUrgtimes(rs.getInt("urgtimes"));
		empheader.setHr(rs.getInt("hr"));
		empheader.setNeedhr(rs.getInt("needhr"));
		empheader.setOverhr(rs.getInt("overhr"));
		return empheader;
	}
	public List<EmpHeader> findAlterHeader( String emid ) throws Exception{
		List <EmpHeader> empheader = new ArrayList<EmpHeader>();
		String sql = "select * from EMPWORKHOURHEADER where EMID like ? and ( stdes = 3 or (URGTIMES > 0 and nvl(stdes,0) <> 1  and nvl(stdes,0) <> 2 ) ) order by hsid";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
        this.rs = pstmt.executeQuery();
        while(rs.next()){
			empheader.add(createObject(rs)); 
		}
        close();
		return empheader;
		
	}
	
	public List<EmpHeader> findCompleteHeader( String emid ) throws Exception{
		List <EmpHeader> empheader = new ArrayList<EmpHeader>();
		String sql = "select * from EMPWORKHOURHEADER where EMID like ? and ( stdes = 1  )";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
        this.rs = pstmt.executeQuery();
        while(rs.next()){
			empheader.add(createObject(rs)); 
		}
        close();
		return empheader;
		
	}
	
	public List<EmpHeader> findCompleteHeader( String emid, String yearmon ) throws Exception{
		List <EmpHeader> empheader = new ArrayList<EmpHeader>();
		String sql = "select * from EMPWORKHOURHEADER where EMID like ? and ( stdes = 1  ) and hsid like ?";
		pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, emid);
        pstmt.setString(2, yearmon);
        this.rs = pstmt.executeQuery();
        while(rs.next()){
			empheader.add(createObject(rs)); 
		}
        close();
		return empheader;
		
	}
	
	
	private void close() throws Exception {
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (pstmt != null) pstmt.close();
		if (conn != null) conn.close();
	}
	
	
}
