package chapter05.netty.T04.TestFlow;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

	public static void main(String[] args) 
	{
		 NioEventLoopGroup boss = new NioEventLoopGroup();
		 NioEventLoopGroup worker = new NioEventLoopGroup();
		 
		 try
		 {
			 ServerBootstrap bootstrap = new ServerBootstrap();
			 bootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());
			 ChannelFuture future =  bootstrap.bind(8888).sync();
			 future.channel().closeFuture().sync();
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 finally
		 {
			 boss.shutdownGracefully();
			 worker.shutdownGracefully();
		 }

	}

}
