import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

	public static boolean isValidLogin(String user, String pass) throws SQLException
	{
		String loginQuery = "select username, passwordSalt, passwordHash " +
				    "from User where username = ?";

		PreparedStatement s = connection.prepareStatement(loginQuery);
		s.setString(1, user);
		ResultSet result = s.executeQuery();
		result.next();
		String saltedPass = pass + result.getString("passwordSalt");
		String hashedPass = null;

		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hashedPass = new String(digest.digest(saltedPass.getBytes()));
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("Error: no support for SHA-256");
		}

		return hashedPass.equals(result.getString("passwordHash"));
	}

	public static ResultSet rawQuery(String query) throws SQLException
	{
		Statement s = connection.createStatement();
		return s.executeQuery(query);
	}

}
