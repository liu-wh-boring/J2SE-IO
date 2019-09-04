package chapter05.netty.T04.TestFlow;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 1)每一次链路（session/channel）的建立都会调用initChannel，将过滤器进行组合和mina不一样
 * 
 * 2）事件流向
 * 
 * 2.1）handlerAdded事件：将过滤机（handler）添加到channel上
 * 2.1.1）事件流向：添加顺序，无论添加到何处，要的是添加顺序
 * 
 * 2.2）handlerRemoved事件：将过滤器从channel上移除
 * 2.2.1）事件流向：3->2-1
 * 
 * 2.2）事件流向：根据ChannelPipeline顺序
 * 
 * 2.2.1)代码中ChannelPipeline的过滤器顺序 
 * ChannelInboundHandler1-->ChannelInboundHandler2-->ChannelInboundHandler3
 * 
 * 2.2.2)事件流向
  --------------------------------------			 --------------------------------------	---	
 | 	 读取动作并执行相关业务    |           | 	    执行相关业务并写动作       |                    
 -----------------------------------------          -----------------------------------------   
 						 /|\   		                						                \|/  		         
 -----------------------------------------          -----------------------------------------   
 | 	    ChannelInboundHandler3     |          | 	    ChannelOutboundHandler1    |         
 -----------------------------------------          -----------------------------------------   
 						 /|\                        						                \|/                 
  -----------------------------------------          -----------------------------------------  
 | 	    ChannelInboundHandler2     |           | 	 ChannelOutboundHandler2    |         
 -----------------------------------------          -----------------------------------------   
						 /|\                        					                    \|/                
  -----------------------------------------          -----------------------------------------  
 | 	    ChannelInboundHandler1     |           | 	 ChannelOutboundHandler3   |         
 -----------------------------------------          -----------------------------------------   
 						 /|\                        						               \|/               
 -----------------------------------------          -----------------------------------------   
 | 	   			 [ Socket.read() ]     		|         | 	   			 [ Socket.write() ]     		|
 |				接受到对端消息			|         |			    向对端发送消息			|            
 -----------------------------------------          -----------------------------------------   
 *
 *  2.2.3）inBound事件触发条件（不是很准确）
 *  channelRegistered:channdel注册到Socket上
 *  channelActive：注册后，激活信道
 *  channelRead：读取消息
 *  channelReadComplete：读取结束
 *  channelInactive：信道失活
 *  channelUnregistered：信道未注册
 *  
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> 
{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		System.out.println("initChannel被调用了");
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ChannelInboundHandler1());
		p.addLast(new ChannelInboundHandler2());
		p.addLast(new ChannelInboundHandler3());
		p.addLast(new ChannelOutboundHandler1());
		p.addLast(new ChannelOutboundHandler2());
		p.addLast(new ChannelOutboundHandler3());
	}

}
