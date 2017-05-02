//Displays the results of a user search

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebUserSearch implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String userKeyword;
		String response;
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> users;
		UserSearch view = new UserSearch();

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
			userKeyword = parsedRequest.get(1);

		users = view.searchUsers(userKeyword);

		response = "<html><h3>Search Results:</h3>\n";
		response += " <br />\n";
		for (String user : users)
		{
			response += "<br /><a href=\"/profile?username=";
			response += user;
			response += "\">";
			response += user;
			response += "</a>\n";
		}

		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
