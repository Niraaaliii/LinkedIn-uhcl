package src_files;

import java.sql.Date;

public class JobSharing {

	String jobSharingID;
	String jobID;
	Date dateandtime;
	String userID;
	
	public String getJobSharingID() {
		return jobSharingID;
	}
	public void setJobSharingID(String jobSharingID) {
		this.jobSharingID = jobSharingID;
	}
	public String getJobID() {
		return jobID;
	}
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	public Date getDateandtime() {
		return dateandtime;
	}
	public void setDateandtime(Date dateandtime) {
		this.dateandtime = dateandtime;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
