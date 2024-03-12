package src_files;

public class Request {

	String requestID;
	String requesterID; //requesting
	String requestedID; //requested
	String status;
	String reqType;
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getRequesterID() {
		return requesterID;
	}
	public void setRequesterID(String requesterID) {
		this.requesterID = requesterID;
	}
	public String getRequestedID() {
		return requestedID;
	}
	public void setRequestedID(String requestedID) {
		this.requestedID = requestedID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	
}
