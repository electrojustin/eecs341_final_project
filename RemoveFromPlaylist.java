import java.sql.*;

public class RemoveFromPlaylist
{
	public void remove(String playlistKeyword, String creatorKeyword, String songKeyword, String albumKeyword)
	{
		try
		{
			String deleteCommand;
			Statement statement;
			Connection connection = MuzikrDB.getConnection();

			deleteCommand =  "DELETE\n";
			deleteCommand += "FROM Compiles\n";
			deleteCommand += "WHERE playlistName = \"" + playlistKeyword + "\"\n";
			deleteCommand += "  AND username = \"" + creatorKeyword + "\"\n";
			deleteCommand += "  AND songName = \"" + songKeyword + "\"\n";
			deleteCommand += "  AND albumName = \"" + albumKeyword + "\"\n";

			statement = connection.createStatement();
			statement.executeUpdate(deleteCommand);
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
		}
	}
}
