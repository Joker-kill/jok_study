package com.jok.zxserver.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author JOKER
 * create time 2024/8/19 13:54
 */
public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 解析收到的 WebSocket 消息
        String payload = message.getPayload();
        JSONObject jsonObject = new JSONObject(Boolean.parseBoolean(payload));
        String userId = jsonObject.getString("userId");
        String token = jsonObject.getString("token");

        System.out.println("配置ws请求处理器");
        // 进一步处理收到的 userId 和 token
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("连接关闭");
        // 处理连接关闭的逻辑，例如更新在线用户列表、发送通知等
    }
}
