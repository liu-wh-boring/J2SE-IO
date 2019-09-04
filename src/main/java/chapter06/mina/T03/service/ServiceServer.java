package chapter06.mina.T03.service;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableAspectJAutoProxy
public class ServiceServer
{

    public static void main(String[] args) throws IOException
    {

        SimpleIoProcessorPool<NioSession> processorPool = new SimpleIoProcessorPool<NioSession>(NioProcessor.class,10);
        IoAcceptor acceptor = new NioSocketAcceptor(processorPool);

        acceptor.addListener(new ServerListener());
        acceptor.setHandler(new ServerHandler());

        acceptor.bind(new InetSocketAddress(8888));
        System.out.println("server init .......");

    }
}
