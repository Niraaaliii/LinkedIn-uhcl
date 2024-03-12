package src_files;

import java.util.ArrayList;

public interface DataStorage {

	boolean testConnection();
	void createAccount(String loginID, String password,String type, String company);
	UserAccount login(String loginID, String password);

	ArrayList<Users> getUsers();
	ArrayList<Users> getConnections(String loginID);
	
}
