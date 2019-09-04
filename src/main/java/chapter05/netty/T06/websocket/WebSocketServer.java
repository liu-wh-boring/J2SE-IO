package chapter05.netty.T06.websocket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WebSocketServer 
{
	public static void main(String[] args) 
	{
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			
			try
			{
				ServerBootstrap  bootstrap = new ServerBootstrap();
				bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.INFO))//针对bossgroup
				.childHandler(new WebSocketServerInitializer());
				
				ChannelFuture future = bootstrap.bind(8888).sync();
				future.channel().closeFuture().sync();
			}
			catch(Exception e)
			{
				
			}
			finally
			{
				bossGroup.shutdownGracefully();
				workerGroup.shutdownGracefully();
			}
	}
}
