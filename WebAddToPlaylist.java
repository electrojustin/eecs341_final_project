import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebAddToPlaylist implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String playlistKeyword = null;
		String creatorKeyword = null;
		String songKeyword = null;
		String albumKeyword = null;
		OutputStream output = exchange.getResponseBody();
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("playlistname"))
				playlistKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("username"))
				creatorKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("songname"))
				songKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("albumname"))
				albumKeyword = parsedRequest.get(i+1);
			else
			{
				response = "<html>Error: invalid search key ";
				response += parsedRequest.get(i);
				response += " <br /> <a href=\"/home\">homepage</a></html>";
				exchange.sendResponseHeaders(200, response.length());
				output.write(response.getBytes());
				output.close();
				return;
			}
		}

		if (playlistKeyword == null || creatorKeyword == null
		    || songKeyword == null || albumKeyword == null)
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		String query = "INSERT INTO Compiles VALUES (";
		query += playlistKeyword + ", ";
		query += creatorKeyword + ", ";
		query += songKeyword + ", ";
		query += albumKeyword + ")";

		MuzikrDB.rawQuery(query);

		response = "<html>Song was successfully added to playlist\n";
		response += " <br /><a href=\"/home\">homepage</a></html>";
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
