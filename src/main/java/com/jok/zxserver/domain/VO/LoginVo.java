package com.jok.zxserver.domain.VO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/8/4 16:05
 */
@Data
public class LoginVo {
    private String openId;
    private String role;
    private String token;
}
