package com.jok.zxserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.ChatRecord;
import com.jok.zxserver.domain.VO.MessageListItemVO;
import com.jok.zxserver.domain.VO.MessageVO;
import com.jok.zxserver.domain.common.constant.MessageStatus;
import com.jok.zxserver.domain.entity.Message;
import com.jok.zxserver.service.ChatService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/28 21:18
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    ChatService chatService;

    @GetMapping
    public R<List<MessageVO>> getUserMessageList(@PathParam("userId") String userId){
        System.out.println("收到来自用户id为"+userId+"的消息请求");
        return R.ok(chatService.getMessageVO(userId));
    }

    @PostMapping
    public R<String> readMessage(@RequestBody String objJson){
        JSONObject jsonObject = JSON.parseObject(objJson);
        String senderId = jsonObject.getString("senderId");
        String receiverId = jsonObject.getString("receiverId");
        chatService.changeMessageStatus(receiverId,senderId,MessageStatus.IS_READ);
        return R.ok("更新成功");
    }

    @GetMapping("/count")
    public R<Integer> getAllNotCount(@PathParam("userId") String userId){
        System.out.println("收到来自用户id为"+userId+"的请求未读数量的请求");
        return R.ok(chatService.getNoteCount(userId));
    }

    @GetMapping("/list")
    public R<List<MessageListItemVO>> getMessageListInfo(@PathParam("userId") String userId){
        return R.ok(chatService.getMessageList(userId));
    }

    @GetMapping("/chat")
    public R<List<Message>> getChatRecords(@PathParam("mid") String mid, @PathParam("fid") String fid){
        System.out.println("查询聊天记录："+mid+"  &   "+fid);
        return R.ok(chatService.getRecordsWithUId(mid,fid));
    }

}
