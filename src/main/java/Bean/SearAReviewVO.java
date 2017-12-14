package Bean;

import java.io.Serializable;
import java.util.*;

public class SearAReviewVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hsid;
	private String emid;
	private String ename;
	private Integer mweekhr;
	private Integer rweekhr;
	private Integer urgestatus;
	private Date reviewtime;
	
	public SearAReviewVO(){}

	public String getHsid() {
		return hsid;
	}

	public void setHsid(String hsid) {
		this.hsid = hsid;
	}

	public String getEmid() {
		return emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Integer getUrgestatus() {
		return urgestatus;
	}

	public void setUrgestatus(Integer urgestatus) {
		this.urgestatus = urgestatus;
	}

	public Integer getMweekhr() {
		return mweekhr;
	}

	public void setMweekhr(Integer mweekhr) {
		this.mweekhr = mweekhr;
	}

	public Integer getRweekhr() {
		return rweekhr;
	}

	public void setRweekhr(Integer rweekhr) {
		this.rweekhr = rweekhr;
	}

	public Date getReviewtime() {
		return reviewtime;
	}

	public void setReviewtime(Date reviewtime) {
		this.reviewtime = reviewtime;
	}
	
	
	
}
