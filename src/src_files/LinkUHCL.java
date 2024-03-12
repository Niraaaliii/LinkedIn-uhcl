package src_files;

public class LinkUHCL {
	public static void main(String[] args) {
		DataStorage data = new SQL_DB();
		new LinkUHCL_System(data).showMainPage();
	}
}
