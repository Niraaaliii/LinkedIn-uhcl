package src_files;

import java.sql.*;
import java.util.ArrayList;

public class SQL_DB implements DataStorage {

	final String DATABASE_URL = "jdbc:mysql://cobmysql.uhcl.edu/rathodn8713?useSSL=false";
	final String db_id = "rathodn8713";
	final String db_psw = "2248713";

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	public boolean testConnection() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			if(connection != null) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void createAccount(String loginID, String password, String type, String company) {

		try {
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("insert into users values " + "('" + loginID + "', '" + password + "', '" + type
					+ "', '" + company + "')");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("***New user account named "+ loginID +" created!***");
			System.out.println();

		} catch (SQLException e) {
			System.out.println("Account creation failed");
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public UserAccount login(String loginID, String password) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from users " + "where loginID = '" + loginID + "'");

			if (resultSet.next()) {
				if (password.equals(resultSet.getString(2))) {
					return new UserAccount(resultSet.getString(1),resultSet.getString(2));
				} else {
					return null;
				}
			} else {
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

	@Override
	public ArrayList<Users> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Users> getConnections(String loginID) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void jobRecommendations(String loginID) {
//		
//		
//	}
//
//	@Override
//	public void sendRequestForConnection(String loginID, String requestedID) {
//		
//		try {
//
//			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
//			statement = connection.createStatement();
//			statement.executeUpdate("INSERT INTO connection(requesterID, requestedID) VALUES ( '"+ loginID +"','"+ requestedID +"')");;
//			
//			connection.setAutoCommit(false);
//			connection.commit();
//            
//			System.out.println("Sent connection request to "+ requestedID +" successfully !!! ");
//			
//		} catch (SQLException e) {
//			System.out.println("connection request to "+ requestedID +" failed !!! ");
//			e.printStackTrace();
//			
//		} finally {
//			try {
//				connection.close();
//				statement.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	@Override
//	public void viewConnectionProfile() {
//		
//		
//	}
//
//	@Override
//	public void VieworShareJobs() {
//		
//		
//	}
//
//	@Override
//	public void notifications() {
//		
//		
//	}
//
//	@Override
//	public void connectionRecommendations(String loginID) {
//		
//		try {
//
//			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery("");
//
//			
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				connection.close();
//				statement.close();
//				resultSet.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
//		
//	}
//	
	

}
