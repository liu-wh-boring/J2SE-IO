package chapter05.netty.T05.heartbeat;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class HeartBeatServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new IdleStateHandler(5,10,30));
		pipeline.addLast(new HeartBeatServerSocketHandler());
	}
}
