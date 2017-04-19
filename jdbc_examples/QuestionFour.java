import java.sql.*;
import java.util.Properties;

public class QuestionFour
{
	public static void main (String[] args) throws SQLException, ClassNotFoundException
	{
		String queryString = "SELECT ms.starName " +
				     "FROM MovieStar ms " +
				     "WHERE NOT EXISTS (SELECT * " +
				     "		        FROM Stars s, Movies m, Stud t " +
				     "		        WHERE s.starName=ms.starName " +
				     "			      AND m.movieTitle=s.movieTitle " +
				     "			      AND m.movieYear=s.movieYear " +
				     "			      AND t.studioName=m.studioName " +
				     "			      AND t.address!=ms.address);";
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
