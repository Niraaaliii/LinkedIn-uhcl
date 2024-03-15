package src_files;

public abstract class Users {
	
	private String loginId;
	private String firstName;
	private String lastName;
	private String password;
	private String accountType;
	private String company;
	
	public Users(String loginId,String firstName,String lastName, String password, String accountType, String company) {
		this.loginId = loginId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.accountType = accountType;
		this.company = company;
	}

	public Users(String loginId, String firstName, String lastName, String company) {
		this.loginId = loginId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	
	public void save(Users u) {
		this.save(u);
	}
	
}
