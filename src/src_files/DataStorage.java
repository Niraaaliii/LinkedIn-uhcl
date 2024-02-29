package src_files;

public interface DataStorage {

	void createAccount(String loginID, String password,String type, String company);
	void login(String loginID, String password);
}
