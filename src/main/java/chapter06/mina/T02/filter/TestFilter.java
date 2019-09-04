package chapter06.mina.T02.filter;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.FilterEvent;

public class TestFilter implements IoFilter
{

    @Override
    public void init() throws Exception {
        System.out.println("init...............");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destrory................");
    }

    @Override
    public void onPreAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        System.out.println("onPreAdd..............");
    }

    @Override
    public void onPostAdd(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        System.out.println("onPostAdd..............");
    }

    @Override
    public void onPreRemove(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        System.out.println("onPreRemove..............");
    }

    @Override
    public void onPostRemove(IoFilterChain parent, String name, NextFilter nextFilter) throws Exception {
        System.out.println("onPostRemove..............");
    }

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        System.out.println("sessionCreated..............");
    }

    @Override
    public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
        System.out.println("sessionOpened..............");
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
        System.out.println("sessionClosed..............");
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
        System.out.println("sessionIdle..............");
    }

    @Override
    public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws Exception {
        System.out.println("exceptionCaught..............");
    }

    @Override
    public void inputClosed(NextFilter nextFilter, IoSession session) throws Exception {
        System.out.println("inputClosed..............");
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
        System.out.println("messageReceived..............");
    }

    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        System.out.println("messageSent..............");
    }

    @Override
    public void filterClose(NextFilter nextFilter, IoSession session) throws Exception {
        System.out.println("filterClose..............");
    }

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
        System.out.println("filterWrite..............");
    }

    @Override
    public void event(NextFilter nextFilter, IoSession session, FilterEvent event) throws Exception {
        System.out.println("event   ..............");
    }
}
