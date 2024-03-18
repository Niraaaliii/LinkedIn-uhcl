package src_files;

import java.sql.Date;

public class Jobs {

	int jobID;
	String jobTitle;
	String jobDesc;
	String creator;
	Date dateandtime;
	
	public Jobs(int jID, String jt, String jd, String c, Date dt) {
		jobID = jID;
		jobTitle = jt;
		jobDesc = jd;
		creator = c;
		dateandtime = dt;
	}
	
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getDateandtime() {
		return dateandtime;
	}
	public void setDateandtime(Date dateandtime) {
		this.dateandtime = dateandtime;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
}
