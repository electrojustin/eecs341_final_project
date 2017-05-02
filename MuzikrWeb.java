//Main file

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.sql.*;

public class MuzikrWeb
{
	//These variables keep track of who is actually logged in at any given time
	public static Semaphore loginSemaphore;
	public static ArrayList<Integer> sessionIds;
	public static ArrayList<String> loginUsernames;
	
	//Helper function for parsing the URL for key value pairs
	public static ArrayList<String> getKeyValues (String uri)
	{
		ArrayList<String> ret = new ArrayList<String>();
		String[] keypairs = uri.split("&");

		for (String keypair : keypairs)
		{
			String[] parsedPair = keypair.split("=");

			ret.add(parsedPair[0]);
			if (parsedPair.length != 2)
				ret.add(null);
			else
				ret.add(parsedPair[1]);
		}

		return ret;
	}

	//Main function from which we launch our HTTP server
	public static void main (String[] args) throws IOException
	{	
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);

		loginSemaphore = new Semaphore(1, true);
		sessionIds = new ArrayList<Integer>();
		loginUsernames = new ArrayList<String>();

		try
		{
			MuzikrDB.init();
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Error initializing database (class not found)");
			return;
		}
		catch(SQLException e)
		{
			System.out.println("Error initializing database (sql exception)");
			return;
		}

		server.createContext("/login", new WebLogin());
		server.createContext("/logout", new WebLogout());
		server.createContext("/home", new WebHome());
		server.createContext("/search", new WebSearch());
		server.createContext("/download", new WebDownload());
		server.createContext("/playlist", new WebPlaylist());
		server.createContext("/buy", new WebBuy());
		server.createContext("/selectplay", new WebPlaylistSelect());
		server.createContext("/viewplaylist", new WebViewPlaylist());
		server.createContext("/addtoplaylist", new WebAddToPlaylist());
		server.createContext("/removesong", new WebRemoveFromPlaylist());
		server.createContext("/details", new WebSongDetails());
		server.createContext("/profile", new WebUserProfile());
		server.createContext("/usersearch", new WebUserSearch());
		server.createContext("/artistportal", new WebArtistProfile());
		server.createContext("/newsong", new WebNewSong());
		server.createContext("/newalbum", new WebNewAlbum());
		server.createContext("/signup", new WebSignUp());
		server.createContext("/createuser", new WebCreateUser());
		server.createContext("/createplaylist", new WebCreatePlaylist());
		server.start(); 
	}
}
