package chapter02.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Gathering Writes是指数据从多个buffer写入到同一个channel
 *
 */
public class T05_Gather {

	public static void main(String[] args) throws IOException 
	{
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(true);
		sc.connect(new InetSocketAddress("127.0.0.1",8899));
		System.out.println("connected..........................");
		if(sc.finishConnect())
		{
			ByteBuffer header = ByteBuffer.wrap("header".getBytes());
			ByteBuffer body = ByteBuffer.wrap("body".getBytes());
			
			while(sc.write(new ByteBuffer[] {header,body}) > 0)
			{
				
			}
			sc.close();
			System.out.println("closed..........................");
		}
		
	}

}
