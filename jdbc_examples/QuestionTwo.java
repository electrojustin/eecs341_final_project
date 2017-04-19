import java.sql.*;
import java.util.Properties;

public class QuestionTwo
{
	public static void main (String[] args) throws SQLException, ClassNotFoundException
	{
		String queryString = "SELECT m1.studioName, m1.movieTitle, m1.movieYear " +
				      "FROM Movies m1 " +
				      "WHERE NOT EXISTS (SELECT * " +
							"FROM Movies m2 " +
							"WHERE m2.length > m1.length " + 
							"AND m1.studioName = m2.studioName);";
		Connection connection;
		Properties connectionProperties = new Properties();
		ResultSet queryResult;
		Statement query;

		Class.forName("org.mariadb.jdbc.Driver");
		connectionProperties.put("user", "jeg112");
		connectionProperties.put("password", "jeg112");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eecs341", connectionProperties);

		query = connection.createStatement();
		queryResult = query.executeQuery(queryString);

		while(queryResult.next())
			System.out.println(queryResult.getString("m1.studioName") + "\t" + queryResult.getString("m1.movieTitle") + "\t" + queryResult.getInt("m1.movieYear"));
	}
}
