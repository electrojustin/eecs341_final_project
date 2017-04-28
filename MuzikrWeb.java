import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;

public class MuzikrWeb
{
	public static ArrayList<Integer> sessionIds;
	public static ArrayList<String> loginUsernames;
	
	public static ArrayList<String> getKeyValues (String uri)
	{
		ArrayList<String> ret = new ArrayList<String>();
		String[] keypairs = uri.split("&");

		for (String keypair : keypairs)
		{
			String[] parsedPair = keypair.split("=");

			if (parsedPair.length != 2)
				return null;

			ret.add(parsedPair[0]);
			ret.add(parsedPair[1]);
		}

		return ret;
	}

	public static void main (String[] args) throws IOException
	{
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);

		sessionIds = new ArrayList<Integer>();
		loginUsernames = new ArrayList<String>();

		server.createContext("/login", new WebLogin());
		server.createContext("/logout", new WebLogout());
		server.createContext("/home", new WebHome());
		server.start(); 
	}
}
