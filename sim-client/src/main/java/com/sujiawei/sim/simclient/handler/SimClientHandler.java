package com.sujiawei.sim.simclient.handler;

import com.sujiawei.sim.simcommon.protocol.MsgType;
import com.sujiawei.sim.simcommon.protocol.Protocol;
import com.sujiawei.sim.simcommon.protocol.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午4:25]
 */
public class SimClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("<<< receive msg " + msg);
        Protocol protocol = ProtocolUtil.transferString((String) msg);
        System.out.println("<<< receive msg " + protocol.toString());
        switch(protocol.getType()) {
            case MsgType.MSG :
                System.out.println(">> receive msg :" + protocol.getMsg() + " form : " + protocol.getFrom());
                break;
            default:
                System.out.println("protocol : " + protocol.toString());
                break;
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("<<< receive msg " + msg);
        Protocol protocol = ProtocolUtil.transferString(msg);
        System.out.println("<<< receive msg " + protocol.toString());
        switch(protocol.getType()) {
            case MsgType.MSG :
                System.out.println(">> receive msg :" + protocol.getMsg() + " form : " + protocol.getFrom());
                break;
            default:
                System.out.println("protocol : " + protocol.toString());
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("chanelActive>>>>>>>");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive>>>>>");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("SimClientHandler.exceptionCaught" + cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
