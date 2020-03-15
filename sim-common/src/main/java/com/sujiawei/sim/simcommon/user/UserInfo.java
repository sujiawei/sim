package com.sujiawei.sim.simcommon.user;

import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * Function: 用户信息
 *
 * @author crossoverJie
 *         Date: 2018/12/24 02:33
 * @since JDK 1.8
 */
@Data
public class UserInfo {
    private String userId ;
    private String userName ;

    @Tolerate
    public UserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
