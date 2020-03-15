package com.sujiawei.sim.simserver.handler;

import com.sujiawei.sim.simcommon.user.UserInfo;
import com.sujiawei.sim.simcommon.util.NettyAttrUtil;
import com.sujiawei.sim.simserver.user.UserSessionHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Service;

/**
 * Function:
 *
 * @author
 * Date: 2019-01-20 17:16
 * @since JDK 1.8
 */
@Service
public class ServerHeartBeatHandler {

    private int heartBeatTime = 20;

    public static void process(ChannelHandlerContext ctx) throws Exception {
        long heartBeatTime = 20 * 1000;
        Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
        long now = System.currentTimeMillis();
        if (lastReadTime != null && now - lastReadTime > heartBeatTime){
            UserInfo userInfo = UserSessionHolder.getUserId((NioSocketChannel) ctx.channel());
            if (userInfo != null){
                System.out.println("客户端[" + userInfo.getUserName() + "]心跳超时[" +  (now - lastReadTime) + "]ms，需要关闭连接!");
            }
            UserSessionHolder.userOffLine(userInfo, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }
    }
}
