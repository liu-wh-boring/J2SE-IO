package chapter05.netty.T02.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client 
{

	public static void main(String[] args) throws InterruptedException 
	{
		EventLoopGroup group = new NioEventLoopGroup();
		
		try
		{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ClientInitializer());
			
			ChannelFuture future = bootstrap.connect("localhost", 8899).sync();
			future.channel().closeFuture().sync();
		}
		finally
		{
			group.shutdownGracefully();
		}
	}

}
