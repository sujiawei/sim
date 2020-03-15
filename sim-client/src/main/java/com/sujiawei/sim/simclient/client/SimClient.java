package com.sujiawei.sim.simclient.client;

import com.sujiawei.sim.simclient.handler.MsgHandler;
import com.sujiawei.sim.simclient.handler.SimClientHandler;
import com.sujiawei.sim.simcommon.protocol.Protocol;
import com.sujiawei.sim.simcommon.protocol.ProtocolUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Scanner;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午4:11]
 */
public class SimClient {

    private static final String IP = "127.0.0.1";
    private static final Integer PORT = 9000;

    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("sim-work"));

    public SocketChannel channel;

    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline()
                                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                                .addLast(new IdleStateHandler(0, 10, 0))
                                //拆包解码
                                .addLast(new LengthFieldPrepender(4))
                                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                .addLast(new SimClientHandler());
                    }
                });

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(IP, PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (future.isSuccess()) {
            System.out.println("启动 cim client 成功");
        }
        channel = (SocketChannel) future.channel();
    }

    public static void main(String[] args) {
        SimClient simClient = new SimClient();
        simClient.start();

        Scanner scanner = new Scanner(System.in);
        String userId = scanner.nextLine();
        Protocol loginProtocol = ProtocolUtil.buildLoginProtocol(userId);
        System.out.println("user login : " + loginProtocol.toString());
        ChannelFuture channelFuture1 = simClient.channel.writeAndFlush(loginProtocol.toGsonString());
        channelFuture1.addListener((ChannelFutureListener) channelFuture ->
                System.out.println("客户端手动发送 Login 成功"));

        while(true) {
            Scanner msgScanner = new Scanner(System.in);
            String to = msgScanner.nextLine();
            String msg = msgScanner.nextLine();
            Protocol msgProtocol = ProtocolUtil.buildMsgProtocol(userId, to, msg);
            System.out.println("user " + userId + " send msg : " + msg + " to : " + to + " protocol : " + msgProtocol);
            ChannelFuture channelFuture2 = simClient.channel.writeAndFlush(msgProtocol.toGsonString());
            channelFuture2.addListener((ChannelFutureListener) channelFuture ->
                    System.out.println("客户端手动发送 msg 成功"));
        }
    }
}
