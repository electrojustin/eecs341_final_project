import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.sql.*;

public class WebArtistProfile implements HttpHandler
{
	public void handle (HttpExchange exchange) throws IOException
	{	
		String response;
		String username = WebLogin.getUsername(exchange);
		String profileUsername;
		OutputStream output = exchange.getResponseBody();
		ArtistProfile profile = null;

		if (username == null)
		{
			response = "<html>You are not signed in";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		profile = getProfile(username);

		if (profile == null)
		{
			response = "<html>You are not registered as an artist";
			response += " <br /> <a href=\"/home\">homepage</a></html>";
			exchange.sendResponseHeaders(200, response.length());
			output.write(response.getBytes());
			output.close();
			return;
		}

		response = "<html><h3>Welcome to the Artist Portal!</h3>\n";
		response += "<br />Name: " + profile.name + "\n";
		response += "<br />Number of songs made: " + profile.numSongsMade + "\n";
		response += "<br />Number of buys: " + profile.numBuys + "\n";
		response += "<br />Most popular song: " + profile.mostPopular + "\n";

		response += "<br /><br /><h4>Create a new album</h4>\n";
		response += "<form action=\"/newalbum\">\n";
		response += "Album name:\n";
		response += "<br /><input type=\"text\" name=\"albumname\">\n";
		response += "<br />Album genre:\n";
		response += "<br /><input type=\"text\" name=\"genre\">\n";
		response += "<br />Label name:\n";
		response += "<br /><input type=\"text\" name=\"label\">\n";
		response += "<br /><input type=\"submit\" value=\"Create\">\n";
		response += "</form>\n";

		response += "<br /><br /><h4>Upload a new song</h4>\n";
		response += "<form action=\"/newsong\" method=\"POST\" enctype=\"multipart/form-data\">";
		response += "Song name:\n";
		response += "<br /><input type=\"text\" name=\"songname\">\n";
		response += "<br />Album name:\n";
		response += "<br /><input type=\"text\" name=\"albumname\">\n";
		response += "<br />Length:\n";
		response += "<br /><input type=\"text\" name=\"length\">\n";
		response += "<br /><input type=\"file\" name=\"upload\">\n";
		response += "<br /><input type=\"submit\" name=\"Create\">\n";
		response += "</form>\n";

		response += "<br /><br />\n";
		response += "<a href=\"/home\">homepage</a></html>";

		exchange.sendResponseHeaders(200, response.length());
		output.write(response.getBytes());
		output.close();

	}

	public static ArtistProfile getProfile(String user)
	{
		try
		{
			String artistQuery = "SELECT artistID, artistName " +
					"FROM Artist WHERE username = \"" + user + "\"";
			ResultSet rs1 = MuzikrDB.rawQuery(artistQuery);

			if(!rs1.next())
				return null;

			ArtistProfile profile = new ArtistProfile();
			profile.username = user;
			profile.name = rs1.getString("artistName");
		
			int artistID = rs1.getInt("artistID");

			String numMadeQuery = "SELECT COUNT(*) " +
					"FROM Creates c " +
					"WHERE c.artistID = " + artistID;
			ResultSet rs2 = MuzikrDB.rawQuery(numMadeQuery);
		
			rs2.next();
			profile.numSongsMade = rs2.getInt(1);

			String buyQuery = "SELECT o.songName, o.albumName, COUNT(*) " +
					"FROM Creates c, Owns o " +
					"WHERE c.artistID = \"" + artistID + "\" " +
					"AND o.songName = c.songName " + 
					"AND o.albumName = c.albumName " +
					"GROUP BY o.songName, o.albumName" ;
			ResultSet rs3 = MuzikrDB.rawQuery(buyQuery);

			String popularSong = null;
			int totalBuys = 0;
			int maxBuys = 0;

			while (rs3.next())
			{
				int buys = rs3.getInt(3);
				totalBuys += buys;
				if (buys > maxBuys)
				{
					maxBuys = buys;
					popularSong = rs3.getString(1);
				}
			}

			profile.numBuys = totalBuys;
			profile.mostPopular = popularSong;
	
			return profile;
		}
		catch (SQLException e)
		{
			System.out.println("SQL Error");
			return null;
		}
	}

}
