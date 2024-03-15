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
	ResultSet resultSet1 = null;

	@Override
	public void createAccount(String loginID, String fname, String lname, String password, String type,
			String company) {

		try {
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("insert into users values " + "('" + loginID + "', '" + fname + "', '" + lname
					+ "', '" + password + "', '" + type + "', '" + company + "')");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("***New user account for " + fname + " " + lname + " created!***");
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
				if (password.equals(resultSet.getString(4))) {
					return new UserAccount(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
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

		try {

			ArrayList<Users> userConnections = new ArrayList<Users>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"Select c.requestedID, u.firstName , u.lastName , u.company from connection c Join users u on c.requestedID = u.loginId "
					+ "where requesterID = '" + loginID + "' and status = 'approved'; ");

			while (resultSet.next()) {
				Users u = new Regular(resultSet.getString(1),
						resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
				userConnections.add(u);
			}

			return userConnections;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;

		} finally {
			try {
				connection.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public String getUserFullName(String id) {
		try {

			String fullname = "";
					
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from users " + "where loginID = '" + id + "'");

			if(resultSet.next()) {
				fullname = resultSet.getNString(2) + " " +resultSet.getString(3);
			}
			
			return fullname;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;

		} finally {
			try {
				connection.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public ArrayList<String> jobRecommendations(String loginID) {
		return null;
		
		
	}

	@Override
	public void sendRequestForConnection(String loginID, String requestedID) {
		
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO connection(requesterID, requestedID) VALUES ( '"+ loginID +"','"+ requestedID +"')");
			
			connection.setAutoCommit(false);
			connection.commit();
            
			System.out.println("Sent connection request to "+ requestedID +" successfully !!! ");
			
		} catch (SQLException e) {
			System.out.println("connection request to "+ requestedID +" failed !!! ");
			e.printStackTrace();
			
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public ArrayList<Users> viewConnectionProfile() {
		return null;
		
		
	}

	@Override
	public ArrayList<Jobs> VieworShareJobs() {
		return null;
		
		
	}

	@Override
	public void notifications() {
		
		
	}

	@Override
	public ArrayList<Users> connectionRecommendations(String loginID) {
		
		try {

			
			ArrayList<Users> connectionRec = new ArrayList<Users>();
			
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			
			//-- Part 1: Recommendations within the Same Organization or Company and not already connected
			resultSet = statement.executeQuery("SELECT loginID, firstName, lastName, company FROM users WHERE"
					+ " company = (SELECT company FROM users WHERE loginID = '" + loginID + "')"
					+ " AND loginID != '" + loginID + "'"
					+ " AND loginID NOT IN ( SELECT DISTINCT requestedID FROM connection WHERE requesterID = '" + loginID + "'"
					+ " AND status = 'approved' or status = 'pending' )");
			
			/*
			 * SELECT loginID, firstName, lastName, company FROM users WHERE 
			 * company = (SELECT company FROM users WHERE loginID = '') AND loginID != ''
			 * AND loginID NOT IN ( SELECT DISTINCT requestedID FROM connection WHERE requesterID = '' 
			 * AND status = 'approved' or status = 'pending' )
			 */
			
			while(resultSet.next()) { 
				Users u = new Regular(resultSet.getString(1),
					resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
				connectionRec.add(u);
			}
			
			//-- Part 2: Second-Degree Connections
			resultSet1 = statement.executeQuery("SELECT c.requestedID, u.firstName , u.lastName , u.company from connection c "
					+ "JOIN users u ON c.requestedID = u.loginID WHERE c.requesterID IN "
					+ "( SELECT c1.requestedID from connection c1 WHERE c1.requesterID = '"+ loginID + "') and status = 'approved'");
			
			/*
			 * SELECT c.requestedID, u.firstName , u.lastName , u.company from connection c 
			 * JOIN users u ON c.requestedID = u.loginID WHERE c.requesterID IN 
			 * ( SELECT c1.requestedID from connection c1 WHERE c1.requesterID = '')
			 */
			
			
			while(resultSet1.next()) {
				Users u = new Regular(resultSet1.getString(1),
						resultSet1.getString(2),resultSet1.getString(3),resultSet1.getString(4));
				
				if(!connectionRec.contains(u)) connectionRec.add(u);
			}
			
			return connectionRec;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				connection.close();
				statement.close();
				resultSet.close();
				resultSet1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
	}
	



}
