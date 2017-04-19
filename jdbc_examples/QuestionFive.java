import java.sql.*;
import java.util.Properties;

public class QuestionFive
{
	public static void main (String[] args) throws SQLException, ClassNotFoundException
	{
		String queryString = "SELECT ms.starName " +
				     "FROM MovieStar ms " +
				     "WHERE NOT EXISTS (SELECT * " +
				     "		        FROM Movies m " +
				     "			WHERE m.studioName='MGM' " +
				     "			      AND NOT EXISTS (SELECT * " +
				     "					      FROM Stars s " +
				     "					      WHERE s.starName=ms.starName " +
				     "						    AND s.movieYear=m.movieYear " +
				     "						    AND s.movieTitle=m.movieTitle));";
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
			System.out.println(queryResult.getString("ms.starName"));
	}
}
