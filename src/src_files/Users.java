package src_files;

public abstract class Users {
	
	private String loginId;
	private String password;
	private String accountType;
	private String company;
	
	public Users(String loginId, String password, String accountType, String company) {
		this.loginId = loginId;
		this.password = password;
		this.accountType = accountType;
		this.company = company;
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
	
}
