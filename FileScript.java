import java.sql.*;
import java.util.Random;
import java.security.*;
import javax.xml.bind.DatatypeConverter;

public class FileScript
{
	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException
	{
		MuzikrDB.init();
	
		String query;
		query =  "UPDATE Song SET\n";
		query += "file=X\'7468697369736174657374\'";
		MuzikrDB.rawQuery(query);

	}

}
