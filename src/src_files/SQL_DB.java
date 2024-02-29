package src_files;

import java.sql.*;

public class SQL_DB implements DataStorage {

	final String DATABASE_URL = "jdbc:mysql://cobmysql.uhcl.edu/rathodn8713?useSSL=false";
	final String db_id = "rathodn8713";
	final String db_psw = "2248713";

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	@Override
	public void createAccount(String loginID, String password, String type, String company) {

		try {
			connection = DriverManager.getConnection(DATABASE_URL, "rathodn8713", "2248713");
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
	public void login(String loginID, String password) {
		final String DATABASE_URL = "jdbc:mysql://cobmysql.uhcl.edu/rathodn8713?useSSL=false";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {

			connection = DriverManager.getConnection(DATABASE_URL, "rathodn8713", "2248713");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("Select * from users " + "where loginID = '" + loginID + "'");

			if (resultSet.next()) {
				if (password.equals(resultSet.getString(2))) {
					System.out.println("Welcome " + loginID + "!!!");
					Users.welcome(loginID,resultSet.getString(3));
				} else {
					System.out.println("password incorrect!");
				}
			} else {
				System.out.println("login ID not found!");
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

		
	}

}
