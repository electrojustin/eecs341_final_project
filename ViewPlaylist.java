import java.util.ArrayList;
import java.sql.*;

public class ViewPlaylist
{
	public ArrayList<String[]> getPlaylistSongs (String playlistName, String username)
	{
		try
		{
			String queryString;
			PreparedStatement query;
			Connection connection = MuzikrDB.getConnection();
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			queryString =  "SELECT songName, albumName\n";
			queryString += "FROM Compiles\n";
			queryString += "WHERE playlistName = ?";
			queryString += "  AND username = ?";

			query = connection.prepareStatement(queryString);
			query.setString(1, playlistName);
			query.setString(2, username);

			queryResult = query.executeQuery();

			while (queryResult.next())
			{
				String[] row = new String[2];

				row[0] = queryResult.getString("songName");
				row[1] = queryResult.getString("albumName");

				ret.add(row);
			}

			return ret;
		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			return null;
		}
	}
}
