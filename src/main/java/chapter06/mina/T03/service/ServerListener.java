package chapter06.mina.T03.service;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerListener implements IoServiceListener
{
    private final static Logger logger = LoggerFactory.getLogger(ServerListener.class);

    @Override
    public void serviceActivated(IoService service) throws Exception
    {
        logger.info("service acvtive");
    }

    @Override
    public void serviceIdle(IoService service, IdleStatus idleStatus) throws Exception {
        logger.info("service idle");
    }

    @Override
    public void serviceDeactivated(IoService service) throws Exception {
        logger.info("service reactive");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("session create");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info("session closed");
    }

    @Override
    public void sessionDestroyed(IoSession session) throws Exception {
        logger.info("session destroyed");
    }
}
