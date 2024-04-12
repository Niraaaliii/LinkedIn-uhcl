package src_files;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class UserAccount {

	public Scanner input = new Scanner(System.in);

	private String loginID;
	private String firstName;
	private String lastName;
	private String company;
	private String type;

	private DataStorage data;

	public UserAccount(String l, String fname, String lname, String c, String ty) {
		loginID = l;
		firstName = fname;
		lastName = lname;
		company = c;
		type = ty;
	}

	public void WelcomePageRegular(String userName) {

		String choice = "";

		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("~~~ Welcome " + userName + " !!! ~~~");
		System.out.println("-----------------------------");
		showNotifications(loginID);
		System.out.println("\n Want to see connection recommendations ? enter 'y' for yes : ");
		choice = input.nextLine();
		System.out.println();

		if (choice.equals("y"))
			showConnectionRecommendations(loginID);

		choice = "";

		System.out.println("\n Want to see top 3 job recommendations ? enter 'y' for yes : ");
		choice = input.nextLine();
		System.out.println();

		if (choice.equals("y"))
			showTOP3JobRecommendations(loginID);

		String selection = "";

		while (!selection.equals("x")) {
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: Search User ");
			System.out.println("2: View Incoming Requests ");
			System.out.println("3: View Your Connections  ");
			System.out.println("4: View Jobs By Recruiter ");
			System.out.println("5: View Your Recommendations  ");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":
				searchUser();
				continue;
			case "2":
				showIncomingRequests(loginID);
				continue;
			case "3":
				showConnections(loginID);
				continue;
			case "4":
				showJobs(loginID);
				continue;
			case "5":
				showRec(loginID);
				continue;
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}

		}
	}

	public void WelcomePageRecruiter(String userName) {

		String choice = "";

		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("~~~ Welcome " + userName + " !!! ~~~");
		System.out.println("-----------------------------");
		showNotifications(loginID);
		System.out.println("Want to see connection recommendations ? enter 'y' for yes : ");
		choice = input.nextLine();
		System.out.println();

		if (choice.equals("y"))
			showConnectionRecommendations(loginID);

		String selection = "";

		while (!selection.equals("x")) {
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: Search User");
			System.out.println("2: Post Job ");
			System.out.println("3: View Incoming Requests ");
			System.out.println("4: View Your Connections  ");
			System.out.println("5: View Your Recommendations  ");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":
				searchUser();
				continue;
			case "2":
				postJob(loginID);
				continue;
			case "3":
				showIncomingRequests(loginID);
				continue;
			case "4":
				showConnections(loginID);
				continue;
			case "5":
				showRec(loginID);
				continue;
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}

		}
	}

	public void showRec(String loginID) {

		ArrayList<Recommendation> recs = data.recommendations(loginID);

		if (!recs.isEmpty()) {

			System.out.println("--- Recommendations  ---");
			for (Recommendation r : recs) {
				
				if(r.getSenderId().equals(loginID)) 
					System.out.println("You have been recommended by " +  data.getUserFullName(r.getReceiverId()));
				else if(r.getReceiverId().equals(loginID))
					System.out.println("You recommended " + data.getUserFullName(r.getSenderId()));
				//printUserProfile(r.getLoginID());
			}
		} else {
			System.out.println("--- No Recommendations  ---");
		}

	}

	public void searchUser() {

		String selection = "";
		System.out.print("Search :");
		selection = input.nextLine();

		ArrayList<UserAccount> searches = data.searchUser(selection, getLoginID());

		if (!searches.isEmpty()) {
			for (UserAccount ua : searches) {
				printUserAccount(ua);

				System.out.println(" Enter 'y' to send connection request : ");
				selection = input.nextLine();

				if (selection.equals("y")) {
					data.sendRequestForConnection(getLoginID(), ua.getLoginID());
				} else
					continue;
			}
		} else {
			System.out.println("--- No Search Result Found ---");
		}
	}

	public void showNotifications(String loginID) {

		String selection = "";

		ArrayList<Notifications> nt = data.notifications(loginID);

		if (!nt.isEmpty()) {
			System.out.println("--- New Notifications ---");

			for (Notifications n : nt) {

				printNotification(n);

				System.out.println(" Enter 'y' to approve  or 'n' to reject :");

				selection = input.nextLine();

				if (selection.equals("y")) {
					if (n.getNtype().equals("Connection"))
						data.updateConnection(loginID, n.getSender_id(), "approved");
					if (n.getNtype().equals("Recommendation"))
						data.updateRecommendation(loginID, n.getSender_id(), "Approved");
					
					
					
				} else if (selection.equals("n")) {
					if (n.getNtype().equals("Connection"))
						data.updateConnection(loginID, n.getSender_id(), "rejected");
					if (n.getNtype().equals("Recommendation"))
						data.updateRecommendation(loginID, n.getSender_id(), "Rejected");
					
					
				}

				data.updateNotification(n.getId(), "Seen");
			}
		} else {
			System.out.println("--- No New Notifications --- \n");
		}
	}

	public void printNotification(Notifications n) {
		System.out.println("-----------------------------");
		System.out.println(" Notification from " + data.getUserFullName(n.getSender_id()));
		System.out.println(data.getUserFullName(n.getSender_id()) + " has sent " + n.getNtype() + " request ");
		System.out.println("-----------------------------");
		System.out.println("");
	}

	public void postJob(String loginID) {

		System.out.println("Enter Job Title");
		String title = input.next();

		System.out.println("Enter Job Description");
		String desc = input.next();

		data.PostJob(loginID, title, desc);

	}

	public void showJobs(String loginID) {

		String selection = "";

		ArrayList<Jobs> jobsByRec = data.ViewJobsbyRecruiter(loginID);

		if (!jobsByRec.isEmpty()) {
			System.out.println("--- New Jobs Posted By Recruiters ---");
			System.out.println("--- Enter 'y' to share job to your connections  ---");

			for (Jobs j : jobsByRec) {

				printJob(j);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.ShareJob(loginID, j.getJobID());

				} else
					continue;
			}
		} else {
			System.out.println("--- No New Jobs Posted By Recruiters  ---");
		}

	}

	public void showConnections(String loginid) {

		String selection = "";

		ArrayList<UserAccount> userConn = data.getConnections(loginid);

		if (!userConn.isEmpty()) {
			System.out.println("--- Connection List for " + getFirstName() + " " + getLastName() + " ---");
			System.out.println("--- Enter 'v' to view connection profile  ---");

			for (UserAccount u : userConn) {

				printUserAccount(u);

				selection = input.nextLine();

				if (selection.equals("v")) {
					printUserProfile(u.getLoginID());

					System.out.println("--- Enter 's' to send request for recommendation : ");

					selection = input.nextLine();

					if (selection.equals("s"))
						data.sendRequestForRecommendation(loginid, u.getLoginID());
					else
						continue;

					selection = "";

				} else
					continue;
			}
		} else {
			System.out.println("--- No Connection List Available For " + getFirstName() + " " + getLastName() + " ---");
		}
	}

	public void showConnectionRecommendations(String loginid) {

		String selection = "";

		Set<UserAccount> sameOrg = data.connectionRecommendationsWithinOrganization(loginid);
		Set<UserAccount> SecondDegree = data.connectionRecommendations2ndDegree(loginid);

		if (!sameOrg.isEmpty()) {

			System.out.println("\n     --- People in " + getCompany() + " ---     ");
			System.out.println("\n--- Enter 'y' to send request to recommendations: --- \n");

			for (UserAccount u : sameOrg) {

				printUserAccount(u);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.sendRequestForConnection(loginid, u.getLoginID());
				} else
					continue;

			}
		} else {
			System.out.println("--- No Recommendations Available Within " + getCompany() + " ---");
		}
		if (!SecondDegree.isEmpty()) {

			System.out.println("\n    --- People in your connections [ 2nd Degree ] ---     ");
			System.out.println("\n--- Enter 'y' to send request to recommendations: --- \n");

			for (UserAccount u : SecondDegree) {

				printUserAccount(u);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.sendRequestForConnection(loginid, u.getLoginID());
				} else
					continue;

			}
		} else {
			System.out.println("--- No 2nd Degree Recommendations Available ---");
		}

	}

	public void showIncomingRequests(String loginId) {
		ArrayList<UserAccount> Connreq = data.viewIncomingConnRequests(loginId);
		
		String selection = "";

		if (!Connreq.isEmpty()) {

			System.out.println(" ------ Incoming Connection Requests ------ ");
			System.out.println(
					"\n --- Enter 'y' to accept connection request, 'n' to reject or press enter for next : --- \n");

			for (UserAccount u : Connreq) {

				printUserAccount(u);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.updateConnection(loginId, u.getLoginID(), "approved");
					
				} else if (selection.equals("n")) {
					data.updateConnection(loginId, u.getLoginID(), "rejected");
					
				} else
					continue;

			}
		} else {
			System.out.println("--- No Incoming Connection Request Available ---");
		}
		
		ArrayList<UserAccount> Recreq = data.viewIncomingRecRequests(loginId);
		
		if (!Recreq.isEmpty()) {

			System.out.println(" ------ Incoming Recommendation Requests ------ ");
			System.out.println(
					"\n --- Enter 'y' to accept connection request, 'n' to reject or press enter for next : --- \n");

			for (UserAccount u : Recreq) {

				printUserAccount(u);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.updateRecommendation(loginId, u.getLoginID(), "Approved");
				} else if (selection.equals("n")) {
					data.updateRecommendation(loginId, u.getLoginID(), "Rejected");
				} else
					continue;

			}
		} else {
			System.out.println("--- No Incoming Recommendation Request Available ---");
		}
	}

	public void printJob(Jobs jb) {

		System.out.println("-----------------------------");
		System.out.println(" Job Posted by : " + data.getUserFullName(jb.getCreator()) + " On " + jb.getDateandtime());
		System.out.println(" Job Title : " + jb.getJobTitle());
		System.out.println(" Job Description : " + jb.getJobDesc());
		System.out.println("-----------------------------");
		System.out.print(" Do you want to share it  ? ");
		System.out.println("");

	}

	public void printUserAccount(UserAccount ua) {
		if (ua.getType().equals("Regular")) {
			System.out.print(ua.getFirstName() + " " + ua.getLastName() + " at " + ua.getCompany() + ": ");
		} else
			System.out.print(ua.getFirstName() + " " + ua.getLastName() + " - " + ua.getType() + " at "
					+ ua.getCompany() + ": ");
	}

	public void printUserProfile(String profileID) {
		UserAccount conProf = data.viewConnectionProfile(profileID);

		if (conProf != null) {

			System.out.println("-------------------------------------------------------");
			System.out.println(data.getUserFullName(profileID) + "'s Profle");
			System.out.println("Full Name : " + data.getUserFullName(profileID));
			System.out.println("Company : " + conProf.getCompany());
			if (conProf.getType().equals("Regular"))
				System.out.println("Position : Staff");
			else
				System.out.println("Position : " + conProf.getType());
			System.out.println("No. of Connections : " + data.ConnectionCount(profileID));
			System.out.println("-------------------------------------------------------");

		} else {
			System.out.println("--- No profile found !!! ---");
		}
	}

	public void showTOP3JobRecommendations(String loginid) {

		String selection = "";

		ArrayList<Jobs> jobRec = data.jobRecommendations(loginid);

		if (!jobRec.isEmpty()) {

			System.out.println("\n    --- Job Ads --- \n");
			System.out.println(" --- Recent Jobs Shared By Your Connections --- ");
			System.out.println("\n --- Enter 'y' to share with your connections: --- \n");

			for (Jobs j : jobRec) {

				printJob(j);

				selection = input.nextLine();

				if (selection.equals("y")) {
					data.ShareJob(loginid, j.getJobID());
				} else
					continue;

			}
		} else {
			System.out.println("--- No Recent Job Recommendations ---");
		}

	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public DataStorage getData() {
		return data;
	}

	public void setData(DataStorage data) {
		this.data = data;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
