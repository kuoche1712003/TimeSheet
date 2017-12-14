package Bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the EMPLOYEE database table.
 * 
 */
@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String emid;
	private Integer autid;
	private String autid_str;
	private Integer emacstatus;
	private String email;
	private String emailkey;
	private Integer emstatus;
	private Integer gender;
	private String id;
	private Integer count;

	@Temporal(TemporalType.DATE)
	private Date leavedate;
	private String name;
	private String passwd;

	@Temporal(TemporalType.DATE)
	private Date startdate;

	public Employee() {
	}
	
	

	public String getEmid() {
		return this.emid;
	}

	public void setEmid(String emid) {
		this.emid = emid;
	}

	public Integer getAutid() {
		return this.autid;
	}
	

	public void setAutid(Integer autid) {
		this.autid = autid;
	}
	
	public Integer getCount() {
		return this.count;
	}
	

	public void setCount(Integer _count) {
		this.count = _count;
	}

	public Integer getEmacstatus() {
		return this.emacstatus;
	}

	public void setEmacstatus(Integer emacstatus) {
		this.emacstatus = emacstatus;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailkey() {
		return this.emailkey;
	}

	public void setEmailkey(String emailkey) {
		this.emailkey = emailkey;
	}

	public Integer getEmstatus() {
		return this.emstatus;
	}

	public void setEmstatus(Integer emstatus) {
		this.emstatus = emstatus;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLeavedate() {
		return this.leavedate;
	}

	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

}