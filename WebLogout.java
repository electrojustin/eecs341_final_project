//Simple web page to log the user out

import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebLogout implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		String userSessionId = exchange.getRequestHeaders().getFirst("Cookie");
		int i;

		if (userSessionId != null && !userSessionId.equals("0"))
		{
			for (i = 0; i < MuzikrWeb.sessionIds.size(); i++)
			{
				if (MuzikrWeb.sessionIds.get(i) == Integer.parseInt(userSessionId))
				{
					try
					{
						MuzikrWeb.loginSemaphore.acquire();
						MuzikrWeb.sessionIds.remove(i);
						MuzikrWeb.loginUsernames.remove(i);
						MuzikrWeb.loginSemaphore.release();
					}
					catch (InterruptedException e)
					{
						String response = "<html>Error logging out";
						response += " <br /> <a href=\"/home\">homepage</a></html>";
						exchange.sendResponseHeaders(200, response.length());
						output.write(response.getBytes());
						output.close();
						return;
					}
				}
			}
		}

		String response = "<html>You are now logged out";
		response += " <br /> <a href=\"/home\">homepage</a></html>";
		exchange.getResponseHeaders().set("Set-Cookie", "0");
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
