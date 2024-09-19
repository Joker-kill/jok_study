package com.jok.zxserver.websocket.config;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author JOKER
 * create time 2024/8/19 14:25
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> headers = request.getHeaders();
        sec.getUserProperties().put("userToken",headers.get("token"));
        super.modifyHandshake(sec, request, response);
    }
}
