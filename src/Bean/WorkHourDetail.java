package Bean;

import java.io.Serializable;



/**
 * The persistent class for the WORK_HOURS_DETAIL database table.
 * 
 */

public class WorkHourDetail implements Serializable {
	private static final long serialVersionUID = 1L;



	private String orihscontent;
	
	private String hsdeid;

	private String emid;

	private String hscontent;

	private Integer monhr;
	
	private Integer tueshr;
	
	private Integer wedhr;
	
	private Integer thurhr;
	
	private Integer frihr;
	
	private Integer sathr;
	
	private Integer sunhr;

	private Integer monoverhr;
	
	private Integer tuesoverhr;
	
	private Integer wedoverhr;
	
	private Integer thuroverhr;
	
	private Integer frioverhr;

	private Integer satoverhr;

	private Integer sunoverhr;

	public WorkHourDetail() {
	}
	
	public String getOrihscontent() {
		return orihscontent;
	}

	public void setOrihscontent(String orihscontent) {
		this.orihscontent = orihscontent;
	}

	public String getHsdeid(){
		return this.hsdeid;
	}

	public void setHsdeid(String hsdeid){
		this.hsdeid = hsdeid;
	}

	public String getEmid(){
		return this.emid;
	}

	public void setEmid(String emid){
		this.emid = emid;
	}

	public String getHscontent(){
		return this.hscontent;
	}

	public void setHscontent(String hscontent){
		this.hscontent = hscontent;
	}

	public Integer getFrihr() {
		return this.frihr;
	}

	public void setFrihr(Integer frihr) {
		this.frihr = frihr;
	}

	public Integer getFrioverhr() {
		return this.frioverhr;
	}

	public void setFrioverhr(Integer frioverhr) {
		this.frioverhr = frioverhr;
	}

	public Integer getMonhr() {
		return this.monhr;
	}

	public void setMonhr(Integer monhr) {
		this.monhr = monhr;
	}

	public Integer getMonoverhr() {
		return this.monoverhr;
	}

	public void setMonoverhr(Integer monoverhr) {
		this.monoverhr = monoverhr;
	}

	public Integer getSathr() {
		return this.sathr;
	}

	public void setSathr(Integer sathr) {
		this.sathr = sathr;
	}

	public Integer getSatoverhr() {
		return this.satoverhr;
	}

	public void setSatoverhr(Integer satoverhr) {
		this.satoverhr = satoverhr;
	}

	public Integer getSunhr() {
		return this.sunhr;
	}

	public void setSunhr(Integer sunhr) {
		this.sunhr = sunhr;
	}

	public Integer getSunoverhr() {
		return this.sunoverhr;
	}

	public void setSunoverhr(Integer sunoverhr) {
		this.sunoverhr = sunoverhr;
	}

	public Integer getThurhr() {
		return this.thurhr;
	}

	public void setThurhr(Integer thurhr) {
		this.thurhr = thurhr;
	}

	public Integer getThuroverhr() {
		return this.thuroverhr;
	}

	public void setThuroverhr(Integer thuroverhr) {
		this.thuroverhr = thuroverhr;
	}

	public Integer getTueshr() {
		return this.tueshr;
	}

	public void setTueshr(Integer tueshr) {
		this.tueshr = tueshr;
	}

	public Integer getTuesoverhr() {
		return this.tuesoverhr;
	}

	public void setTuesoverhr(Integer tuesoverhr) {
		this.tuesoverhr = tuesoverhr;
	}

	public Integer getWedoverhr() {
		return this.wedoverhr;
	}

	public void setWedoverhr(Integer wedoverhr) {
		this.wedoverhr = wedoverhr;
	}

	public Integer getWedhr() {
		return this.wedhr;
	}

	public void setWedhr(Integer wedhr) {
		this.wedhr = wedhr;
	}



}