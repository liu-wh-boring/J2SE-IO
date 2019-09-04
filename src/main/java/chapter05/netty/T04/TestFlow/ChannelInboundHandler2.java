package chapter05.netty.T04.TestFlow;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ChannelInboundHandler2 extends ChannelInboundHandlerAdapter 
{

	private final static String name = ChannelInboundHandler2.class.getName();
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception 
	{
		System.out.println(name+":"+"channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(name+":"+"channelRead");
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println(name+":"+"userEventTriggered");
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"channelWritabilityChanged");
		super.channelWritabilityChanged(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(name+":"+"exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

	@Override
	protected void ensureNotSharable() {
		System.out.println(name+":"+"ensureNotSharable");
		super.ensureNotSharable();
	}

	@Override
	public boolean isSharable() {
		System.out.println(name+":"+"isSharable");
		return super.isSharable();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"handlerAdded");
	//	super.handlerAdded(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"handlerRemoved");
		super.handlerRemoved(ctx);
	}
	
}
