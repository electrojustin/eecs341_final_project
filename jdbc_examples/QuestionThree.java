import java.sql.*;
import java.util.Properties;

public class QuestionThree
{
	public static void main (String[] args) throws SQLException, ClassNotFoundException
	{
		String queryString = "SELECT DISTINCT me.name " + //My implementation of MySQL does not currently support WITH statements, so I have to basically repeat a query
				     "FROM MovieExec me, Movies m " +
				     "WHERE m.producerC=me.cert " +
				     "	    AND m.studioName=? " +
				     "	    AND NOT EXISTS (SELECT * " +
				     "			    FROM MovieExec me2, Movies m2 " +
				     "			    WHERE m2.studioName=? " +
				     "				  AND m2.producerC=me2.cert " +
				     "				  AND me2.networth>me.networth);";
		Connection connection;
		Properties connectionProperties = new Properties();
		PreparedStatement query;
		ResultSet queryResult;

		Class.forName("org.mariadb.jdbc.Driver");
		connectionProperties.put("user", "jeg112");
		connectionProperties.put("password", "jeg112");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eecs341", connectionProperties);

		query = connection.prepareStatement(queryString);
		query.setString(1, "MGM");
		query.setString(2, "MGM");
		queryResult = query.executeQuery();

		while(queryResult.next())
			System.out.println(queryResult.getString("me.name"));
	}
}
