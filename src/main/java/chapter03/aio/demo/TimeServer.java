package chapter03.aio.demo;



import java.io.IOException;

public class TimeServer 
{
	private final static int PORT = 8080;
	
    public static void main(String[] args) throws IOException 
    {
        //首先创建异步的时间服务器处理类，然后启动线程将AsyncTimeServerHandler启动
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(PORT);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
