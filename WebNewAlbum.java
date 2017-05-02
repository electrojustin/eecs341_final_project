//Simple web page to create a new album

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebNewAlbum implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String albumName = null;
		String genre = null;
		String label = null;
		String response;
		NewAlbum album = new NewAlbum();
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("albumname"))
				albumName = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("genre"))
				genre = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("label"))
				label = parsedRequest.get(i+1);
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

		if (albumName == null || genre == null | label == null)
		{
			response = "<html>Error: all fields must be filled out";
			response += parsedRequest.get(i);
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		album.insertAlbum(albumName, genre, label);

		response = "<html>Successfully created album!\n";
		response += " <br /><a href=\"/home\">homepage</a></html>";
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
