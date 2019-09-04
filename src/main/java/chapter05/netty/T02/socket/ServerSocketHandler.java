package chapter05.netty.T02.socket;

import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerSocketHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception 
	{
		System.out.println("s-客户端地址："+ctx.channel().remoteAddress());
		System.out.println("s-获取消息："+msg);
		
		ctx.channel().writeAndFlush("from server"+UUID.randomUUID().toString());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	

}
