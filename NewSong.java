import java.sql.*;
import javax.xml.bind.DatatypeConverter;

public class NewSong
{
	public void insertSong(String songname, String albumname, int length, String file)
	{
		try
		{
			String hexFile = "X\'" + DatatypeConverter.printHexBinary(file.getBytes()) + "\'";
			String insertCommand;
			Statement statement;
			Connection connection = MuzikrDB.getConnection();

			insertCommand =  "INSERT INTO Song VALUES (\"" + songname + "\", ";
			insertCommand += "\"" + albumname + "\", ";
			insertCommand += length + ", ";
			insertCommand += hexFile + ")";

			statement = connection.createStatement();
			statement.executeUpdate(insertCommand);
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
		}
	}
	public void insertCreate(String songname, String albumname, String username)
	{
		try
		{
			String insertCommand;
			Statement statement;
			Connection connection = MuzikrDB.getConnection();
			String queryString;
			ResultSet queryResult;
			int artistId;

			queryString =  "SELECT artistID\n";
			queryString += "FROM Artist\n";
			queryString += "WHERE username = \"" + username + "\"\n";

			queryResult = MuzikrDB.rawQuery(queryString);

			if (queryResult.next())
				artistId = queryResult.getInt(1);
			else
			{
				System.out.println("Error: no valid artistId");
				return;
			}

			insertCommand =  "INSERT INTO Creates VALUES (" + artistId + ", ";
			insertCommand += "\"" + songname + "\", ";
			insertCommand += "\"" + albumname + "\")";

			statement = connection.createStatement();
			statement.executeUpdate(insertCommand);
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
		}
	}
}
