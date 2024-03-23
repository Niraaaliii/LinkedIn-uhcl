package src_files;

public class Recommendation {

	int id;
	String sender_id;
	String receiver_id;
	String status;
	
	public Recommendation(int id, String sender_id, String receiver_id, String status) {
		super();
		this.id = id;
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
