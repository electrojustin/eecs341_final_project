import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebUserProfile implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String profileUsername = null;
		String username = WebLogin.getUsername(exchange);
		OutputStream output = exchange.getResponseBody();
		UserProfile profile = null;
		int i;

		if (parsedRequest.size() != 2 || !parsedRequest.get(0).equals("username"))
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}
		else
			profileUsername = parsedRequest.get(1);

		if (profileUsername == null)
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		//Implement me
		//profile = getProfile();
		/*profile = new UserProfile();
		profile.username = "testUser";
		profile.numSongs = 42;
		profile.playlists = new ArrayList<String[]>();
		String[] playlist1 = new String[4];
		playlist1 [0] = "MyFavSongs";
		playlist1 [1] = "testUser";
		playlist1 [2] = "23";
		playlist1 [3] = "120";
		profile.playlists.add(playlist1);
		profile.commonSongs = new ArrayList<String[]>();
		String[] song1 = new String[2];
		song1 [0] = "Never Gonna Give You Up";
		song1 [1] = "Foo Bar";
		profile.commonSongs.add(song1);*/

		profile = new UserProfile(profileUsername);
		profile.getNumSongs();
		profile.getPlaylists();

		if (profile == null)
		{
			response = "<html>Error: no such user ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		response = "<html><h3>Profile For ";
		response += profileUsername;
		response += "</h3>\n";
		response += "<br />Number of songs owned: ";
		response += profile.numSongs + "\n";
		response += "<br /><h4>Playlists: </h4>\n";
		response += WebPlaylist.playlistListing(profile.playlists, "View Songs", "View", "/viewplaylist?");

		if (username != null && !profileUsername.equals(username))
		{
			profile.getCommonSongs(username);

			response += "<br /><h4>Songs in common: </h4>\n";
			response += WebSearch.songListing(exchange, profile.commonSongs);
		}
		else if (username != null)
		{
			profile.checkArtist();

			if (profile.isArtist)
			{
				response += "<br /><br />\n";
				response += "<a href=\"/artistportal\">Artist Portal</a>\n";
			}

			response += "<br /><br />\n";
			response += "<form action=\"/createplaylist\">\n";
			response += "Create a new playlist with this name:\n";
			response += "<input type=\"text\" name=\"playlistname\">\n";
			response += "<br /><input type=\"submit\" value=\"Create\">\n";
		}

		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
