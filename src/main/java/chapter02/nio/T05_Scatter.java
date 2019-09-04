package chapter02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * 分散（scatter）
 * 从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
 * 因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中
 * read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写
 *
 */
public class T05_Scatter 
{
	private final static int PORT = 8899;
	
	public static void main(String[] args) throws IOException
	{
		startServer();
	}
	
	public static void startServer() throws IOException
	{
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.bind(new InetSocketAddress(PORT));
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		
		while(true)
		{
			selector.select();
			/**
			 * TODO:不知道此处循环为甚不能停止
			 * 一直有key进来
			 */
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
						SocketChannel sc = ssc.accept();
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
						System.out.println(sc.getRemoteAddress().toString()+" is online....");
					}
					else if( key.isReadable() )
					{
						SocketChannel sc = (SocketChannel)key.channel();
						ByteBuffer header = ByteBuffer.allocate(6);
						ByteBuffer body = ByteBuffer.allocate(4);
						while(sc.read(new ByteBuffer[] {header,body}) > 0 ) //scatter
						{
							
						}
						System.out.println(Arrays.toString(header.array()));
						System.out.println(Arrays.toString(body.array()));
					}
				}
			}
		}
		
	}
}
