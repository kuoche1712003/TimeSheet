package Bean;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the HOLIDAY database table.
 * 
 */

public class Holiday implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date hddate;

	private Integer hdhr;

	private Integer hdtype;

	private String hdweek;
	private String datestring;

	public Holiday() {
	}
	public String getDateString(){
	return this.datestring;
	}
	
	public String setDateString(String dateString){
		return this.datestring = dateString;
	}

	public Date getHddate() {
		return this.hddate;
	}

	public void setHddate(Date hddate) {
		this.hddate = hddate;
	}

	public Integer getHdhr() {
		return this.hdhr;
	}

	public void setHdhr(Integer hdhr) {
		this.hdhr = hdhr;
	}

	public Integer getHdtype() {
		return this.hdtype;
	}

	public void setHdtype(Integer hdtype) {
		this.hdtype = hdtype;
	}

	public String getHdweek() {
		return this.hdweek;
	}

	public void setHdweek(String hdweek) {
		this.hdweek = hdweek;
	}

}