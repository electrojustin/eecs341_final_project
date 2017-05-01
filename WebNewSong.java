import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebNewSong implements HttpHandler
{
	public static ArrayList<String[]> parsePost (HttpExchange exchange) throws IOException
	{
		InputStream requestBody;
		byte[] bodyBytes;
		int bodyLen;
		String contentType;
		String delim;
		String[] responseBodies;
		ArrayList<String[]> parsedRequest = new ArrayList<String[]>();
		int i;

		contentType = exchange.getRequestHeaders().getFirst("Content-type");
		delim = "\n--" + contentType.substring(contentType.indexOf('=')+1);
		bodyLen = Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-length"));

		requestBody = exchange.getRequestBody();
		bodyBytes = new byte[bodyLen];
		for (i = 0; i < bodyLen; i++)
			bodyBytes[i] = (byte)requestBody.read();

		responseBodies = (new String(bodyBytes)).split(delim);

		for (String responseBody : responseBodies)
		{
			String[] keypair = new String[2];
			String name;
			String content;
			int nameStart = responseBody.indexOf('=') + 2;
			int nameEnd = nameStart + 1;
			int contentStart = nameEnd;

			if (nameStart == 1)
				continue;

			while (responseBody.charAt(nameEnd) != '\"')
				nameEnd++;

			name = responseBody.substring(nameStart, nameEnd);

			for (contentStart = nameEnd; contentStart < responseBody.length()-2; contentStart++)
			{
				if (responseBody.charAt(contentStart) == '\n' && responseBody.charAt(contentStart+1) == '\n')
				{
					contentStart += 2;
					break;
				}
				if (responseBody.charAt(contentStart) == '\n' && responseBody.charAt(contentStart+2) == '\n')
				{
					contentStart += 3;
					break;
				}
			}

			if (contentStart == responseBody.length()-2)
				content = "";
			else
				content = responseBody.substring(contentStart);

			keypair[0] = name;
			keypair[1] = content;
			parsedRequest.add(keypair);
		}

		return parsedRequest;
	}

	public void handle (HttpExchange exchange) throws IOException
	{
		OutputStream output = exchange.getResponseBody();
		String response;
		int i;
		ArrayList<String[]> parsedRequest;
		String artist = WebLogin.getUsername(exchange);
		String songname;
		String albumname;
		int length;
		String file;

		//exchange.getResponseHeaders().add("Content-type", "text/plain");
		exchange.sendResponseHeaders(200, 0);

		parsedRequest = parsePost(exchange);

		for (String[] requestPart : parsedRequest)
		{
			if (requestPart == null || requestPart[0] == null)
				continue;
			else if (requestPart[0].equals("songname"))
				songname = requestPart[1].trim();
			else if (requestPart[0].equals("albumname"))
				albumname = requestPart[1].trim();
			else if (requestPart[0].equals("length"))
			{
				try
				{
					length = Integer.parseInt(requestPart[1].trim());
				}
				catch (NumberFormatException e)
				{
					response = "<html>Error: invalid length\n";
					response += "<br /><a href=\"/home\">homepage</a></html>";
					output.write(response.getBytes());
					output.close();
					return;
				}
			}
			else if (requestPart[0].equals("upload"))
				file = requestPart[1];
		}
		
		response = "<html>Successfully uploaded song!\n";
		response += " <br /><a href=\"/home\">homepage</a></html>";
		output.write(response.getBytes());
		output.close();
	}
}
