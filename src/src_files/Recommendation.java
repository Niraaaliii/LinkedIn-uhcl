package src_files;

public class Recommendation {

	int id;
	String senderId;
    String receiverId;
    String status;
    
    public Recommendation(int id, String senderId, String receiverId, String status) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
}
