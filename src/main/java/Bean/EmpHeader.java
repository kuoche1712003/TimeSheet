package Bean;

import java.io.Serializable;

public class EmpHeader implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hsid;
	private String emid;
	private Integer stdes;
	private Integer urgtimes;
	private Integer hr;
	private Integer needhr;
	private Integer overhr;
	
	public EmpHeader(){
		
	}
	
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
	public Integer getStdes() {
		return stdes;
	}
	public void setStdes(Integer stdes) {
		this.stdes = stdes;
	}
	public Integer getUrgtimes() {
		return urgtimes;
	}
	public void setUrgtimes(Integer urgtimes) {
		this.urgtimes = urgtimes;
	}
	public Integer getHr() {
		return hr;
	}
	public void setHr(Integer hr) {
		this.hr = hr;
	}
	public Integer getNeedhr() {
		return needhr;
	}
	public void setNeedhr(Integer needhr) {
		this.needhr = needhr;
	}
	public Integer getOverhr() {
		return overhr;
	}
	public void setOverhr(Integer overhr) {
		this.overhr = overhr;
	}
	
}
