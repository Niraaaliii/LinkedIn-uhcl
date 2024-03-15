package src_files;

import java.util.ArrayList;

public interface DataStorage {

	//register / login
	void createAccount(String loginID,String fname, String lname,  String password,String type, String company); //done
	UserAccount login(String loginID, String password);  //done

	//users
	//ArrayList<Users> getUsers(); //don't know if i have to implement this
	String getUserFullName(String id); //returns first name and last name  of the requested user
	
	//jobs
	ArrayList<Jobs> VieworShareJobs(); // can view or share the job to connections
	ArrayList<String> jobRecommendations(String loginID); // give job recommendations based what connections posted
	
	
	//connections
	//if given id of sender and receiver it sends connection request to that person
	void sendRequestForConnection(String loginID, String requestedID); 
	
	// view incoming connection request
	ArrayList<UserAccount> connectionRequests(String loginID); 
	
	//update connection request
	void updateConnection(String loginID, String requestedID, String status);
	
	//can view profile of given connection
	ArrayList<UserAccount> viewConnectionProfile(); 
	
	// view connections profile [ as in people who are connected to the id which is provided ]
	ArrayList<UserAccount> getConnections(String loginID); 
	
	//connection recommendation based on same company
	ArrayList<UserAccount> connectionRecommendationsWithinOrganization(String loginID); 
	
	//connection recommendation based on people who are already connected
	ArrayList<UserAccount> connectionRecommendations2ndDegree(String loginID); 
	
	//can see notification
	ArrayList<String> notifications();
}
