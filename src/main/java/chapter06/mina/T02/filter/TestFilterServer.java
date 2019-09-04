package chapter06.mina.T02.filter;

import com.mina.chapter01.start.ServerHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoEvent;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.util.ReferenceCountingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class TestFilterServer
{
    public static final Logger logger = LoggerFactory.getLogger(TestFilterServer.class);

    public static void main(String[] args) throws IOException
    {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setBothIdleTime(60);
        acceptor.getSessionConfig().setReadBufferSize(1024);

       // LoggingFilter loggingFilter = new LoggingFilter();

       //acceptor.addListener("logger",loggingFilter);
      //  acceptor.getFilterChain().addFirst("logger",loggingFilter);
        ReferenceCountingFilter filter = new ReferenceCountingFilter(new TestFilter());
        acceptor.getFilterChain().addLast("test",filter);
        acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter(1,2,30, TimeUnit.SECONDS, IoEventType.MESSAGE_RECEIVED));
        acceptor.setHandler(new TestFilterHandler());

        acceptor.bind(new InetSocketAddress(8888));
    }
}
