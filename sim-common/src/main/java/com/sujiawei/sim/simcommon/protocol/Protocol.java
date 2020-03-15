package com.sujiawei.sim.simcommon.protocol;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

/**
 * @Description: [一句话描述该类的功能]
 * @Author: [sujiawei]
 * @CreateDate: [20/3/8 下午2:00]
 */
@Data
@Builder
public class Protocol {

    private String from;

    private String to;

    private String msg;

    private Integer type;

    public String toGsonString() {
        return new Gson().toJson(this);
    }
}
