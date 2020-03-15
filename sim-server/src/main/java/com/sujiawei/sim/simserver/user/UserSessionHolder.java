package com.sujiawei.sim.simserver.user;

import com.sujiawei.sim.simcommon.user.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import io.netty.channel.socket.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午3:33]
 */
public class UserSessionHolder {

    private static final Map<String, SocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);
    private static final Map<String, String> SESSION_MAP = new ConcurrentHashMap<>(16);

    public static void saveSession(String userId,String userName){
        SESSION_MAP.put(userId, userName);
    }

    public static void removeSession(String userId){
        SESSION_MAP.remove(userId) ;
    }

    public static void put(String id, SocketChannel socketChannel) {
        CHANNEL_MAP.put(id, socketChannel);
    }

    public static SocketChannel get(String id) {
        return CHANNEL_MAP.get(id);
    }

    public static Map<String, SocketChannel> getMAP() {
        return CHANNEL_MAP;
    }

    public static void remove(SocketChannel nioSocketChannel) {
        CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

    /**
     * 获取注册用户信息
     * @param nioSocketChannel
     * @return
     */
    public static UserInfo getUserId(SocketChannel nioSocketChannel){
        for (Map.Entry<String, SocketChannel> entry : CHANNEL_MAP.entrySet()) {
            SocketChannel value = entry.getValue();
            if (nioSocketChannel == value){
                String key = entry.getKey();
                String userName = SESSION_MAP.get(key);
                UserInfo info = new UserInfo(key,userName) ;
                return info ;
            }
        }
        return null;
    }

    public static void userOffLine(UserInfo userInfo, SocketChannel channel) {
        if (userInfo != null){
            System.out.println("用户[{" + userInfo.getUserName() + "}]下线");
            removeSession(userInfo.getUserId());
        }
        remove(channel);
    }
}
