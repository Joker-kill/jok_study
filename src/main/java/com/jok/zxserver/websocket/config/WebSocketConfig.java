package com.jok.zxserver.websocket.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author JOKER
 * create time 2024/8/10 20:43
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        System.out.println("注册ws请求拦截器");
//        registry.addHandler(new MyWebSocketHandler(), "/ws")
//                .addInterceptors(new MyHandshakeInterceptor())  // 注册自定义拦截器
//                .setAllowedOrigins("*");
//    }
}
