package chapter05.netty.T06.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class WebSocketSocketHandler extends ChannelInboundHandlerAdapter 
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception 
	{
		System.out.println(msg.getClass());
	}
	
	
}
