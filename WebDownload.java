import java.io.IOException;
import java.OutputStream;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebDownload implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		byte[] file;

		if (WebLogin.isLoggedIn(exchange))
		{
			if (parsedRequest.get(0).equals("songname") && parsedRequest.get(2).equals("albumname"))
			{
				file = null;
				//file = getFile(); //More placeholder code
				if (file == null)
				{
					String response = "<html>Error: you do not own this song";
					response += " <br /><a href=\"/home\">homepage</a></html>";
					exchange.sendResponseHeader(200, response.length());
					output.write(response.getBytes());
					output.close();	
				}
				else
				{
					exchange.sendResponseHeader(200, file.length);
					output.write(file);
					output.close();	
				}
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
			String response = "<html>Error: you must be logged in to download music";
			response += " <br /><a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeader(200, response.length());
			output.write(response.getBytes());
			output.close();
		}
	} 
}
