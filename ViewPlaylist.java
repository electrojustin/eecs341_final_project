import java.util.ArrayList;
import java.sql.*;

public class ViewPlaylist
{
	public ArrayList<String[]> getPlaylistSongs (String playlistName, String username)
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			queryString =  "SELECT songName, albumName\n";
			queryString += "FROM Compiles\n";
			queryString += "WHERE playlistName = \"" + playlistName + "\"\n";
			queryString += "  AND username = \"" + username + "\"";

			queryResult = MuzikrDB.rawQuery(queryString);

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
