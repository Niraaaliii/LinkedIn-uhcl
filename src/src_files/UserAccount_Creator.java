package src_files;

import java.util.Scanner;

public class UserAccount_Creator {
	private Scanner input = new Scanner(System.in);

	private UserAccount theUserAccount;

	DataStorage data;

	public UserAccount_Creator(DataStorage d) {
		data = d;
		theUserAccount = null;
	}
	


	public void createAccount() {

		System.out.println("Please enter your username [loginID] : ");
		String loginID = input.nextLine();

		while (!isValidAccountID(loginID)) {
			System.out.println("**************************************************************");
			System.out.println("\n Username Invalid !! ");
			System.out.println(
					"\n Please enter username which matches following criteria...  \n \n it should be between 3 and 10 characters, \n"
							+ "\n must contain at least one letter, \n"
							+ "\n one digit and one special character from {#, ? !, *}. \n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your username again : ");
			loginID = input.nextLine();
		}
		
		System.out.println("Please enter your First Name : ");
		String fname = input.nextLine();
		
		while (fname.isEmpty()) {
			System.out.println("**************************************************************");
			System.out.println("\n\n First Name Invalid \n\n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your First Name : ");
			fname = input.nextLine();
		}
		
		System.out.println("Please enter your Last Name : ");
		String lname = input.nextLine();
		
		while (lname.isEmpty()) {
			System.out.println("**************************************************************");
			System.out.println("\n\n Last Name Invalid \n\n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your Last Name : ");
			lname = input.nextLine();
		}

		System.out.println("Please enter your password : ");
		String password = input.nextLine();

		while (!isValidPassoword(loginID, password)) {
			System.out.println("**************************************************************");
			System.out.println("\n\n Password Invalid \n\n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your password again: ");

			password = input.nextLine();
		}

		String accType = "Regular";

		System.out.println("Please enter your company name : ");
		String companyName = input.nextLine();

		while (companyName.isEmpty()) {
			System.out.println("**************************************************************");
			System.out.println("\n\n Company Name Invalid \n\n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your company name : ");
			companyName = input.nextLine();
		}

		data.createAccount(loginID, fname, lname, password, accType, companyName);

	}

	public void login() {

		System.out.println("Please enter your loginID");
		String id = input.next();
		System.out.println("Please enter your password");
		String password = input.next();

		theUserAccount = data.login(id, password);
		String fullUserName = data.getUserFullName(id);
		if (theUserAccount != null) {
			theUserAccount.setData(data);
			if(theUserAccount.getType().equals("Regular"))
				theUserAccount.WelcomePageRegular(fullUserName);
			else
				theUserAccount.WelcomePageRecruiter(fullUserName);
		} else {
			System.out.println("The login failed");
			System.out.println();
		}

	}

	public static boolean isValidAccountID(String id) {
		if (id == null || id.length() < 3 || id.length() > 10) {
			return false;
		}

		boolean hasLetter = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;

		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);

			if (Character.isLetter(ch)) {
				hasLetter = true;
			} else if (Character.isDigit(ch)) {
				hasDigit = true;
			} else if (ch == '#' || ch == '?' || ch == '!' || ch == '*') {
				hasSpecial = true;
			}
		}

		return hasLetter && hasDigit && hasSpecial; // Ensure all conditions are met
	}

	public static boolean isValidPassoword(String loginID, String password) {
		if (password == null || password.length() <= 0 || password.compareTo(loginID) == 0) {
			return false;
		}
		return true;
	}
	
	
}
