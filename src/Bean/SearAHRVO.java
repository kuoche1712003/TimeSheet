package Bean;

import java.io.Serializable;

public class SearAHRVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hsid;
	private String emid;
	private String ename;
	private Integer mweekhr;
	private Integer rweekhr;
	private Integer overtime;
	
	public SearAHRVO(){}
	
	public void setHsid(String hsid){
		this.hsid = hsid;
	}
	public String getHsid(){
		return this.hsid;
	}
	
	public void setEmid(String emid){
		this.emid = emid;
	}
	public String getEmid(){
		return this.emid;
	}

	public void setEname(String ename){
		this.ename = ename;
	}
	public String getEname(){
		return this.ename;
	}
	
	public void setMweekhr(Integer mweekhr){
		this.mweekhr = mweekhr;
	}
	public Integer getMweekhr(){
		return this.mweekhr;
	}
	
	public void setRweekhr(Integer rweekhr){
		this.rweekhr = rweekhr;
	}
	public Integer getRweekhr(){
		return this.rweekhr;
	}
	
	public void setOvertime(Integer overtime){
		this.overtime = overtime;
	}
	public Integer getOvertime(){
		return this.overtime;
	}

}
