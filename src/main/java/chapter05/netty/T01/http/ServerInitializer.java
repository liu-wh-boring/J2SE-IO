package chapter05.netty.T01.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("HttpServerCodec", new HttpServerCodec());
		pipeline.addLast("HttpServerHandler",new ServerHttpHandler());
	}

}
