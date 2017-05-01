import java.sql.*;

public class MuzikrDB
{
	
	private static Connection connection;

	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
		init();
		ResultSet rs = getUsers();
		int cols = rs.getMetaData().getColumnCount();
		while(rs.next())
		{
			for(int i=1; i<=cols; i++)
			{
				if (i > 1) System.out.print(",  ");
				System.out.print(rs.getString(i));
			}
			System.out.println();
		}
	}

	public static void init() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.mariadb.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/muzikr", "eecs341", 
"eecs341");		
	}

	private static boolean isValidLogin(String user, String pass) throws SQLException
	{
		String loginQuery = "select username, passwordSalt, passwordHash from User where username = 
?";
		PreparedStatement s = connection.prepareStatement(loginQuery);
		s.setString(1, user);
		ResultSet result = s.executeQuery();
		result.next();
		String saltedPass = pass + result.getString("passwordSalt");
		// return hash(saltedPass).equals(result.getString("passwordHash"));
		// Add actual hash		

		return true;
	}

}
