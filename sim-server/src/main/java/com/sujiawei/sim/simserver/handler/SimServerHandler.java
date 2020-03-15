package com.sujiawei.sim.simserver.handler;

import com.sujiawei.sim.simcommon.protocol.MsgType;
import com.sujiawei.sim.simcommon.protocol.Protocol;
import com.sujiawei.sim.simcommon.protocol.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午1:41]
 */
public class SimServerHandler extends SimpleChannelInboundHandler<String> {

    private SimMessageHandler simMessageHandler;

    public SimServerHandler(SimMessageHandler simMessageHandler) {
        this.simMessageHandler = simMessageHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("chanelActive>>>>>>>");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(" >>> receive msg : " + msg);
        Protocol protocol = ProtocolUtil.transferString(msg);
        switch (protocol.getType()) {
            case MsgType.LOGIN:
                simMessageHandler.processLogin(ctx.channel(), protocol);
                break;
            case MsgType.MSG:
                simMessageHandler.processMsg(ctx.channel(), protocol);
                break;
            case MsgType.PING:
                simMessageHandler.processPing(ctx.channel(), protocol);
                break;
            default:
                System.out.println("type is invalid");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("get server exception :"+cause.getMessage());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                System.out.println("定时检测客户端端是否存活");
                ServerHeartBeatHandler.process(ctx) ;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

}
