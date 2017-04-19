import java.sql.*;
import java.util.Properties;

public class QuestionOne
{
	public static void main (String[] args) throws SQLException, ClassNotFoundException
	{
		String queryString = "SELECT DISTINCT movieTitle, movieYear " +
				      "FROM Stars " +
				      "WHERE starName = ?;";
		Connection connection;
		Properties connectionProperties = new Properties();
		PreparedStatement query;
		ResultSet queryResult;

		Class.forName("org.mariadb.jdbc.Driver");
		connectionProperties.put("user", "jeg112");
		connectionProperties.put("password", "jeg112");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eecs341", connectionProperties);

		query = connection.prepareStatement(queryString);
		query.setString(1, "Meryl Streep");
		queryResult = query.executeQuery();

		while(queryResult.next())
			System.out.println(queryResult.getString("movieTitle") + "\t" + queryResult.getInt("movieYear"));
	}
}
