//Simple web page for attempting a login

import java.util.Random;
import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.sql.*;

public class WebLogin implements HttpHandler
{
	//Helper function to check if a user is logged in
	public static boolean isLoggedIn (HttpExchange exchange)
	{
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");

		if (getUsername(exchange) == null)
			return false;
		else
			return true;
	}

	//Gets the username of the currently logged in user, or returns null if no one is logged in on this computer
	public static String getUsername (HttpExchange exchange)
	{
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");
		int i;

		if (userSessionId == null || userSessionId.equals("0"))
			return null;

		for (i = 0; i < MuzikrWeb.sessionIds.size(); i++)
		{
			if (MuzikrWeb.sessionIds.get(i) == Integer.parseInt(userSessionId))
				return MuzikrWeb.loginUsernames.get(i);
		}

		return null;
	}

	//Actually performs the login action
	private static void login (HttpExchange exchange, String username) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		Random rand = new Random();
		int userSessionId = rand.nextInt(Integer.MAX_VALUE);

		try
		{
			MuzikrWeb.loginSemaphore.acquire();
			MuzikrWeb.sessionIds.add(userSessionId);
			MuzikrWeb.loginUsernames.add(username);
			MuzikrWeb.loginSemaphore.release();
		}
		catch (InterruptedException e)
		{
			String response = "<html>Error logging in";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

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

		if (parsedRequest.get(0).equals("username") && parsedRequest.get(2).equals("passwd") && parsedRequest.get(1) != null && parsedRequest.get(3) != null)
		{
			String user = parsedRequest.get(1);
			String pass = parsedRequest.get(3);

			try
			{
				if (MuzikrDB.isValidLogin(user, pass))
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
						login(exchange, parsedRequest.get(1));
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
			catch (SQLException e)
			{
				System.out.println("SQL Error");
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
