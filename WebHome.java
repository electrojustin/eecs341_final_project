import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebHome implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response = "<html><h1>Welcome to Muzikr!</h1>\n";
		boolean isLoggedIn = WebLogin.isLoggedIn(exchange);

		if (!isLoggedIn)
		{
			response += " <br /><h3>Sign in here: </h3>\n";
			response += " <form action=\"/login\">\n";
			response += " Username: \n";
			response += " <br /><input type=\"text\" name=\"username\">\n";
			response += " <br />Password: \n";
			response += " <br /><input type=\"password\" name=\"passwd\">\n";
			response += " <br /><input type=\"submit\" value=\"Login\">\n";
			response += " </form>\n";
			response += " <br /><a href=\"/signup\">Sign Up</a>\n";
		}
		else
		{
			response += " <br />You are signed in as ";
			response += WebLogin.getUsername(exchange) + " \n";
			response += " <br />\n";
			response += " <a href=\"logout\">Logout</a>\n";
		}

		response += " <br /><br />";
		response += " <br /><h3>Search for songs here: </h3>\n";
		response += " <form action=\"/search\">\n";
		response += " Search by song name: \n";
		response += " <br /><input type=\"text\" name=\"song\">\n";
		response += " <br />Search by artist: \n";
		response += " <br /><input type=\"text\" name=\"artist\">\n";
		response += " <br />Search by producer: \n";
		response += " <br /><input type=\"text\" name=\"producer\">\n";
		if (isLoggedIn)
		{
			response += " <br /><input type=\"checkbox\" name=\"mysongs\" value=\"true\">";
			response += "Only show songs that I own\n";
		}
		response += " <br /><input type=\"submit\" value=\"Search\">\n";
		response += " </form>\n";

		response += " <br /><br />";
		response += " <br /><h3>Search for playlists here: </h3>\n";
		response += " <form action=\"/playlist\">\n";
		response += " Search by playlist name: \n";
		response += " <br /><input type=\"text\" name=\"playlistname\">\n";
		response += " <br />Search by playlist creator: \n";
		response += " <br /><input type=\"text\" name=\"creator\">\n";
		response += " <br /><input type=\"submit\" value=\"Search\">\n";
		response += " </form>\n";

		if (isLoggedIn)
		{
			response += " <br /><br />";
			response += " <a href=\"/profile?username=";
			response += WebLogin.getUsername(exchange);
			response += "\">My Profile</a>\n";
		}

		response += " <br /><br />";
		response += " <br /><h3>Search for users here: </h3>\n";
		response += " <form action=\"/usersearch\">\n";
		response += " Username: \n";
		response += " <br /><input type=\"text\" name=\"username\">\n";
		response += " <br /><input type=\"submit\" value=\"Search\">\n";
		response += " </form>\n";

		response += "</html>";

		OutputStream output = exchange.getResponseBody();
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
