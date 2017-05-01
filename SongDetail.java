import java.util.ArrayList;
import java.sql.*;

public class SongDetail
{
	public String name;
	public String album;
	public String[] artists;
	public String producerName;
	public String producerUsername;
	public String producerAddress;
	public int songLength;

	public SongDetail (String name, String album)
	{
		this.name = name;
		this.album = album;
	}

	public void getLength()
	{
		try
		{
			String queryString;
			ResultSet queryResult;

			queryString =  "SELECT length\n";
			queryString += "FROM Song\n";
			queryString += "WHERE songName = \"" + name + "\"\n";
			queryString += "  AND albumName = \"" + album + "\"";

			queryResult = MuzikrDB.rawQuery(queryString);

			if (queryResult.next())
				songLength = queryResult.getInt("length");
			else
				songLength = 0;
		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			songLength = 0;
		}
	}

	public void getArtists()
	{	
		try
		{
			String queryString;
			ResultSet queryResult;
			ArrayList<String> ret = new ArrayList<String>();

			queryString =  "SELECT a.artistName\n";
			queryString += "FROM Artist a, Creates c\n";
			queryString += "WHERE c.songName = \"" + name + "\"\n";
			queryString += "  AND c.albumName = \"" + album + "\"\n";
			queryString += "  AND a.artistID = c.artistID";

			queryResult = MuzikrDB.rawQuery(queryString);

			while (queryResult.next())
				ret.add(queryResult.getString(1));

			artists = ret.toArray(new String[ret.size()]);
		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			artists = null;
		}
	}

	public void getProducerInfo()
	{
		try
		{
			String queryString;
			ResultSet queryResult;

			queryString =  "SELECT l.labelName, l.studioAddress, l.presidentUsername\n";
			queryString += "FROM Label l, Album a\n";
			queryString += "WHERE a.albumName = \"" + album + "\"\n";
			queryString += "  AND l.labelName = a.labelName";

			queryResult = MuzikrDB.rawQuery(queryString);

			if (queryResult.next())
			{
				producerName = queryResult.getString("l.labelName");
				producerUsername = queryResult.getString("l.presidentUsername");
				producerAddress = queryResult.getString("l.studioAddress");
			}
			else
			{
				producerName = null;
				producerUsername = null;
				producerAddress = null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("SQL error");
			producerName = null;
			producerUsername = null;
			producerAddress = null;
		}
	}
}
