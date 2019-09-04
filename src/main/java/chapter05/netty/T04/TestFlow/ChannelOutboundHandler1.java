package chapter05.netty.T04.TestFlow;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ChannelOutboundHandler1 extends ChannelOutboundHandlerAdapter 
{

	private final static String name = ChannelOutboundHandler1.class.getName();

	@Override
	public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"bind");
	}

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"connect");
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"disconnect");
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"close");
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"deregister");
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"read");
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		System.out.println(name+":"+"write");
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"flush");
	}
	
	@Override
	public boolean isSharable() {
		System.out.println(name+":"+"isSharable");
		return super.isSharable();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"handlerAdded");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println(name+":"+"handlerRemoved");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println(name+":"+"exceptionCaught");
	}
}
