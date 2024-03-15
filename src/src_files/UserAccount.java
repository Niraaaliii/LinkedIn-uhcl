package src_files;

import java.util.ArrayList;
import java.util.Scanner;

public class UserAccount {

	private String loginID;
	private String firstName;
	private String lastName;
	private String company;
	private String type;

	private DataStorage data;

	public UserAccount(String l, String fname, String lname,String c, String ty) {
		loginID = l;
		firstName = fname;
		lastName = lname;
		company = c;
		type = ty;
	}

	public void WelcomePage(String userName) {
		
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("Welcome " + userName + " !!!");
		System.out.println("-----------------------------");
		System.out.println(userName + "'s Home Page \n ");
		
		showRecommendations(loginID);
		System.out.println(" --- Job Ads --- ");
		
		
		Scanner input = new Scanner(System.in);
		String selection = "";

		while (!selection.equals("x")) 
		{
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: View Incoming Connection Request ");
			System.out.println("2: View Connection Profiles ");
			System.out.println("3: View or Share Jobs");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":
				showIncomingConnectionRequests(loginID);
			case "2":
				showConnections(loginID);
			case "3":

			case "4":

			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}

		}
	}

	public void showConnections(String loginid) {
		ArrayList<UserAccount> userConn = data.getConnections(loginid);
		System.out.println("Connection List for " + loginid);
		int i = 1;
		for (UserAccount u : userConn) {
			
			
			if(u.getType().equals("Regular")) {
				System.out.print(u.getFirstName() + " " + u.getLastName() + " at " + u.getCompany() + ": ");
			}
			else System.out.print(u.getFirstName() + " " + u.getLastName() + " - " + u.getType() +" at " + u.getCompany() + ": ");
			
			i++;
		}

		
	}

	public void showRecommendations(String loginid) {
		
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		ArrayList<UserAccount> sameOrg = data.connectionRecommendationsWithinOrganization(loginid);
		ArrayList<UserAccount> SecondDegree = data.connectionRecommendations2ndDegree(loginid);
		
		System.out.println(" --- People in "+ getCompany()  +" --- ");
		System.out.println("\n --- Enter 'y' to send request to recommendations: --- \n");
		
		for (UserAccount u : sameOrg) {
			
			if(u.getType().equals("Regular")) {
				System.out.print(u.getFirstName() + " " + u.getLastName() + " at " + u.getCompany() + ": ");
			}
			else System.out.print(u.getFirstName() + " " + u.getLastName() + " - " + u.getType() +" at " + u.getCompany() + ": ");
			
			selection = input.nextLine();
			
			if(selection.equals("y")) {
				data.sendRequestForConnection(loginid, u.getLoginID());
			}
			else continue;
			
		}
		
		System.out.println(" --- People in your connections [ 2nd Degree ] --- ");
		System.out.println("\n --- Enter 'y' to send request to recommendations: --- \n");
		
		for (UserAccount u : SecondDegree) {
			
			if(u.getType().equals("Regular")) {
				System.out.print(u.getFirstName() + " " + u.getLastName() + " at " + u.getCompany() + ": ");
			}
			else System.out.print(u.getFirstName() + " " + u.getLastName() + " - " + u.getType() +" at " + u.getCompany() + ": ");
			
			
			selection = input.nextLine();
			
			if(selection.equals("y")) {
				data.sendRequestForConnection(loginid, u.getLoginID());
			}
			else continue;
			
		}
	}
	
	public void showIncomingConnectionRequests(String loginId) {
		ArrayList<UserAccount> connReq = data.connectionRequests(loginId);
		
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		System.out.println("\n --- Enter 'y' to accept connection request, 'n' to reject or press enter for next : --- \n");
		
		for (UserAccount u : connReq) {
			
			if(u.getType().equals("Regular")) {
				System.out.print(u.getFirstName() + " " + u.getLastName() + " at " + u.getCompany() + ": ");
			}
			else System.out.print(u.getFirstName() + " " + u.getLastName() + " - " + u.getType() +" at " + u.getCompany() + ": ");
			
			selection = input.nextLine();
			
			if(selection.equals("y")) {
				data.updateConnection(loginId, u.getLoginID(), "approved");
			}
			else if(selection.equals("n")){
				data.updateConnection(loginId, u.getLoginID(), "rejected");
			}
			else continue;
			
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
