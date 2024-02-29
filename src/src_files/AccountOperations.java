package src_files;
import java.util.Scanner;

public class AccountOperations {
	
	DataStorage data;
	
	public AccountOperations(DataStorage d){
		data = d;
	}

	public void createAccount() {
		Scanner input = new Scanner(System.in);

		System.out.println("Please enter your username [loginID] : ");
		String loginID = input.nextLine();
		
		while(!isValidAccountID(loginID)) {
			System.out.println("**************************************************************");
			System.out.println("\n Username Invalid !! ");
			System.out.println("\n Please enter username which matches following criteria...  \n \n it should be between 3 and 10 characters, \n"
					+ "\n must contain at least one letter, \n"
					+ "\n one digit and one special character from {#, ? !, *}. \n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your username again : ");
			loginID = input.nextLine();
		}
		
		System.out.println("Please enter your password : ");
		String password = input.nextLine();
		
		while(password.compareToIgnoreCase(loginID)==0) {
			System.out.println("**************************************************************");
			System.out.println("\n\n Password and Username can not be similiar \n\n");
			System.out.println("**************************************************************");
			System.out.println("Please enter your password again: ");
			
			password = input.nextLine();
		}

		String accType = "Regular";

		System.out.println("Please enter your company name : ");
		String companyName = input.nextLine();
		
		data.createAccount(loginID,password,accType,companyName);

	}

	public void login() {
		Scanner input = new Scanner(System.in);

		System.out.println("Please enter your loginID");
		String id = input.next();
		System.out.println("Please enter your password");
		String password = input.next();

		data.login(id, password);
		
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
	

}
