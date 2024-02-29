package src_files;

import java.util.ArrayList;
import java.util.Scanner;

public class Users {
	
	String loginId;
	String password;
	String accountType;
	String company;
	ArrayList<Users> connectedUsers = new ArrayList<>();
	
	
	public static void welcome(String user, String type) {
		if(type == "Regular") regularPage(user);
		else if(type == "Recruiter") recruiterPage(user);
	}
	
	public static void regularPage(String user) {
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		while(!selection.equals("x")) 
		{
			System.out.println();
			System.out.println("-----------------------------");
			System.out.println(user + "'s Home Page");
			System.out.println("-----------------------------");
			System.out.println("Please Select Option: ");
			System.out.println("1: Send Connection Request ");
			System.out.println("2: View Connection Profiles ");
			System.out.println("3: View or Share Jobs");
			System.out.println("4: Check Notifications");
			System.out.println("x: Exit");
			
			selection = input.nextLine();
			System.out.println();
			
			switch(selection) {
			case "1":	
				sendRequestForConnection();
			case "2":
				viewConnectionProfile();
			case "3":
				viewOrShareJobs();
			case "4":
				notifications();
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}
			 		 
		}
	}
	
	public static void recruiterPage(String user) {
		Scanner input = new Scanner(System.in);
		String selection = "";
		
		while(!selection.equals("x")) 
		{
			System.out.println();
			System.out.println("-----------------------------");
			System.out.println(user + "'s Home Page");
			System.out.println("-----------------------------");
			System.out.println("Please Select Option: ");
			System.out.println("1: Send Connection Request ");
			System.out.println("2: View Connection Profiles ");
			System.out.println("3: Post Job");
			System.out.println("4: View or Share Jobs");
			System.out.println("5: Check Notifications");
			System.out.println("x: Exit");
			
			selection = input.nextLine();
			System.out.println();
			
			switch(selection) {
			case "1":	
				sendRequestForConnection();
			case "2":
				viewConnectionProfile();
			case "3":
				viewOrShareJobs();
			case "4":
				notifications();
			case "x":
				break;
			default:
				System.out.println("Option invalid !!!");
			}
			 		 
		}
	}
	
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public static void sendRequestForConnection() {
		
	}
	
	public static void viewConnectionProfile() {
		
	}
	
	public static void viewOrShareJobs() {
		
	}
	
	public static void notifications() {
		
	}
	
}
