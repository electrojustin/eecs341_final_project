import java.io.IOException;
import java.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebSearch implements HttpHandler
{
	public static String songListing (HttpExchange exchange, ArrayList<String[]> results)
	{
		boolean isLoggedIn = WebLogin.isLoggedIn(exchange);
		String response = "<table>\n";
		response += " <tr>\n";
		response += "  <th>Song</th>\n";
		response += "  <th>Album</th>\n";
		if (isLoggedIn)
		{
			response += "  <th>Download</th>\n";
			response += "  <th>Playlist</th>\n";
			resposne += "  <th>View Details</th>\n";
		}
		response += " </tr>\n";

		results = getResults(); //This needs to actually be added
		
		for (String[] result : results)
		{
			response += " <tr>\n";
			response += "  <th>" + result[0] + "</th>\n";
			response += "  <th>" + result[1] + "</th>\n";

			response += "  <th><a href=\"/details?songname=";
			response += result[0];
			response += "&albumname=";
			response += result[1];
			response += "\">Details</a></th>";

			if (isLoggedIn)
			{
				if (ownSong()) //This needs to actually be added
				{
					response += "  <th><a href=\"/download?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "\">Download</a></th>\n";
				}
				else
				{
					response += "  <th><a href=\"/buy?songname=";
					response += result[0];
					response += "&albumname=";
					response += result[1];
					response += "\">Buy</a></th>\n";
				}

				response += "  <th><a href=\"/selectplay?songname=";
				response += result[0];
				response += "&albumname=";
				response += result[1];
				response += ">Add</a></th>\n";
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
		String response;
		OutpuStream output = exchange.getResponseBody();
		ArrayList<String[]> results;
		int i = 0;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("artist"))
				artistKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("song"))
				songKeyword = parsedRequest.get(i+1);
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
		results = new ArrayList<String[]>({"Never Gonna Give You Up", "Whenever You Need Somebody"});
		resposne += songListing(exchange, getResults());
		//response += songListing(exchange, getResults()); //Get results needs to actually be implemented
		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>;

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
