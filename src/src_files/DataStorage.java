package src_files;

import java.util.ArrayList;
import java.util.Set;

public interface DataStorage {

	// register / login
	void createAccount(String loginID, String fname, String lname, String password, String type, String company); // done

	UserAccount login(String loginID, String password); // done

	// users
	String getUserFullName(String id); // returns first name and last name of the requested user

	// jobs
	ArrayList<Jobs> ViewJobsbyRecruiter(String loginID); //  can view the job shared by connected recruiter
	
	void ShareJob(String loginID, int jobID); // can share the job to connections
	
	void PostJob(String loginID, String jobTitle, String jobDesc); //post a job


	ArrayList<Jobs> jobRecommendations(String loginID); // give job recommendations based what connections posted

	// connections
	void sendRequestForConnection(String loginID, String requestedID); // if given id of sender and receiver it sends
																		// connection request to that person

	ArrayList<UserAccount> connectionRequests(String loginID); // view incoming connection request

	void updateConnection(String loginID, String requestedID, String status); // update connection request

	Set<UserAccount> viewConnectionProfile(String loginId); // can view profile of given connection

	ArrayList<UserAccount> getConnections(String loginID); // view connections profile [ as in people who are connected
															// to the id which is provided ]

	Set<UserAccount> connectionRecommendationsWithinOrganization(String loginID); // connection recommendation based on
																					// same company

	Set<UserAccount> connectionRecommendations2ndDegree(String loginID); // connection recommendation based on people
																			// who are already connected
	
	int ConnectionCount(String loginId);

	//notification
	ArrayList<Notifications> notifications(String loginID); // can see notification
	
	void updateNotification(int not_id,String updateStatus);
	
	//recommendation
	
	void sendRequestForRecommendation(String loginID, String requestedID); // sends Recommendation request to that person
	
	ArrayList<Recommendation> recommendations(String loginID); // can see recommendations
	
	void updateRecommendation(String loginID, String requestedID, String updateStatus); //update the recommendations
	
	
}
