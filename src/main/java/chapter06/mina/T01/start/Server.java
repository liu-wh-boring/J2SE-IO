package chapter06.mina.T01.start;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Server {

	public static final Logger logger = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) throws IOException
	{
		//IoAcce
		IoAcceptor ioAcceptor = new NioSocketAcceptor();
		ioAcceptor.getSessionConfig().setBothIdleTime(10);
		ioAcceptor.getSessionConfig().setMaxReadBufferSize(2048);
		ioAcceptor.getSessionConfig().setReadBufferSize(1024);

		ioAcceptor.getFilterChain().addFirst( "codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.MAC.getValue(), LineDelimiter.MAC.getValue())));

		ioAcceptor.setHandler(new ServerHandler());

		ioAcceptor.bind(new InetSocketAddress(8787));

		logger.info("server is started .........");

	}

}
