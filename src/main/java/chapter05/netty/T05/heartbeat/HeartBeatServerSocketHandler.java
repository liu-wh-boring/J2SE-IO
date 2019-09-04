package chapter05.netty.T05.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatServerSocketHandler extends ChannelInboundHandlerAdapter 
{

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		if( evt instanceof IdleStateEvent)
		{
			IdleStateEvent event = (IdleStateEvent)evt;
			switch(event.state())
			{
			case ALL_IDLE:
				System.out.println("你挂了吗?");
				break;
			case READER_IDLE:
				System.out.println("已经好久没有收到你的信息了");
				break;
			case WRITER_IDLE:
				System.out.println("已经好久没有给你发送信息了");
				break;
			}
		}
	}
	
	

}
