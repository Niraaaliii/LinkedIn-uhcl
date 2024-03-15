package src_files;

public class Recruiter extends Users {

	public Recruiter(String loginId,String fname, String lname, String password, String accountType, String company) {
		super(loginId,fname,lname, password, accountType, company);
	}

	public Recruiter(String loginId,String fname, String lname, String company) {
		super(loginId,fname,lname, company);
	}
}
