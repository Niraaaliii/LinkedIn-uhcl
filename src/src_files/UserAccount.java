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

		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("~~~ Welcome " + userName + " !!! ~~~");
		System.out.println("-----------------------------");
		System.out.println(userName + "'s Home Page \n ");
		showRecommendations(loginID);
		System.out.println(" --- Job Ads --- ");

		String selection = "";

		while (!selection.equals("x")) {
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: View Incoming Connection Requests ");
			System.out.println("2: View Your Connections  ");
			System.out.println("3: View Jobs ");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":
				showIncomingConnectionRequests(loginID);
				continue;
			case "2":
				showConnections(loginID);
				continue;
			case "3":
				showJobs(loginID);
				continue;
			case "4":
				
				continue;
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}

		}
	}
	
	public void WelcomePageRecruiter(String userName) {

		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("~~~ Welcome " + userName + " !!! ~~~");
		System.out.println("-----------------------------");
		System.out.println(userName + "'s Home Page \n ");
		showRecommendations(loginID);

		String selection = "";

		while (!selection.equals("x")) {
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: View Incoming Connection Requests ");
			System.out.println("2: View Your Connections  ");
			System.out.println("3: Post Job ");
			System.out.println("3: View Posted Jobs ");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":
				showIncomingConnectionRequests(loginID);
				continue;
			case "2":
				showConnections(loginID);
				continue;
			case "3":
				postJob(loginID);
				continue;
			case "4":
				viewPostedJob(loginID);
				continue;
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}

		}
	}
	
	
	public void viewPostedJob(String loginID2) {
		
		
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
					
				} else
					continue;
			}
		} else {
			System.out.println("--- No Connection List Available For " + getFirstName() + " " + getLastName() + " ---");
		}
	}

	public void showRecommendations(String loginid) {

		String selection = "";

		Set<UserAccount> sameOrg = data.connectionRecommendationsWithinOrganization(loginid);
		Set<UserAccount> SecondDegree = data.connectionRecommendations2ndDegree(loginid);

		if (!sameOrg.isEmpty()) {

			System.out.println(" --- People in " + getCompany() + " --- ");
			System.out.println("\n --- Enter 'y' to send request to recommendations: --- \n");

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

			System.out.println(" --- People in your connections [ 2nd Degree ] --- ");
			System.out.println("\n --- Enter 'y' to send request to recommendations: --- \n");

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

	public void showIncomingConnectionRequests(String loginId) {
		ArrayList<UserAccount> connReq = data.connectionRequests(loginId);

		String selection = "";

		if (!connReq.isEmpty()) {

			System.out.println(
					"\n --- Enter 'y' to accept connection request, 'n' to reject or press enter for next : --- \n");

			for (UserAccount u : connReq) {

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
	}

	public void printJob(Jobs jb) {
		
		System.out.println("-----------------------------");
		System.out.println(" Job Posted by : " + data.getUserFullName(jb.getCreator()) + " On " + jb.getDateandtime());
		System.out.println(" Job Title : " +  jb.getJobTitle());
		System.out.println(" Job Description : " +  jb.getJobDesc());
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
		Set<UserAccount> conProf =  data.viewConnectionProfile(profileID);
		
		if(!conProf.isEmpty()) {
			for(UserAccount u : conProf) {
				System.out.println("-------------------------------------------------------");
				System.out.println( data.getUserFullName(profileID)   + "'s Profle");
				System.out.println("Full Name : " + data.getUserFullName(profileID));
				System.out.println("Company : " + u.getCompany());
				if(u.getType().equals("Regular"))
					System.out.println("Position : Staff");
				else
					System.out.println("Position : "+ u.getType());
				System.out.println("No. of Connections : " + data.ConnectionCount(profileID) );
				System.out.println("-------------------------------------------------------");
				
			}
		}
		else {
			System.out.println("--- No profile found !!! ---");
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
