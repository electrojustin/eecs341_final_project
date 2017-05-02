import java.util.ArrayList;
import java.sql.*;

public class PlaylistSearch
{
	public ArrayList<String[]> search(String playlistKeyword, String creatorKeyword)
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			if (playlistKeyword == null)
				playlistKeyword = "";
			if (creatorKeyword == null)
				creatorKeyword = "";

			queryString =  "SELECT c.playlistName, c.username, count(c.songName), p.length\n";
			queryString += "FROM Compiles c, Playlist p\n";
			queryString += "WHERE c.playlistName = p.playlistName\n";
			queryString += "  AND c.username = p.userOwner\n";
			queryString += "  AND c.username like \"%" + creatorKeyword + "%\"\n";
			queryString += "  AND c.playlistName like \"%" + playlistKeyword + "%\"\n";
			queryString += "GROUP BY c.playlistName";

			queryResult = MuzikrDB.rawQuery(queryString);

			while (queryResult.next())
			{
				String[] row = new String[4];

				row[0] = queryResult.getString(1);
				row[1] = queryResult.getString(2);
				row[2] = Integer.toString(queryResult.getInt(3));
				row[3] = Integer.toString(queryResult.getInt(4));

				ret.add(row);
			}

			//Don't forgot about newly created playlists
			queryString =  "SELECT p.playlistName, p.userOwner\n";
			queryString += "FROM Playlist p\n";
			queryString += "WHERE p.userOwner like \"%" + creatorKeyword + "%\"\n";
			queryString += "  AND p.playlistName like \"%" + playlistKeyword + "%\"\n";
			queryString += "  AND NOT EXISTS (SELECT *\n";
			queryString += "                  FROM Compiles c\n";
			queryString += "                  WHERE p.playlistName = c.playlistName\n";
			queryString += "                    AND p.userOwner = c.username)";

			queryResult = MuzikrDB.rawQuery(queryString);

			while (queryResult.next())
			{
				String[] row = new String[4];

				row[0] = queryResult.getString(1);
				row[1] = queryResult.getString(2);
				row[2] = "0";
				row[3] = "0";

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
