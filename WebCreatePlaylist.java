import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.sql.*;

public class WebCreatePlaylist implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String username = WebLogin.getUsername(exchange);
		String playlistName;
		OutputStream output = exchange.getResponseBody();

		if (parsedRequest.size() != 2 || !parsedRequest.get(0).equals("playlistname"))
		{
			response = "<html>Error: invalid key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}
		else
			playlistName = parsedRequest.get(1);

		try
		{
			Connection connection = MuzikrDB.getConnection();
			String insertCommand;
			Statement statement;

			insertCommand =  "INSERT INTO Playlist VALUES (\"" + playlistName + "\", \"" + username + "\", 0)";

			statement = connection.createStatement();
			statement.executeUpdate(insertCommand);

			response = "<html>Successfully created playlist ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}
		catch (SQLException e)
		{
			response = "<html>Error creating playlist ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}
	}
}
