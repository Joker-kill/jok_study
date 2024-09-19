package com.jok.zxserver.domain.DO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/8/4 16:18
 */
@Data
public class UserDO {
    private String nickName;
    private String openId;
    private String avatarUrl;
    private int gender;
}
