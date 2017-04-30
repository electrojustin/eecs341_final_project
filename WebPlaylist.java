import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebPlaylist implements HttpHandler
{
	public static String playlistListing (ArrayList<String[]> results, String fifthColumnTitle, String fifthColumn, String url)
	{
		String response = "<table style=\"width:75%\">\n";
		response += " <tr>\n";
		response += "  <th style=\"text-align:left\">Playlist Name</th>\n";
		response += "  <th style=\"text-align:left\">Creator</th>\n";
		response += "  <th style=\"text-align:left\">Number of Songs</th>\n";
		response += "  <th style=\"text-align:left\">Total Length</th>\n";
		response += "  <th style=\"text-align:left\">";
		response += fifthColumnTitle;
		response += "</th>\n";
		response += " </tr>\n";

		for (String[] result: results)
		{
			response += " <tr>\n";
			response += "  <td>" + result[0] + "</td>\n";
			response += "  <td>" + result[1] + "</td>\n";
			response += "  <td>" + result[2] + "</td>\n";
			response += "  <td>" + result[3] + "</td>\n";

			response += "  <td><a href=\"";
			response += url;
			response += "playlistname=";
			response += result[0];
			response += "&username=";
			response += result[1];
			response += "\">";
			response += fifthColumn;
			response += "</a></td>\n";

			response += " </tr>\n";
		}
		response += "</table>";

		return response;
	}

	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String playlistKeyword;
		String creatorKeyword;
		OutputStream output = exchange.getResponseBody();
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("playlistname"))
				playlistKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("creator"))
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

		response = "<html><h3>Playlist Search Results:</h3>\n";
		response += " <br />\n";
		String[] result1 = new String[4];
		result1 [0] = "MyFavSongs";
		result1 [1] = "testUser";
		result1 [2] = "23";
		result1 [3] = "120";
		ArrayList<String[]> results = new ArrayList<String[]>();
		results.add(result1);
		response += playlistListing(results, "View Songs", "View", "/viewplaylist?");
		//Replace the above with the below once we have actual database results
		//response += playlistListing(getResults(), "View Songs", "View", "/viewplaylist?");
		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
