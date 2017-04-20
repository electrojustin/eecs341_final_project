import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebLogout implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");
		int i;

		if (userSessionId != null && !userSessionId.equals("0"))
		{
			for (i = 0; i < MuzikrWeb.sessionIds.size(); i++)
			{
				if (MuzikrWeb.sessionIds.get(i) == Integer.parseInt(userSessionId))
				{
					MuzikrWeb.sessionIds.remove(i);
					MuzikrWeb.loginUsernames.remove(i);
				}
			}
		}

		String response = "<html>You are now logged out";
		response += " <br /> <a href=\"/home\">homepage</a></html>";
		OutputStream output = exchange.getResponseBody();
		exchange.getResponseHeaders().set("Set-Cookie", "0");
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
