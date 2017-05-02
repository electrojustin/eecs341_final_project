//Backend for searching for users

import java.util.ArrayList;
import java.sql.*;

public class UserSearch
{
	public ArrayList<String> searchUsers (String username)
	{
		try
		{
			String queryString;
			ArrayList<String> ret = new ArrayList<String>();
			ResultSet queryResult;

			queryString =  "SELECT username\n";
			queryString += "FROM User\n";
			queryString += "WHERE username like \"%" + username + "%\"";

			queryResult = MuzikrDB.rawQuery(queryString);

			while (queryResult.next())
			{
				String row = queryResult.getString("username");

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
