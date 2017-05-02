//Displays a list of playlists the user controls so they can choose which one to add a song to

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebPlaylistSelect implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		OutputStream output = exchange.getResponseBody();
		String songName = null;
		String albumName = null;
		String addUrl = "/addtoplaylist?";
		PlaylistSelect selector = new PlaylistSelect();
		int i;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("songname"))
				songName = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("albumname"))
				albumName = parsedRequest.get(i+1);
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

		if (songName == null || albumName == null)
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}
		addUrl += "songname=";
		addUrl += songName;
		addUrl += "&albumname=";
		addUrl += albumName;
		addUrl += "&";

		response = "<html><h3>Select a playlist to add to</h3>\n";
		response += " <br />\n";
		response += WebPlaylist.playlistListing(selector.getSelections(WebLogin.getUsername(exchange)), "Add To Playlist", "Add", addUrl);
		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
