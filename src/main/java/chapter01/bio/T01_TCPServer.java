package chapter01.bio;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class T01_TCPServer 
{
	private static ServerSocket server;

	public static void main(String[] args) throws Exception
	{
		T01_TCPServer.server();
	}
	
	public static void server() throws Exception
	{
		server = new ServerSocket(8899);
		
		Socket socket = server.accept();
		try
		(
			InputStream  is = socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			DataInputStream dis = new DataInputStream(bis);
		)
		{
		
			byte[] b = new byte[10];
			while(dis.read(b) != -1)
			{
				Descriptor.receive(socket, new String(b,"UTF-8"));
			}
		}
		finally
		{
			
		}
		
		
	}
}
