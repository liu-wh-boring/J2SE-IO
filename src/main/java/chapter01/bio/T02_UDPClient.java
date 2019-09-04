package chapter01.bio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class T02_UDPClient 
{
private final int port;
	
	private final String remoteHost;
	
	private final int remotePort;

	private DatagramSocket udp;
	
	T02_UDPClient(String remoteHost,int remotePort,int port)
	{
		
		this.port = port;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
	}
	
	private void client() throws Exception
	{
		
		udp = new DatagramSocket(port);
		udp.connect(new InetSocketAddress(remoteHost,remotePort));
		
		new Thread(()->
		{
			//接收
			byte[] data =new byte[1024];
			DatagramPacket recevie = new DatagramPacket(data,data.length);
			
			while(true)
			{
				try 
				{
					//此方法在接受数据报之前会一致阻塞
					udp.receive(recevie);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				
				System.out.println("from "+udp.getRemoteSocketAddress()+new String(data));
			}
	
		}).start();
		
		new Thread(()->{
			
			try
			{
				while(true)
				{
					TimeUnit.SECONDS.sleep(5);
					byte[] b = "hao123".getBytes();
					DatagramPacket send = new DatagramPacket(b,b.length);
					udp.send(send);
				}
			}
			catch(Exception e)
			{
				
			}
			
		}).start();
		
	}
	
	
	public static void main(String[] args) throws Exception 
	{
		new T02_UDPClient("127.0.0.1",8899,9988).client();
		new T02_UDPClient("127.0.0.1",9988,8899).client();
	}
}
