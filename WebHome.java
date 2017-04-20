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
		response += " <br /><h3>Sign in here: </h3>";
		response += " <form action=\"/login\">";
		response += " Username: ";
		response += " <br /><input type=\"text\" name=\"username\">";
		response += " <br />Password: ";
		response += " <br /><input type=\"text\" name=\"passwd\">";
		response += " <br /><input type=\"submit\" value=\"Login\">";
		response += " <br /><br /><br />";
		response += " <a href=\"logout\">Logout</a>";

		OutputStream output = exchange.getResponseBody();
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
