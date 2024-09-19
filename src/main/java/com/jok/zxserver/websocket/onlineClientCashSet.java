package com.jok.zxserver.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @Author JOKER
 * create time 2024/8/23 16:43
 */
public class onlineClientCashSet {

    @Autowired
    RestTemplate restTemplate;

    private onlineClientCashSet(){}

    // 添加在线客户端
    public static boolean put(String clientId){
        return false;
    }

    // 移除在线客户端
}
