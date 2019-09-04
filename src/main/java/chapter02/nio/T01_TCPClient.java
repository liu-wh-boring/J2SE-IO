package chapter02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class T01_TCPClient {

	public static void main(String[] args) throws IOException 
	{
		start();
		listen();
	}
	
	private final static InetSocketAddress REMOTE = new InetSocketAddress("127.0.0.1",8989);
	
	private  static Selector selector;
	
	public static void start() throws IOException
	{
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		
		selector = Selector.open();
		/**
		 * 此处注意 如果两个颠倒配置，客户端会出现问题
		 * 不知道为什么
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.register(selector, SelectionKey.OP_READ);
		 * 
		 */
		socketChannel.register(selector, SelectionKey.OP_READ);
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(REMOTE);
		
	}
	
	public static void listen()
	{
		new Thread()
		{
			public void run()
			{
				try
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
							
							SocketChannel sc = (SocketChannel)key.channel();
							if(key.isValid())
							{
								if(key.isConnectable() && sc.finishConnect())
								{
									System.out.println("连上去了。。。。。。");
								}
								else if(key.isReadable())
								{
									ByteBuffer buffer = ByteBuffer.allocate(10);
									sc.read(buffer);
									byte[] b = buffer.array();
									System.out.println(sc.getLocalAddress().toString()+":"+new String(b));
								}
							}
						}
					}
					
				}
				catch(Exception e)
				{
					
				}
			}
		}.start();
		
	}

}
