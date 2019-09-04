package chapter02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class T02_UDP 
{
	
	private  static Selector selector;
	
	static
	{
		try 
		{
			selector = Selector.open();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		init(new InetSocketAddress("127.0.0.1",7777),new InetSocketAddress("127.0.0.1",8888));
		init(new InetSocketAddress("127.0.0.1",8888),new InetSocketAddress("127.0.0.1",7777));
		listen();
	}
	
	public static void init(InetSocketAddress remote,InetSocketAddress local) throws IOException
	{
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		
		channel.register(selector, SelectionKey.OP_READ);
	//	channel.register(selector, SelectionKey.OP_ACCEPT);
	//	channel.register(selector, SelectionKey.OP_CONNECT);
		
		channel.bind(remote);
		channel.connect(local);
		
		new Thread(()->{
			if(remote.equals(new InetSocketAddress("127.0.0.1",7777)))
			{
				while(true)
				{
					try
					{
						ByteBuffer buffer = ByteBuffer.wrap(LocalDateTime.now().toString().getBytes());
						channel.write(buffer);
						TimeUnit.SECONDS.sleep(2);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}) .start();
	}
	
	public static void listen() throws IOException
	{
		while(true)
		{
			selector.select(1000);
			
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();
			
			while(it.hasNext())
			{
				SelectionKey key = it.next();
				it.remove();
				
				DatagramChannel  channel = (DatagramChannel)key.channel();
				
				if(key.isValid())
				{
						if( key.isReadable() )
						{
							ByteBuffer dst = ByteBuffer.allocate(1024);
							channel.read(dst);
							System.out.println(new String(dst.array()));
						}
				}
			}
		}
	}
}
