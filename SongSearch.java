import java.util.ArrayList;
import java.sql.*;

public class SongSearch
{
	public ArrayList<String[]> search (String songKeyword, String artistKeyword, String albumKeyword, String producerKeyword)
	{
		try
		{
			String queryString;
			ArrayList<String[]> ret = new ArrayList<String[]>();
			ResultSet queryResult;

			if (songKeyword == null)
				songKeyword = "";
			if (artistKeyword == null)
				artistKeyword = "";
			if (albumKeyword == null)
				albumKeyword = "";
			if (producerKeyword == null)
				producerKeyword = "";

			queryString =  "SELECT s.songName, s.albumName\n";
			queryString += "FROM Song s, Album al, Artist ar, Creates c\n";
			queryString += "WHERE s.albumName = al.albumName\n";
			queryString += "  AND c.songName = s.songName\n";
			queryString += "  AND c.albumName = s.albumName\n";
			queryString += "  AND ar.artistID = c.artistID\n";
			queryString += "  AND s.songName like \"%" + songKeyword + "%\"\n";
			queryString += "  AND s.albumName like \"%" + albumKeyword + "%\"\n";
			queryString += "  AND ar.artistName like \"%" + artistKeyword + "%\"\n";
			queryString += "  AND al.labelName like \"%" + producerKeyword + "%\"\n";

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
