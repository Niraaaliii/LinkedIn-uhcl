package src_files;

import java.util.ArrayList;
import java.util.Scanner;

public class UserAccount {

	private String loginID;
	private String firstName;
	private String lastName;
	private String psw;

	private DataStorage data;

	public UserAccount(String l, String fname, String lname, String p) {
		loginID = l;
		firstName = fname;
		lastName = lname;
		psw = p;
	}

	public void WelcomePage(String userName) {
		
		System.out.println();
		System.out.println("-----------------------------");
		System.out.println("Welcome " + userName + " !!!");
		System.out.println("-----------------------------");
		System.out.println(userName + "'s Home Page \n ");
		System.out.println(" --- Recommendations of Connections and 2nd Connections --- ");
		showRecommendations(loginID);
		System.out.println(" --- Job Ads --- ");
		
		Scanner input = new Scanner(System.in);
		String selection = "";

		while (!selection.equals("x")) 
		{
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println("1: Send Connection Request ");
			System.out.println("2: View Connection Profiles ");
			System.out.println("3: View or Share Jobs");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();

			switch (selection) {
			case "1":

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
		ArrayList<Users> userConn = data.getConnections(loginid);
		System.out.println("Connection List for " + loginid);
		int i = 1;
		for (Users u : userConn) {
			System.out.println( u.getFirstName() + " " + u.getLastName()+ " at " + u.getCompany());
			i++;
		}

		
	}

	public void showRecommendations(String loginid) {
		
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		ArrayList<Users> listOfRecomm = data.connectionRecommendations(loginid);
		System.out.println("\n --- Enter 'y' to send request to recommendations: --- \n");
		
		for (Users u : listOfRecomm) {
			
			System.out.print(u.getFirstName() + " " + u.getLastName() + " at " + u.getCompany() + ": ");
			
			selection = input.nextLine();
			
			if(selection.equals("y")) {
				data.sendRequestForConnection(loginid, u.getLoginId());
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

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
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

}
