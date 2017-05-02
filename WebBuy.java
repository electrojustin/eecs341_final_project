import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.sql.*;

public class WebBuy implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String username = WebLogin.getUsername(exchange);
	
		if (username != null)
		{
			if (parsedRequest.get(0).equals("songname") && parsedRequest.get(2).equals("albumname") && parsedRequest.get(1) != null && parsedRequest.get(3) != null)
			{
				String songName = parsedRequest.get(1);
				String albumName = parsedRequest.get(3);
		
				try
				{
					String query = "INSERT INTO Owns VALUES (";
					query += "\"" + username + "\", ";
					query += "\"" + songName + "\", ";
					query += "\"" + albumName + "\")";
	
					MuzikrDB.rawQuery(query);
				}
				catch (SQLException e)
				{
					System.out.println("SQL Error");
					return;
				}

				String response = "<html>Thank you for your purchase!";
				response += " <br /><a href=\"/home\">homepage</a></html>";
				output.write(response.getBytes());
				output.close();
			}
			else
			{
				String response = "<html>Error: invalid request";
				response += " <br /><a href=\"/home\">homepage</a></html>";
				exchange.sendResponseHeaders(200, response.length());
				output.write(response.getBytes());
				output.close();
			}
		}
		else
		{
			String response = "<html>Error: you must be logged in to buy music";
			response += " <br /><a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
		}
	}
}
