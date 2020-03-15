package com.sujiawei.sim.simserver.server;

import com.sujiawei.sim.simserver.handler.SimMessageHandler;
import com.sujiawei.sim.simserver.handler.SimServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetSocketAddress;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 上午10:35]
 */
public class SimServer {

//    @Value("${im.port}")
    private static final int imPort = 9000;

    private static final EventLoopGroup boss = new NioEventLoopGroup();
    private static final EventLoopGroup work = new NioEventLoopGroup();

    public void start() throws Exception {
        ServerBootstrap serverBootstrap =
                new ServerBootstrap().group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(imPort))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new IdleStateHandler(10, 0, 0))
                                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                .addLast(new SimServerHandler(new SimMessageHandler()));
                    }
                });
        ChannelFuture future = serverBootstrap.bind().sync();
        if (future.isSuccess()) {
            System.out.println("启动 sim server 成功. listening port : " + imPort);
        }
    }

    public static void shutdown(){
        work.shutdownGracefully();
        boss.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        SimServer simServer = new SimServer();
        simServer.start();
    }
}
