package src_files;
import java.sql.*;

public class Notifications {

	int id;
	String sender_id;
	String receiver_id;
	String nstatus;
	String ntype;
	Date dateandtime;
	
	public Notifications
	(int id, String sender_id, String receiver_id, String nstatus, String ntype, Date dateandtime) {
		super();
		this.id = id;
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.nstatus = nstatus;
		this.ntype = ntype;
	
		this.dateandtime = dateandtime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getNstatus() {
		return nstatus;
	}
	public String getNtype() {
		return ntype;
	}

	public void setNtype(String ntype) {
		this.ntype = ntype;
	}
	public void setNstatus(String nstatus) {
		this.nstatus = nstatus;
	}
	public Date getDateandtime() {
		return dateandtime;
	}
	public void setDateandtime(Date dateandtime) {
		this.dateandtime = dateandtime;
	}
	
}
