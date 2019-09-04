package chapter05.netty.T02.socket;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientSocketHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception 
	{
		System.out.println("c-服务端地址："+ctx.channel().remoteAddress());
		System.out.println("c-获取消息："+msg);
		
		ctx.writeAndFlush(LocalDateTime.now().toString());
	//	super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("shishi");
		super.channelActive(ctx);
	}

	
	
}
