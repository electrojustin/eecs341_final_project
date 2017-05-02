import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.sql.*;

public class WebDownload implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		byte[] file;

		if (WebLogin.isLoggedIn(exchange))
		{
			if (parsedRequest.get(0).equals("songname") && parsedRequest.get(2).equals("albumname"))
			{
				file = null;

				try
				{
					String queryString;
					ResultSet queryResult;

					queryString =  "SELECT s.file\n";
					queryString += "FROM Song s, Owns o\n";
					queryString += "WHERE s.songName = \"" + parsedRequest.get(1) + "\"\n";
					queryString += "  AND s.albumName = \"" + parsedRequest.get(3) + "\"\n";
					queryString += "  AND s.songName = o.songName\n";
					queryString += "  AND s.albumName = o.albumName\n";
					queryString += "  AND o.username = \"" + WebLogin.getUsername(exchange) + "\"";

					queryResult = MuzikrDB.rawQuery(queryString);

					if (queryResult.next())
						file = queryResult.getBytes(1);
					else
						file = null;
						
				}
				catch (SQLException e)
				{	
					String response = "<html>SQL Error when processing download";
					response += " <br /><a href=\"/home\">homepage</a></html>";
					exchange.sendResponseHeaders(200, response.length());
					output.write(response.getBytes());
					output.close();	
				}

				if (file == null)
				{
					String response = "<html>Error: you do not own this song";
					response += " <br /><a href=\"/home\">homepage</a></html>";
					exchange.sendResponseHeaders(200, response.length());
					output.write(response.getBytes());
					output.close();	
				}
				else
				{
					exchange.sendResponseHeaders(200, file.length);
					output.write(file);
					output.close();	
				}
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
			String response = "<html>Error: you must be logged in to download music";
			response += " <br /><a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
		}
	} 
}
