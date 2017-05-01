import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebViewPlaylist implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String playlistKeyword = null;
		String creatorKeyword = null;
		String username = WebLogin.getUsername(exchange);
		OutputStream output = exchange.getResponseBody();
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("playlistname"))
				playlistKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("username"))
				creatorKeyword = parsedRequest.get(i+1);
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

		if (playlistKeyword == null || creatorKeyword == null)
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		response = "<html><h3>Playlist Songs:</h3>\n";
		response += " <br />\n";
		String[] result1 = new String[2];
		result1 [0] = "Never Gonna Give You Up";
		result1 [1] = "Foo Bar";
		ArrayList<String[]> results = new ArrayList<String[]>();
		results.add(result1);
		if (username != null && creatorKeyword.equals(username))
		{
			response += WebSearch.songListing(exchange, results, playlistKeyword, creatorKeyword);
			//response += WebSearch.songListing(exchange, getResults(), playlistKeyword, creatorKeyword);
		}
		else
		{
			response += WebSearch.songListing(exchange, results);
			//Again, replace above with below
			//response += WebSearch.songListing(exchange, getResults());
		}
		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}