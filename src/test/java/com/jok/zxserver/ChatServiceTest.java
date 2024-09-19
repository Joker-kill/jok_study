package com.jok.zxserver;

import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.mapper.MessageMapper;
import com.jok.zxserver.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

/**
 * @Author JOKER
 * create time 2024/8/26 17:13
 */
@SpringBootTest
public class ChatServiceTest {
    @Autowired
    ChatService chatService;
    @Autowired
    MessageMapper messageMapper;
    @Test
    void test1() throws ParseException {
        MessageDO messageDO = new MessageDO();
        messageDO.setId("vfsgvsfhsfhnfs");
        messageDO.setReceiverId("wfeedgesrh");
        messageDO.setReceiverId("gsdgdsg");
        messageDO.setContent("srgrvdeghr");
        messageDO.setType("text");
        chatService.saveMessage(messageDO);
    }

    @Test
    void ttt(){
//        System.out.println(messageMapper.selectMessageListByUserId("opXOV5GXAAZ1VbTUosbRSBS50e1c"));
    }
}
