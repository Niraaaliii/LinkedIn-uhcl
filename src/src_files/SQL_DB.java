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
	ResultSet resultSet = null , resultSet1 = null;

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
					"Select requestedID, u.firstName , u.lastName ,u.type, u.company from connection c Join users u on c.requestedID = u.loginId "
							+ "where status = 'approved' and requesterID = '" + loginID + "' UNION Select requesterID, u.firstName , u.lastName ,"
							+ "u.type, u.company from connection c Join users u on c.requesterID = u.loginId where status = 'approved'"
							+ "and requestedID = '" + loginID + "'");

			
			/*
			 * SELECT * FROM `connection` WHERE `requesterID` = 'lin#111' AND status = 'approved' 
			UNION
				SELECT * FROM `connection` WHERE `requestedID` = 'lin#111' AND status = 'approved';
			 * 
			 */
			
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
	public void sendRequestForConnection(String loginID, String requestedID) {

		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO connection(requesterID, requestedID) VALUES ( '" + loginID + "','"
					+ requestedID + "')");

			statement.executeUpdate("INSERT INTO notification(sender_user_id, receiver_user_id,ntype) VALUES ('"+loginID+"', '"+requestedID+"' , 'Connection' )");
			
			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("Sent connection request to " + requestedID + " successfully !!! ");
			System.out.println("Sent Notification for connection request to " + requestedID + " successfully !!! ");

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
	public UserAccount viewConnectionProfile(String loginId) {
		
		try {

			UserAccount connProfile = null;

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("Select loginID , firstName , lastName , company , type from users where loginID = '" + loginId + "'");

			while (resultSet.next()) {
				connProfile = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				
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
	public ArrayList<Notifications> notifications(String loginID) {
		try {

			ArrayList<Notifications> not = new ArrayList<Notifications>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT * FROM notification WHERE receiver_user_id = '"+loginID+"' AND nstatus = 'Not Seen';");

			while (resultSet.next()) {
				Notifications n = new Notifications(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), 
						resultSet.getString(5), resultSet.getDate("time"));
				not.add(n);
			}

			return not;
			
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
	public Set<UserAccount> connectionRecommendationsWithinOrganization(String loginID) {

		try {

			Set<UserAccount> connectionRec = new HashSet<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery("SELECT loginID, firstName, lastName, company, type "
					+ "FROM users "
					+ "WHERE company IN ("
					+ "    SELECT company "
					+ "    FROM users "
					+ "    WHERE loginID = '" + loginID + "'"
					+ ")"
					+ "AND loginID != '" + loginID + "'"
					+ "AND loginID NOT IN ("
					+ "    SELECT loginID "
					+ "    FROM ("
					+ "        SELECT requestedID AS loginID"
					+ "        FROM connection c "
					+ "        JOIN users u ON c.requestedID = u.loginId "
					+ "        WHERE (status = 'approved' OR status = 'pending') AND requesterID = '" + loginID + "'"
					+ "        UNION "
					+ "        SELECT requesterID AS loginID"
					+ "        FROM connection c "
					+ "        JOIN users u ON c.requestedID = u.loginId "
					+ "        WHERE (status = 'approved' OR status = 'pending') AND requestedID = '" + loginID + "'"
					+ "    ) AS subquery"
					+ ")");

			
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

			
			resultSet =  statement
					.executeQuery("SELECT DISTINCT u2.loginID, u2.firstName, u2.lastName, u2.company, u2.type "
			           + "FROM users u "
			           + "JOIN connection c1 ON u.loginID = c1.requesterID OR u.loginID = c1.requestedID "
			           + "JOIN connection c2 ON (c1.requesterID = c2.requesterID OR c1.requestedID = c2.requestedID OR "
			           + "c1.requesterID = c2.requestedID OR c1.requestedID = c2.requesterID) "
			           + "AND c2.status = 'approved' "
			           + "AND c1.status = 'approved' "
			           + "JOIN users u2 ON (u2.loginID = c2.requesterID OR u2.loginID = c2.requestedID) "
			           + "WHERE u.loginID = '"+loginID+"' "
			           + "AND u2.loginID <> '"+loginID+"' "
			           + "AND NOT EXISTS (SELECT 1 FROM connection c3 WHERE c3.status = 'approved' "
			           + "AND ((c3.requesterID = '"+loginID+"' AND c3.requestedID = u2.loginID) OR (c3.requestedID = '"+loginID+"' "
			           + "AND c3.requesterID = u2.loginID)))");


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
	public ArrayList<UserAccount> viewIncomingConnRequests(String loginID) {
		try {

			ArrayList<UserAccount> connectionRec = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT c.requesterID, u.firstName, u.lastName, u.company, u.type\n"
							+ "FROM connection c\n"
							+ "JOIN users u ON c.requesterID = u.loginID\n"
							+ "WHERE c.requestedID = '"+loginID+"' AND c.status = 'pending'\n"
							+ "\n"
							+ "UNION\n"
							+ "\n"
							+ "SELECT c.requestedID, u.firstName, u.lastName, u.company, u.type\n"
							+ "FROM connection c\n"
							+ "JOIN users u ON c.requestedID = u.loginID\n"
							+ "WHERE c.requesterID = '"+loginID+"' AND c.status = 'pending'\n"
							+ "");

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
	public ArrayList<UserAccount> viewIncomingRecRequests(String loginID) {
		try {

			ArrayList<UserAccount> recReq = new ArrayList<UserAccount>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT u.loginID, u.firstName, u.lastName, u.company, u.type\n"
							+ "FROM recommendation r\n"
							+ "JOIN users u ON r.sender_id = u.loginID\n"
							+ "WHERE r.receiver_id = '"+loginID+"' AND r.status = 'Pending'\n"
							+ "\n"
							+ "UNION\n"
							+ "\n"
							+ "SELECT u.loginID, u.firstName, u.lastName, u.company, u.type\n"
							+ "FROM recommendation r\n"
							+ "JOIN users u ON r.receiver_id = u.loginID\n"
							+ "WHERE r.sender_id = '"+loginID+"' AND r.status = 'Pending'\n"
							+ "");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				recReq.add(ua);
			}

			return recReq;
			
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

		if (!existsJob(jobID, loginID)) {
			try {

				connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
				statement = connection.createStatement();

				statement.executeUpdate(
						"INSERT INTO jobsharing(jobID, userID) VALUES ( '" + jobID + "', '" + loginID + "') ");

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
		} else {
			System.out.println("You have already shared this Job !!! ");
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
					.executeQuery("SELECT j.jobID, j.jobTitle, j.jobDesc, j.creator,j.dateandtime\n"
							+ "FROM job j\n"
							+ "WHERE j.creator IN (\n"
							+ "    SELECT requestedID FROM connection\n"
							+ "    WHERE requesterID = '"+loginID+"' AND status = 'approved'\n"
							+ "    UNION\n"
							+ "    SELECT requesterID FROM connection\n"
							+ "    WHERE requestedID = '"+loginID+"' AND status = 'approved'\n"
							+ ")\n"
							+ "ORDER BY j.dateandtime DESC;\n"
							+ "");

			while (resultSet.next()) {
				Jobs j = new Jobs( resultSet.getInt(1), resultSet.getString(2) ,resultSet.getString(3) , resultSet.getString(4) ,resultSet.getDate("dateandtime") );
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
	public ArrayList<Jobs> jobRecommendations(String loginID) {
		try {
			ArrayList<Jobs> jobsByConn = new ArrayList<Jobs>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT j.jobID, j.jobTitle, j.jobDesc,j.creator, js.dateandtime\n"
							+ "FROM jobsharing js\n"
							+ "JOIN job j ON js.jobID = j.jobID\n"
							+ "WHERE js.userID IN (\n"
							+ "    SELECT requestedID FROM connection\n"
							+ "    WHERE requesterID = '"+loginID+"' AND status = 'approved'\n"
							+ "    UNION\n"
							+ "    SELECT requesterID FROM connection\n"
							+ "    WHERE requestedID = '"+loginID+"' AND status = 'approved'\n"
							+ ")\n"
							+ "ORDER BY js.dateandtime DESC\n"
							+ "LIMIT 3;\n"
							+ "");

			while (resultSet.next()) {
					Jobs j = new Jobs( resultSet.getInt(1), resultSet.getString(2) ,resultSet.getString(3) , resultSet.getString(4) ,resultSet.getDate("dateandtime") );
					jobsByConn.add(j);
			}

			return jobsByConn;
			
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

	public boolean existsJob(int jobID, String userID) {
		
		try {

			

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("SELECT 1 from jobsharing WHERE jobID = '"+jobID+"' and userID = '"+userID+"' ");

			while (resultSet.next()) {
				return true;
			}

			
		} catch (SQLException e) {
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
		return false;
		
		
	}

	@Override
	public void updateNotification(int not_id,  String updateStatus) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			statement.executeUpdate("UPDATE notification SET nstatus = '"+updateStatus+"' WHERE id = '"+not_id+"'");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println(" Notification seen successfully !!! ");
			
		} catch (SQLException e) {
			System.out.println(" Notification failed to execute !!! ");
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
	public ArrayList<Recommendation> recommendations(String loginID) {
		try {

			ArrayList<Recommendation> rec = new ArrayList<Recommendation>();

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

//			resultSet = statement
//					.executeQuery("SELECT u.loginID, u.firstName, u.lastName, u.company, u.type\n"
//							+ "FROM recommendation r\n"
//							+ "JOIN users u ON r.receiver_id = u.loginID\n"
//							+ "WHERE r.status = 'Approved' AND r.sender_id = '"+loginID+"'\n"
//							+ "\n"
//							+ "UNION\n"
//							+ "\n"
//							+ "SELECT u.loginID, u.firstName, u.lastName, u.company, u.type\n"
//							+ "FROM recommendation r\n"
//							+ "JOIN users u ON r.sender_id = u.loginID\n"
//							+ "WHERE r.status = 'Approved' AND r.receiver_id = '"+loginID+"'\n"
//							+ "");
			
			resultSet = statement.executeQuery("SELECT r.* FROM recommendation r " +
	                "WHERE r.status = 'Approved' AND (r.sender_id = '"+loginID+"' OR r.receiver_id = '"+loginID+"')");


			while (resultSet.next()) {
//				UserAccount r = new UserAccount(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
//				rec.add(r);
				Recommendation r = new Recommendation(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
				rec.add(r);
			}

			return rec;
			
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
	public void updateRecommendation(String loginID, String requestedID, String updateStatus) {
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			
			statement.executeUpdate("UPDATE recommendation \n"
					+ "SET status = '"+updateStatus+"'\n"
					+ "WHERE (sender_id = '"+loginID+"' AND receiver_id = '"+requestedID+"')\n"
					+ "   OR (sender_id = '"+requestedID+"' AND receiver_id = '"+loginID+"')\n"
					+ "");

			connection.setAutoCommit(false);
			connection.commit();

			System.out.println(" Recommendation request " + updateStatus  +" successfully !!! ");
			
		} catch (SQLException e) {
			System.out.println(" Recommendation request failed to "+ updateStatus +" !!! ");
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
	public void sendRequestForRecommendation(String loginID, String requestedID) {
		
		try {

			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO recommendation(sender_id, receiver_id, status) VALUES ('"+loginID+"', '"+requestedID+"' , 'Pending' )");

			statement.executeUpdate("INSERT INTO notification(sender_user_id, receiver_user_id,ntype) VALUES ('"+loginID+"', '"+requestedID+"' , 'Recommendation' )");
			
			connection.setAutoCommit(false);
			connection.commit();

			System.out.println("Sent recommendation request to " + requestedID + " successfully !!! ");
			System.out.println("Sent Notification for recommendation request to " + requestedID + " successfully !!! ");

		} catch (SQLException e) {
			System.out.println("recommendation request to " + requestedID + " failed !!! ");
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
	public ArrayList<UserAccount> searchUser(String search, String loginID) {
		try {
		
			ArrayList<UserAccount> searches = new ArrayList<UserAccount>();
			
			connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT loginID, firstName, lastName, company, type FROM users WHERE loginID != '"+loginID+"' AND ("
					+ "loginID LIKE '"+search+"' or firstName LIKE '"+search+"' or lastName LIKE '"+search+"' or company LIKE '"+search+"' or type LIKE '"+search+"')");

			while (resultSet.next()) {
				UserAccount ua = new UserAccount(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5));
				searches.add(ua);
			}

			return searches;
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
}
