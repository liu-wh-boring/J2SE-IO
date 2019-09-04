package chapter05.netty.T03.chat;

import com.netty.chapter03.chat.ServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server 
{
	public static void main(String[] args) 
	{
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			
			try
			{
				ServerBootstrap  bootstrap = new ServerBootstrap();
				bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ServerInitializer());
				
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
