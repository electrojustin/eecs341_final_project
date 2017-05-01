import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebArtistProfile implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{	
		String response;
		String username = WebLogin.getUsername(exchange);
		String profileUsername;
		OutputStream output = exchange.getResponseBody();
		ArtistProfile profile = null;

		if (username == null)
		{
			response = "<html>You are not signed in";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		//Implement me
		//profile = getProfile();
		profile = new ArtistProfile();
		profile.username = "testUser";
		profile.name = "Rick Astley";
		profile.numSongsMade = 3;
		profile.numBuys = 27;
		profile.mostPopular = "Never Gonna Give You Up";

		if (profile == null)
		{
			response = "<html>You are not registered as an artist";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		response = "<html><h3>Welcome to the Artist Portal!</h3>\n";
		response += "<br />Name: " + profile.name + "\n";
		response += "<br />Number of songs made: " + profile.numSongsMade + "\n";
		response += "<br />Number of buys: " + profile.numBuys + "\n";
		response += "<br />Most popular song: " + profile.mostPopular + "\n";

		response += "<br /><br /><h4>Create a new album</h4>\n";
		response += "<form action=\"/newalbum\">\n";
		response += "Album name:\n";
		response += "<br /><input type=\"text\" name=\"albumname\">\n";
		response += "<br />Album genre:\n";
		response += "<br /><input type=\"text\" name=\"genre\">\n";
		response += "<br />Label name:\n";
		response += "<br /><input type=\"text\" name=\"label\">\n";
		response += "<br /><input type=\"submit\" value=\"Create\">\n";
		response += "</form>\n";

		response += "<br /><br /><h4>Upload a new song</h4>\n";
		response += "<form action=\"/newsong\" method=\"POST\" enctype=\"multipart/form-data\">";
		response += "Song name:\n";
		response += "<br /><input type=\"text\" name=\"songname\">\n";
		response += "<br />Album name:\n";
		response += "<br /><input type=\"text\" name=\"albumname\">\n";
		response += "<br />Length:\n";
		response += "<br /><input type=\"text\" name=\"length\">\n";
		response += "<br /><input type=\"file\" name=\"upload\">\n";
		response += "<br /><input type=\"submit\" name=\"Create\">\n";
		response += "</form>\n";

		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();

	}
}
