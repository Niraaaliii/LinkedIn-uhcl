package src_files;

import java.util.ArrayList;

public interface DataStorage {

	void createAccount(String loginID,String fname, String lname,  String password,String type, String company);
	UserAccount login(String loginID, String password);

	ArrayList<Users> getUsers();
	ArrayList<Users> getConnections(String loginID);
	String getUserFullName(String id);
	
	
	ArrayList<Users> viewConnectionProfile();
	ArrayList<Jobs> VieworShareJobs();
	void notifications();
	ArrayList<Users> connectionRecommendations(String loginID);
	ArrayList<String> jobRecommendations(String loginID);
	void sendRequestForConnection(String loginID, String requestedID);
}
