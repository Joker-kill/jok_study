package com.jok.zxserver.websocket;

import com.alibaba.fastjson.JSON;
import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.domain.entity.Relationship;
import com.jok.zxserver.service.ChatService;
import com.jok.zxserver.service.RelationshipService;
import com.jok.zxserver.utils.TokenUtil;
import com.jok.zxserver.websocket.config.HttpSessionConfigurator;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author JOKER
 * create time 2024/8/10 20:45
 */

@Component
@ServerEndpoint(value = "/ws", configurator = HttpSessionConfigurator.class)
public class WebSocketServer {

    private ChatService chatService = (ChatService) ApplicationContextHelper.getBean("chatServiceImpl");

    private RelationshipService relationshipService =(RelationshipService)ApplicationContextHelper.getBean("relationshipServiceImpl");

    private static ConcurrentHashMap<String, String> sessionIdMap = new ConcurrentHashMap<String, String>();

    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<String, Session>();


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws EncodeException, IOException {

        Map<String, Object> userProperties = config.getUserProperties();
        List<String> properties = (List<String>) userProperties.get("userToken");
        String userToken = properties.get(0);
        System.out.println(userToken);
        String userId = TokenUtil.getId(userToken.toString());
        System.out.println(userId);

        sessionIdMap.put(session.getId(), userId);
        sessionPool.put(userId, session);
        System.out.println("session open. ID:" + session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println(session);
        String userId = sessionIdMap.get(session.getId());
        sessionPool.remove(userId);
        System.out.println("session close. ID:" + session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, ParseException {
        // 解析并保存消息
        MessageDO messageDO = JSON.parseObject(message, MessageDO.class);
        String receiverId = messageDO.getReceiverId();
        Session receiverSession = sessionPool.get(receiverId);
        chatService.saveMessage(messageDO);

        // 如果是第一次聊天，建立聊天关系
        boolean related = relationshipService.isRelated(messageDO);
        if(!related){
            relationshipService.save(new Relationship(null,messageDO.getSenderId(),messageDO.getReceiverId(),new Date()));
        }else{
            relationshipService.changeLastDate(messageDO.getSenderId());
        }


        if (receiverSession != null) {
            RemoteEndpoint.Basic basicRemote = receiverSession.getBasicRemote();
            basicRemote.sendText(message);
//            RemoteEndpoint.Basic basicRemote1 = session.getBasicRemote();
//            basicRemote1.sendText(message);
        } else {
            RemoteEndpoint.Basic basicRemote1 = session.getBasicRemote();
            basicRemote1.sendText("ok");
        }


        System.out.println(message);
        System.out.println("get client msg. ID:" + session.getId() + ". msg:" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
