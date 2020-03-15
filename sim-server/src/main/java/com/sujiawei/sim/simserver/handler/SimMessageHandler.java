package com.sujiawei.sim.simserver.handler;

import com.sujiawei.sim.simcommon.protocol.MsgType;
import com.sujiawei.sim.simcommon.protocol.Protocol;
import com.sujiawei.sim.simcommon.util.NettyAttrUtil;
import com.sujiawei.sim.simserver.user.UserSessionHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import io.netty.channel.socket.SocketChannel;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午3:05]
 */
public class SimMessageHandler {

    public void processLogin(Channel channel, Protocol protocol) {
        UserSessionHolder.put(protocol.getFrom(), (SocketChannel) channel);
        UserSessionHolder.saveSession(protocol.getFrom(), protocol.getMsg());
    }

    public void processMsg(Channel channel, Protocol protocol) {
        String to = protocol.getTo();
        SocketChannel socketChannel = UserSessionHolder.get(to);
        ChannelFuture future = socketChannel.writeAndFlush(protocol.toGsonString());
        future.addListener((ChannelFutureListener) channelFuture ->
                System.out.println("服务端手动发送 Msg 成功=" + protocol));
    }

    public void processPing(Channel channel, Protocol protocol) {
        NettyAttrUtil.updateReaderTime(channel, System.currentTimeMillis());
        //向客户端响应 pong 消息
        Protocol ping = Protocol.builder().from("0").to(protocol.getFrom()).msg("ping").type(MsgType.PING).build();
        channel.writeAndFlush(ping.toGsonString()).addListeners((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                future.channel().close();
            }
        }) ;
    }
}
