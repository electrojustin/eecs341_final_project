import java.io.IOException;
import java.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebBuy implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String username = WebLogin.getUsername(exchange);
	
		if (username != null)
		{
			if (parsedRequest.get(0).equals("songname") && parsedRequest.get(2).equals("albumname"))
			{
				//Insert buying code here

				String response = "<html>Thank you for your purchase!"
				response += " <br /><a href=\"/home\">homepage</a></html>";
				output.write(response.getBytes());
				output.close();
			}
			else
			{
				String response = "<html>Error: invalid request";
				response += " <br /><a href=\"/home\">homepage</a></html>";
				exchange.sendResponseHeader(200, response.length());
				output.write(response.getBytes());
				output.close();
			}
		}
		else
		{
			String response = "<html>Error: you must be logged in to buy music";
			response += " <br /><a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeader(200, response.length());
			output.write(response.getBytes());
			output.close();
		}
	}
}
