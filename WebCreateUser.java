import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.sql.*;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebCreateUser implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String response;
		OutputStream output = exchange.getResponseBody();
		String username = null;
		String password = null;
		String email = null;
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("email"))
				email = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("username"))
				username = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("passwd"))
				password = parsedRequest.get(i+1);
			else
			{
				response = "<html>Error: invalid key ";
				response += parsedRequest.get(i);
				response += " <br /> <a href=\"/home\">homepage</a></html>";
				exchange.sendResponseHeaders(200, response.length());
				output.write(response.getBytes());
				output.close();
				return;
			}
		}

		if (username == null || password == null || email == null)
		{
			response = "<html>Error: all fields must be filled out";
			response += parsedRequest.get(i);
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}


		Random r = new Random();
		int salt = r.nextInt();

		String saltedPass = password + salt;
		String hashedPass = null;

		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hashedPass = new String(digest.digest(saltedPass.getBytes()));
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("Error: no support for SHA-256");
		}

		try
		{
			String query = "INSERT INTO User VALUES (";
			query += "\"" + username + "\", ";
			query += "\"" + hashedPass + "\", ";
			query += "\"" + salt + "\", ";
			query += "\"" + email + "\")";
		
			MuzikrDB.rawQuery(query);
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
		}

		response = "<html>Successfully created user!\n";
		response += " <br /><a href=\"/home\">homepage</a></html>";
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
