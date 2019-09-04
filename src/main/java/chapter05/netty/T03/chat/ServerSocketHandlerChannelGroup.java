package chapter05.netty.T03.chat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerSocketHandlerChannelGroup extends SimpleChannelInboundHandler<String> {

	/**
	 * 用来存储所有的channel，channel失活自动从容器中剔除
	 */
	private final static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception 
	{
		group.forEach((i)->{
			if(i == ctx.channel())
			{
				i.writeAndFlush("自己："+msg+"\n");
			}
			else
			{
				i.writeAndFlush("from"+i.remoteAddress()+" :"+msg+"\n");
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
		group.forEach((i)->{
			i.writeAndFlush(ctx.channel().remoteAddress()+" online"+"\n");
		});
		group.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception 
	{
		group.forEach((i)->{
			i.writeAndFlush(ctx.channel().remoteAddress()+" offline"+"\n");
		});
	}
}
