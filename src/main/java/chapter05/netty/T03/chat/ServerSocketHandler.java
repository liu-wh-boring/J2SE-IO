package chapter05.netty.T03.chat;

import java.util.Vector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerSocketHandler extends SimpleChannelInboundHandler<String> {

	private final static Vector<ChannelHandlerContext> sessions = new Vector<ChannelHandlerContext>();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception 
	{
		sessions.forEach((i)->{
			if(i == ctx)
			{
				ctx.channel().writeAndFlush("自己："+msg+"/n");
			}
			else
			{
				i.channel().writeAndFlush("from"+ctx.channel().remoteAddress()+" :"+msg+"/n");
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception 
	{
		if(sessions.isEmpty())
		{
			sessions.add(ctx);
		}
		else
		{
			for(ChannelHandlerContext i : sessions)
			{
				i.channel().writeAndFlush(i.channel().remoteAddress()+" online"+"/n");
			}
			sessions.add(ctx);
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception 
	{
		sessions.remove(ctx);
		if(!sessions.isEmpty())
		{
			for(ChannelHandlerContext i : sessions)
			{
				i.writeAndFlush(i.channel().remoteAddress()+" offline"+"/n");
			}
		}
	}

}
