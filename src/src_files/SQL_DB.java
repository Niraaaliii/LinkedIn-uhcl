package src_files;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	public ArrayList<Jobs> jobRecommendations(String loginID) {
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
	public Set<UserAccount> viewConnectionProfile(String loginId) {
		
		try {

			Set<UserAccount> connProfile = new HashSet<>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("Select loginID , firstName , lastName , company , type from users where loginID = '" + loginId + "'");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				connProfile.add(ua);
			}

			return connProfile;
			
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
	public ArrayList<String> notifications() {
		return null;

	}

	@Override
	public Set<UserAccount> connectionRecommendationsWithinOrganization(String loginID) {

		try {

			Set<UserAccount> connectionRec = new HashSet<UserAccount>();

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
	public Set<UserAccount> connectionRecommendations2ndDegree(String loginID) {

		try {

			Set<UserAccount> connectionRec = new HashSet<UserAccount>();

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

	@Override
	public int ConnectionCount(String loginId) {
		try {

			int connCount  = 0 ;

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT COUNT(connectionID) as con_count  FROM connection "
							+ "WHERE requesterID = '"+ loginId +"' AND status = 'approved'");

			while (resultSet.next()) {
				connCount = resultSet.getInt("con_count");
			}

			return connCount;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;

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
	public void ShareJob(String loginID, int jobID) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO jobsharing(jobID, userID) VALUES ( '"+jobID+"', '"+loginID+"')");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("Shared Job successfully !!! ");

		} catch (SQLException e) {
			System.out.println("Failed to Share Job !!! ");
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
	public void PostJob(String loginID, String jobTitle, String jobDesc) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO job(creator, jobTitle, jobDesc) "
					+ "VALUES ('"+loginID+"','"+jobTitle+"', '"+jobDesc+"' )");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("Posted Job successfully !!! ");

		} catch (SQLException e) {
			System.out.println("Failed to Post Job !!! ");
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
	public ArrayList<Jobs> ViewJobsbyRecruiter(String loginID) {
		
		try {

			ArrayList<Jobs> jobsByRecruiter = new ArrayList<Jobs>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT j.jobID,  j.creator , j.jobTitle , j.jobDesc , j.dateandtime as date_of_posting from job j join connection c "
							+ "on c.requestedID = j.creator WHERE c.requesterID = '"+ loginID+"' AND c.status = 'approved';");

			while (resultSet.next()) {
				Jobs j = new Jobs( resultSet.getInt(1), resultSet.getString(4) ,resultSet.getString(2) , resultSet.getString(3) ,resultSet.getDate("date_of_posting") );
				jobsByRecruiter.add(j);
			}

			return jobsByRecruiter;
			
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
	public ArrayList<Jobs> ViewJobsbyConnection(String loginID) {
		
		return null;
	}

}
