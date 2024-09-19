package com.jok.zxserver.websocket.interceptor;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Author JOKER
 * create time 2024/8/18 21:50
 */
public class MyHandshakeInterceptor  implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();
        List<String> connection = headers.getConnection();
        for(String s:connection){
            System.out.println(s);
        }
        System.out.println("attributes==============================>");
        Set<String> strings = attributes.keySet();
        for (String key:strings){
            System.out.println(attributes.get(key).toString());
        }
        // 解析查询参数
        String userId = "test111"; // 从 queryParams 解析出 userId
        String token = "vfsinkfgdfbshfgfn"; // 从 queryParams 解析出 token

        // 存储到 WebSocket session attributes 中
        attributes.put("userId", userId);
        attributes.put("token", token);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
