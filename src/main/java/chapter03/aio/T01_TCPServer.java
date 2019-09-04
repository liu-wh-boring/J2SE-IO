package chapter03.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;

public class T01_TCPServer {
	
    public static void main(String args[]) throws Exception 
    {
    	T01_TCPServer.init();
    }
    
    public final static int PORT = 9888;
    public final static void init() throws IOException
    {
    	//1.定义线程组
    	AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(2));
    	
    	//2.启动一个服务端通道
    	AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(9900));
    	
    	//3.向系统注册一个感兴趣的事件，如接受数据，并把事件完成的处理器传递给系统
    	server.accept(null, new AcceptHandler());
    }
    
    static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,Object>
    {

		@Override
		public void completed(AsynchronousSocketChannel socketChannel, Object attachment) 
		{
			try 
			{
				System.out.println(socketChannel.getRemoteAddress().toString()+"  "+"is online");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			ByteBuffer src = ByteBuffer.wrap("00000".getBytes());
			socketChannel.write(src, attachment, new WriteHandler());
		}

		@Override
		public void failed(Throwable exc, Object attachment)
		{
			exc.printStackTrace();
		}
    	
    }
    
    static class WriteHandler implements CompletionHandler<Integer,Object>
    {

		@Override
		public void completed(Integer result, Object attachment) 
		{
			 
			System.out.println("done......");
		}
		
		@Override
		public void failed(Throwable exc, Object attachment) 
		{
			
		}
    	
    }

}


