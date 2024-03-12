package src_files;

import java.util.ArrayList;
import java.util.Scanner;

public class UserAccount {

	private String loginID;
	private String psw;
	private ArrayList<Users> connectedUsers = new ArrayList<Users>();
	private DataStorage data;
	
	public UserAccount(String l, String p) {
		loginID = l;
		psw = p;
	}
	
	public void WelcomePage(String user) {
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		while(!selection.equals("x")) 
		{
			System.out.println();
			System.out.println("-----------------------------");
			System.out.println("Welcome " + user + " !!!");
			System.out.println("-----------------------------");
			System.out.println("Please Select Option from Below: ");
			System.out.println("-----------------------------");
			System.out.println(user + "'s Home Page");
			System.out.println(" --- Recommendations of Connections and 2nd Connections --- ");
			
			System.out.println(" --- Job Ads --- ");
			
			System.out.println("-----------------------------");
			System.out.println("1: Send Connection Request ");
			System.out.println("2: View Connection Profiles ");
			System.out.println("3: View or Share Jobs");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");
			
			selection = input.nextLine();
			System.out.println();
			
			switch(selection) {
			case "1":	
				
			case "2":
				
			case "3":
				
			case "4":
				
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}
			 		 
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
	
	

}
