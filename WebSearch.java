import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebSearch implements HttpHandler
{
	public static String songListing (HttpExchange exchange, ArrayList<String[]> results)
	{
		return songListing(exchange, results, null, null);
	}

	public static String songListing (HttpExchange exchange, ArrayList<String[]> results, String removePlaylist, String removePlaylistCreator)
	{
		boolean isLoggedIn = WebLogin.isLoggedIn(exchange);
		String response = "<table style=\"width:75%\">\n";
		response += " <tr>\n";
		response += "  <th style=\"text-align:left\">Song</th>\n";
		response += "  <th style=\"text-align:left\">Album</th>\n";
		response += "  <th style=\"text-align:left\">View Details</th>\n";
		if (isLoggedIn)
		{
			response += "  <th style=\"text-align:left\">Download</th>\n";
			if (removePlaylist == null)
				response += "  <th style=\"text-align:left\">Add To Playlist</th>\n";
			else
				response += "  <th style=\"text-align:left\">Remove From Playlist</th>\n";
		}
		response += " </tr>\n";
		
		for (String[] result : results)
		{
			response += " <tr>\n";
			response += "  <td>" + result[0] + "</td>\n";
			response += "  <td>" + result[1] + "</td>\n";

			response += "  <td><a href=\"/details?songname=";
			response += result[0];
			response += "&albumname=";
			response += result[1];
			response += "\">Details</a></td>\n";

			if (isLoggedIn)
			{
				if (true)
				//if (ownSong()) //This needs to actually be added
				{
					response += "  <td><a href=\"/download?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "\">Download</a></td>\n";
				}
				else
				{
					response += "  <td><a href=\"/buy?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "\">Buy</a></td>\n";
				}

				if (removePlaylist == null)
				{
					response += "  <td><a href=\"/selectplay?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "\">Add</a></td>\n";
				}
				else
				{
					response += "  <td><a href=\"/removesong?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "&playlist=";
					response += removePlaylist;
					response += "&creator=";
					response += removePlaylistCreator;
					response += "\">Remove</a></td>\n";
				}
			}

			response += " </tr>\n";
		}

		response += "</table>\n";
		return response;
	}

	@Override
	public void handle (HttpExchange exchange) throws IOException
	{
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String songKeyword;
		String artistKeyword;
		String albumKeyword;
		String producerKeyword;
		boolean onlyShowUser = false;
		String response;
		OutputStream output = exchange.getResponseBody();
		int i = 0;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("artist"))
				artistKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("song"))
				songKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("album"))
				albumKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("producer"))
				producerKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("mysongs"))
				onlyShowUser = true;
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

		response = "<html><h3>Search Results:</h3>\n";
		response += " <br />\n";
		String[] result1 = new String[2];
		result1 [0] = "Never Gonna Give You Up";
		result1 [1] = "Foo Bar";
		ArrayList<String[]> results = new ArrayList<String[]>();
		results.add(result1);
		response += songListing(exchange, results);
		//response += songListing(exchange, getResults()); //Get results needs to actually be implemented
		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
