package Bean;

import java.io.Serializable;

public class SearOReviewVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hsid;
	private String emid;
	private String ename;
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

	public SearOReviewVO(){}
	
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
	
	public void setHscontent(String hscontent){
		this.hscontent = hscontent;
	}
	public String getHscontent(){
		return this.hscontent;
	}	
	
	public void setMonhr(Integer monhr){
		this.monhr = monhr;
	}
	public Integer getMonhr(){
		return this.monhr;
	}	

	public void setTueshr(Integer tueshr){
		this.tueshr = tueshr;
	}
	public Integer getTueshr(){
		return this.tueshr;
	}	

	public void setWedhr(Integer wedhr){
		this.wedhr = wedhr;
	}
	public Integer getWedhr(){
		return this.wedhr;
	}	
	
	public void setThurhr(Integer thurhr){
		this.thurhr = thurhr;
	}
	public Integer getThurhr(){
		return this.thurhr;
	}	
	
	public void setFrihr(Integer frihr){
		this.frihr = frihr;
	}
	public Integer getFrihr(){
		return this.frihr;
	}	
	
	public void setSathr(Integer sathr){
		this.sathr = sathr;
	}
	public Integer getSathr(){
		return this.sathr;
	}	
	
	public void setSunhr(Integer sunhr){
		this.sunhr = sunhr;
	}
	public Integer getSunhr(){
		return this.sunhr;
	}

	public Integer getMonoverhr() {
		return monoverhr;
	}

	public void setMonoverhr(Integer monoverhr) {
		this.monoverhr = monoverhr;
	}

	public Integer getTuesoverhr() {
		return tuesoverhr;
	}

	public void setTuesoverhr(Integer tuesoverhr) {
		this.tuesoverhr = tuesoverhr;
	}

	public Integer getWedoverhr() {
		return wedoverhr;
	}

	public void setWedoverhr(Integer wedoverhr) {
		this.wedoverhr = wedoverhr;
	}

	public Integer getThuroverhr() {
		return thuroverhr;
	}

	public void setThuroverhr(Integer thuroverhr) {
		this.thuroverhr = thuroverhr;
	}

	public Integer getFrioverhr() {
		return frioverhr;
	}

	public void setFrioverhr(Integer frioverhr) {
		this.frioverhr = frioverhr;
	}

	public Integer getSatoverhr() {
		return satoverhr;
	}

	public void setSatoverhr(Integer satoverhr) {
		this.satoverhr = satoverhr;
	}

	public Integer getSunoverhr() {
		return sunoverhr;
	}

	public void setSunoverhr(Integer sunoverhr) {
		this.sunoverhr = sunoverhr;
	}		
	
	
}