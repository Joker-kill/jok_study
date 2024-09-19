package com.jok.zxserver.domain.DO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/8/4 15:24
 */
@Data
public class WxLoginDO {
    private String code;
    private String encryptedData;
    private  String iv;
    private String signature;
}
