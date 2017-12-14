package Bean;

import java.io.Serializable;

import java.util.Date;

/**
 * The persistent class for the WORK_HOURS database table.
 * 
 */

public class WorkHour implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String hsid;

	private String emid;

	private Date revtm;

	private Integer stdes;

	private Integer urgtimes;

	private Date urgtm;


	public WorkHour() {
	}


	public String getHsid(){
		return this.hsid;
	}

	public void setHsid(String hsid){
		this.hsid = hsid;
	}

	public String getEmid(){
		return this.emid;
	}

	public void setEmid(String emid){
		this.emid = emid;
	}

	public Date getRevtm() {
		return this.revtm;
	}

	public void setRevtm(Date revtm) {
		this.revtm = revtm;
	}

	public Integer getStdes() {
		return this.stdes;
	}

	public void setStdes(Integer stdes) {
		this.stdes = stdes;
	}

	public Integer getUrgtimes() {
		return this.urgtimes;
	}

	public void setUrgtimes(Integer urgtimes) {
		this.urgtimes = urgtimes;
	}

	public Date getUrgtm() {
		return this.urgtm;
	}

	public void setUrgtm(Date urgtm) {
		this.urgtm = urgtm;
	}

}