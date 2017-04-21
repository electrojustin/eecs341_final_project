import java.util.Random;
import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebLogin implements HttpHandler
{
	public static boolean isLoggedIn (HttpExchange exchange)
	{
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");

		if (getUserame(exchange) == null)
			return false;
		else
			return true;
	}

	public static String getUsername (HttpExchange exchange)
	{
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");
		int i;

		if (userSessionId == null || userSessionId.equals("0"))
			return false;

		for (i = 0; i < MuzikrWeb.sessionIds.size(); i++)
		{
			if (MuzikrWeb.sessionIds.get(i) == Integer.parseInt(userSessionId))
				return MuzikrWeb.loginUsernames.get(i);
		}

		return null;
	}

	private static void login (HttpExchange exchange, String username) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		Random rand = new Random();
		int userSessionId = rand.nextInt(Integer.MAX_VALUE);

		MuzikrWeb.sessionIds.add(userSessionId);
		MuzikrWeb.loginUsernames.add(username);

		String response = "<html>You are now logged in as ";
		response += username;
		response += " <br /> <a href=\"/home\">homepage</a></html>";


		exchange.getResponseHeaders().set("Set-Cookie", "" + userSessionId);
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}

	public void handle (HttpExchange exchange) throws IOException
	{
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());

		if (parsedRequest.get(0).equals("username") && parsedRequest.get(2).equals("passwd"))
		{
			if (true)//isValidLogin()) //This function needs to be defined at the database level
			{
				if (isLoggedIn(exchange))
				{
					String response = "<html>Error: already logged in";
					response += " <br /> <a href=\"/home\">homepage</a></html>";
					OutputStream output = exchange.getResponseBody();
					exchange.sendResponseHeaders(200, response.length());
					output.write(response.getBytes());
					output.close();
				}
				else
					login(exchange, parsedRequest.get(3));
			}
			else
			{
				String response = "<html>Error: invalid username or password";
				response += " <br /> <a href=\"/home\">homepage</a></html>";
				OutputStream output = exchange.getResponseBody();
				exchange.sendResponseHeaders(200, response.length());
				output.write(response.getBytes());
				output.close();
			}
		}
		else
		{
			String response = "<html>Error: invalid request";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			OutputStream output = exchange.getResponseBody();
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
		}
	}
}
