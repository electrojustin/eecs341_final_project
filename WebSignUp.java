//Displays a sign-up page

import java.io.OutputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class WebSignUp implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{
		String response = "<html><h3>Sign Up Here</h3>\n";
		OutputStream output = exchange.getResponseBody();
		
		response += "<br /><form action=\"/createuser\">\n";
		response += "Email: \n";
		response += "<br /><input type=\"text\" name=\"email\">\n";
		response += "<br />Username: \n";
		response += "<br /><input type=\"text\" name=\"username\">\n";
		response += "<br />Password: \n";
		response += "<br /><input type=\"password\" name=\"passwd\">\n";
		response += "<br /><input type=\"submit\" value=\"Sign Up\">\n";
		response += "</form>\n";

		response += " <br /><a href=\"/home\">homepage</a></html>";
		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
