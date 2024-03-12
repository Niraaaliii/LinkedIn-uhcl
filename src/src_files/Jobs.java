package src_files;

import java.sql.Date;

public class Jobs {

	String jobID;
	String jobTitle;
	String creator;
	Date dateandtime;
	
	public String getJobID() {
		return jobID;
	}
	public void setJobID(String jobID) {
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
}
