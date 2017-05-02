//Displays the details for a song

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebSongDetails implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{	
		String response;
		ArrayList<String> parsedRequest = MuzikrWeb.getKeyValues(exchange.getRequestURI().getQuery());
		String songKeyword = null;
		String albumKeyword = null;
		OutputStream output = exchange.getResponseBody();
		int i = 0;
		SongDetail details;

		for (i = 0; i < parsedRequest.size(); i += 2)
		{
			if (parsedRequest.get(i).equals("songname"))
				songKeyword = parsedRequest.get(i+1);
			else if (parsedRequest.get(i).equals("albumname"))
				albumKeyword = parsedRequest.get(i+1);
			else
			{
				response = "<html>Error: invalid search key ";
				response += parsedRequest.get(i);
				response += " <br /> <a href=\"/home\">homepage</a></html>";
				exchange.sendResponseHeaders(200, response.length());
				output.write(response.getBytes());
				output.close();
				return;
			}
		}

		if (songKeyword == null || albumKeyword == null)
		{
			response = "<html>Error: invalid search key ";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		details = new SongDetail(songKeyword, albumKeyword);
		details.getLength();
		details.getArtists();
		details.getProducerInfo();

		if (details == null)
		{
			response = "<html>Error: no such song ";
			response += parsedRequest.get(i);
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;		
		}

		response = "<html><h3>Song Details For ";
		response += songKeyword;
		response += ": </h3>\n";
		response += "<br />Song name: " + details.name + "\n";
		response += "<br />Album name: " + details.name + "\n";
		response += "<br />Artist(s): ";
		for (i = 0; i < details.artists.length-1; i++)
			response += details.artists[i] +", ";
		response += details.artists[i] + "\n";
		response += "<br />Producer name: " + details.producerName + "\n";
		if (details.producerUsername != null)
			response += "<br />Producer username: " + details.producerUsername + "\n";
		response += "<br />Producer address: " + details.producerAddress + "\n";
		response += "<br />Song length (minutes): " + details.songLength + "\n";

		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();
	} 
}
