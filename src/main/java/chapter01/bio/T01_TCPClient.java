package chapter01.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class T01_TCPClient {

	private static Socket socket;

	public static void main(String[] args) throws IOException 
	{
		T01_TCPClient.client();
	}
	
	private static void client() throws IOException
	{
		socket = new Socket();
		socket.bind(new InetSocketAddress(56765));
		socket.connect(new InetSocketAddress("127.0.0.1",8899));
		
		OutputStream os = socket.getOutputStream();
		os.write("998989".getBytes());
	}

}
