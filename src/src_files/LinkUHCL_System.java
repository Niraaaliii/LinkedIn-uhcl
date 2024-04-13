package src_files;

import java.util.Scanner;

public class LinkUHCL_System {
	
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		String selection = "";
		DataStorage data = new SQL_DB();


		while (!selection.equals("x")) {
			
			System.out.println();
			System.out.println("*** Welcome to LinkUHCL ***");
			System.out.println("Please Select Option: ");
			System.out.println("1: Register ");
			System.out.println("2: Login ");
			System.out.println("x: Exit");

			selection = input.nextLine();
			System.out.println();
			
			if(selection.equals("1"))  new UserAccount_Creator(data).createAccount();
			else if(selection.equals("2"))   new UserAccount_Creator(data).login();
		}
	}
}
