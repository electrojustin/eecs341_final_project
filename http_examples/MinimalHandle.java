import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class MinimalHandle implements HttpHandler
{
	@Override
	public void handle (HttpExchange t) throws IOException
	{
		OutputStream output = t.getResponseBody();
		System.out.println(t.getRequestHeaders().getFirst("Cookie"));
		String response = "Hello world!";
		t.getResponseHeaders().set("Set-Cookie", "blah");
		t.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	}
}
