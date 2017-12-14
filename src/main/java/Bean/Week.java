package Bean;

import java.io.Serializable;



public class Week implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer mon;
	private Integer tues;
	private Integer wed;
	private Integer thur;
	private Integer fri;
	private Integer sat;
	private Integer sun;

	
	public Week(){
		
	}

	public Integer getMon() {
		return mon;
	}


	public void setMon(Integer mon) {
		this.mon = mon;
	}


	public Integer getTues() {
		return tues;
	}


	public void setTues(Integer tues) {
		this.tues = tues;
	}


	public Integer getWed() {
		return wed;
	}


	public void setWed(Integer wed) {
		this.wed = wed;
	}


	public Integer getThur() {
		return thur;
	}


	public void setThur(Integer thur) {
		this.thur = thur;
	}


	public Integer getFri() {
		return fri;
	}


	public void setFri(Integer fri) {
		this.fri = fri;
	}


	public Integer getSat() {
		return sat;
	}


	public void setSat(Integer sat) {
		this.sat = sat;
	}


	public Integer getSun() {
		return sun;
	}


	public void setSun(Integer sun) {
		this.sun = sun;
	}


}
