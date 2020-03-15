package com.sujiawei.sim.simclient.handler;

import io.netty.channel.Channel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/9 下午8:59]
 */
@Data
@Builder
public class MsgHandler {

    private String userId;

    private Channel channel;

    @Tolerate
    public MsgHandler() {
    }

    @Tolerate
    public MsgHandler(Channel channel) {
        this.channel = channel;
    }

}
