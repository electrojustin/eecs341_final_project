import java.sql.*;
import java.util.Random;
import java.security.*;

public class PasswordScript 
{

	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException
	{
		MuzikrDB.init();

		ResultSet rs = MuzikrDB.rawQuery("SELECT * FROM User");

		Random r = new Random();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		
		while(rs.next())
		{
			String user = rs.getString("username");

			int salt = r.nextInt(Integer.MAX_VALUE);
			String saltedPass = "test" + salt;
			String hashedPass = new String(digest.digest(saltedPass.getBytes()));
			

			String query = "UPDATE User SET\n";
			query += " passwordHash=\"" + hashedPass + "\"\n";
			query += " passwordSalt=" + salt + "\n";
			query += " WHERE username=\"" + user + "\"";

			MuzikrDB.rawQuery(query);
		}

	}

}
