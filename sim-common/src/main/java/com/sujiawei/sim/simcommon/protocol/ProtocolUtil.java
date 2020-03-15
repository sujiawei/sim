package com.sujiawei.sim.simcommon.protocol;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午2:05]
 */
public class ProtocolUtil {

    private final static String ENCODE_CHARSET = "UTF-8";
    private final static String DECODE_CHARSET = "UTF-8";

    public static String byteBufToJsonString(ByteBuf buffer) throws Exception {
        byte[] req = new byte[buffer.readableBytes()];
        buffer.readBytes(req);
        return new String(req, DECODE_CHARSET);
    }

    public static Protocol transferByteBuf(ByteBuf buffer) throws Exception {
        return new Gson().fromJson(byteBufToJsonString(buffer), Protocol.class);
    }

    public static Protocol transferString(String msg) throws Exception {
        return new Gson().fromJson(msg, Protocol.class);
    }

    public static Protocol buildLoginProtocol(String userId) {
        return Protocol.builder().from(userId).to("0").type(MsgType.LOGIN).msg("login").build();
    }

    public static Protocol buildMsgProtocol(String userId, String to, String msg) {
        return Protocol.builder().from(userId).to(to).type(MsgType.MSG).msg(msg).build();
    }
}
