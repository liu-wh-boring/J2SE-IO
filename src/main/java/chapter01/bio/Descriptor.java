package chapter01.bio;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Descriptor 
{
	public  static void receive(Socket socket ,String msg)
	{
		desc(socket,"<----",msg);
	}
	
	public  static void send(Socket socket ,String msg)
	{
		desc(socket,"---->",msg);
	}
	
	public  static void desc(Socket socket ,String direct,String msg)
	{
		InetSocketAddress local = (InetSocketAddress) socket.getLocalSocketAddress();
		InetSocketAddress remote = (InetSocketAddress) socket.getRemoteSocketAddress();
		System.out.println(local.getHostName()+":"+local.getPort()+direct + remote.getHostName()+":"+remote.getPort()+"       "+msg);
	}
}
