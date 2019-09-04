package chapter06.mina.T01.start;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter
{
    private final static Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private String msg;

    public ClientHandler(String msg)
    {
        this.msg = msg;
    }

    @Override
    public void sessionOpened(IoSession session) {
        logger.info("send->"+msg);
        session.write(msg);

    }

}
