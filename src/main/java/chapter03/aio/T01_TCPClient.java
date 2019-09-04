package chapter03.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class T01_TCPClient 
{
	  public static void main(String... args) throws Exception 
	  {
	        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
	        client.connect(new InetSocketAddress("localhost", 9888));
	        client.write(ByteBuffer.wrap("test".getBytes())).get();
	  }
}
