//Handles the backend for album creation

import java.sql.*;

public class NewAlbum
{
	public void insertAlbum(String albumName, String genre, String labelName)
	{
		try
		{
			String insertCommand = "INSERT INTO Album VALUES (\"" + albumName + "\", \"" + genre + "\", " + "\"labelName\")";
			Statement statement;
			Connection connection = MuzikrDB.getConnection();

			statement = connection.createStatement();
			statement.executeUpdate(insertCommand);
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
		}
	}
}
