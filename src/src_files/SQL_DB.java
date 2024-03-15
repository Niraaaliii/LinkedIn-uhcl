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
					return new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(6), resultSet.getString(5));
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

//	@Override
//	public ArrayList<UserAccount> getUsers() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ArrayList<UserAccount> getConnections(String loginID) {

		try {

			ArrayList<UserAccount> userConnections = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"Select c.requestedID, u.firstName , u.lastName ,u.type, u.company from connection c Join users u on c.requestedID = u.loginId "
							+ "where requesterID = '" + loginID + "' and status = 'approved'; ");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(5), resultSet.getString(4));
				userConnections.add(ua);
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

			if (resultSet.next()) {
				fullname = resultSet.getNString(2) + " " + resultSet.getString(3);
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
			statement.executeUpdate("INSERT INTO connection(requesterID, requestedID) VALUES ( '" + loginID + "','"
					+ requestedID + "')");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("Sent connection request to " + requestedID + " successfully !!! ");

		} catch (SQLException e) {
			System.out.println("connection request to " + requestedID + " failed !!! ");
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
	public ArrayList<UserAccount> viewConnectionProfile() {
		return null;

	}

	@Override
	public ArrayList<Jobs> VieworShareJobs() {
		return null;

	}

	@Override
	public ArrayList<String> notifications() {
		return null;

	}

	@Override
	public ArrayList<UserAccount> connectionRecommendationsWithinOrganization(String loginID) {

		try {

			ArrayList<UserAccount> connectionRec = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT loginID, firstName, lastName, company, type FROM users WHERE"
					+ " company = (SELECT company FROM users WHERE loginID = '" + loginID + "')" + " AND loginID != '"
					+ loginID + "' AND loginID NOT IN ( SELECT DISTINCT requestedID FROM connection WHERE requesterID = '"
					+ loginID + "'" + " AND status = 'approved' or status = 'pending' )");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				connectionRec.add(ua);
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
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public ArrayList<UserAccount> connectionRecommendations2ndDegree(String loginID) {

		try {

			ArrayList<UserAccount> connectionRec = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT c.requestedID, u.firstName , u.lastName , u.company, u.type  from "
							+ "connection c JOIN users u ON c.requestedID = u.loginID WHERE c.requesterID "
							+ "IN ( SELECT requestedID from connection WHERE requesterID = '" + loginID + "') ");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				connectionRec.add(ua);
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
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public ArrayList<UserAccount> connectionRequests(String loginID) {
		try {

			ArrayList<UserAccount> connectionRec = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT c.requesterID , u.firstName , u.lastName , u.company , u.type "
							+ "FROM connection c JOIN users u ON u.loginID = c.requesterID "
							+ "WHERE c.requestedID = '"+ loginID +"' AND c.status = 'pending'");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				connectionRec.add(ua);
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
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void updateConnection(String loginID, String requesterID, String status) {
		
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			statement.executeUpdate("UPDATE connection SET status = '"+ status +"' WHERE requesterID = '"+ requesterID  +"' AND requestedID = '"+ loginID +"'");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println(" connection request from " + requesterID + " " + status  +" successfully !!! ");
			
		} catch (SQLException e) {
			System.out.println(" connection request from " + requesterID + " failed to "+ status +" !!! ");
			e.printStackTrace();
			

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

}
