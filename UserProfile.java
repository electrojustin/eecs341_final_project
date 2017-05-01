import java.util.ArrayList;
import java.sql.*;

public class UserProfile
{
	public String username;
	public int numSongs;
	public ArrayList<String[]> playlists;
	public ArrayList<String[]> commonSongs;
	public boolean isArtist;

	public UserProfile (String username)
	{
		this.username = username;
	}

	public void getNumSongs()
	{
		try
		{
			String queryString;
			ResultSet queryResult;

			queryString =  "SELECT count(*)\n";
			queryString += "FROM Owns\n";
			queryString += "WHERE username = \"" + username + "\"";

			queryResult = MuzikrDB.rawQuery(queryString);

			if (queryResult.next())
				numSongs = queryResult.getInt(1);
			else
				numSongs = 0;
		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			numSongs = 0;
		}
	}

	public void getPlaylists()
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			queryString =  "SELECT c.playlistName, c.username, count(c.songName), p.length\n";
			queryString += "FROM Compiles c, Playlist p\n";
			queryString += "WHERE c.playlistName = p.playlistName\n";
			queryString += "  AND c.username = p.userOwner\n";
			queryString += "  AND c.username = \"" + username + "\"\n";
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

			playlists = ret;

		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			playlists = null;
		}
	}

	public void getCommonSongs(String sessionUsername)
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			queryString =  "SELECT o.songName, o.albumName\n";
			queryString += "FROM Owns o\n";
			queryString += "WHERE o.username = \"" + sessionUsername + "\"\n";
			queryString += "  AND EXISTS (SELECT *\n";
			queryString += "              FROM Owns o2\n";
			queryString += "              WHERE o2.username = \"" + username + "\"\n";
			queryString += "                AND o.songName = o2.songName\n";
			queryString += "                AND o.albumName = o2.albumName)";

			queryResult = MuzikrDB.rawQuery(queryString);

			while (queryResult.next())
			{
				String[] row = new String[2];

				row[0] = queryResult.getString("songName");
				row[1] = queryResult.getString("albumName");

				ret.add(row);
			}

			commonSongs = ret;

		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			commonSongs = null;
		}
	}
	public void checkArtist()
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;
			int artistEntries;

			queryString =  "SELECT count(*)\n";
			queryString += "FROM Artist\n";
			queryString += "WHERE username = \"" + username + "\"";

			queryResult = MuzikrDB.rawQuery(queryString);

			if (queryResult.next())
			{
				if (queryResult.getInt(1) > 0)
					isArtist = true;
				else
					isArtist = false;
			}
			else
				isArtist = false;

		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			isArtist = false;
		}
	}
}
