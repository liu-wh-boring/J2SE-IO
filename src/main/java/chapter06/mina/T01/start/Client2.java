package chapter06.mina.T01.start;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class Client2
{
    public static final Logger logger = LoggerFactory.getLogger(Client2.class);

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();

        connector.getSessionConfig().setReadBufferSize(2028);
        connector.getSessionConfig().setBothIdleTime(10);
        connector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                        LineDelimiter.WINDOWS.getValue(),
                        LineDelimiter.WINDOWS.getValue())));

        LoggingFilter lf = new LoggingFilter();
        lf.setSessionOpenedLogLevel(LogLevel.DEBUG);
        connector.getFilterChain().addFirst("logger", lf);

        connector.setHandler(new ClientHandler("gaomeshisa"));

        ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 8787));

        future.addListener(new IoFutureListener<ConnectFuture>() {
            public void operationComplete(ConnectFuture future) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IoSession session = future.getSession();
                System.out.println("++++++++++++++++++++++++++++");
            }
        });

//        future.addListener((ConnectFuture f)->{
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            IoSession session = f.getSession();
//            System.out.println("++++++++++++++++++++++++++++");
//        });
//        System.out.println("*************");
    }
}
