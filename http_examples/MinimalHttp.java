import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class MinimalHttp
{
	public static void main(String[] args) throws Exception
	{
		HttpServer server =  HttpServer.create(new InetSocketAddress(80), 0);
		server.createContext("/test", new MinimalHandle());
		server.start();
	}
}
