import java.sql.*;
import java.security.MessageDigest;

public class MuzikrDB
{
	
	private static Connection connection;

	public static void init() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/muzikr", "eecs341", "eecs341");		
	}

	public static Connection getConnection()
	{
		return connection;
	}

	private static boolean isValidLogin(String user, String pass) throws SQLException
	{
		String loginQuery = "select username, passwordSalt, passwordHash " +
				    "from User where username = ?";

		PreparedStatement s = connection.prepareStatement(loginQuery);
		s.setString(1, user);
		ResultSet result = s.executeQuery();
		result.next();
		String saltedPass = pass + result.getString("passwordSalt");

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		String hashedPass = new String(digest.digest(saltedPass.getBytes()));

		return hashedPass.equals(result.getString("passwordHash"));
	}

	public static ResultSet rawQuery(String query) throws SQLException
	{
		Statement s = connection.createStatement();
		return s.executeQuery(query);
	}

}
