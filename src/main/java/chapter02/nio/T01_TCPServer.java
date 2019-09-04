package chapter02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T01_TCPServer {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		T01_TCPServer.start();
		T01_TCPServer.listen();
	}
	
	private final static int PORT = 8989;
	
	private final static ConcurrentHashMap<SocketAddress,SocketChannel>  SESSIONS = new ConcurrentHashMap<SocketAddress,SocketChannel>();
	
	private final static ExecutorService pool = Executors.newCachedThreadPool();
	
	private static Selector selector;
	
	public static void start() throws IOException, InterruptedException
	{
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress(PORT));
		serverSocket.configureBlocking(false);
		
		selector = Selector.open();
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);

	}
	
	public static void listen()
	{
		pool.execute(()->{
			
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
						if(key.isValid())
						{
							if(key.isAcceptable())
							{
								ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
								
								SocketChannel socketChannel = ssc.accept();
								socketChannel.configureBlocking(false);
								socketChannel.register(selector, SelectionKey.OP_READ);
								socketChannel.register(selector, SelectionKey.OP_WRITE);
								System.out.println(socketChannel.getRemoteAddress()+"连上了");
								
								SESSIONS.forEach((k,v)->{
									
									ByteBuffer buffer;
									try 
									{
										buffer = ByteBuffer.wrap((socketChannel.getRemoteAddress().toString()+" is online now").getBytes());
										v.write(buffer);
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
								});
								
								SESSIONS.put(socketChannel.getRemoteAddress(), socketChannel);
							}
						}
					}
				}
			
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				try 
				{
					selector.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			
		});
	}

}
