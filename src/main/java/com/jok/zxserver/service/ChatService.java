package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.ChatRecord;
import com.jok.zxserver.domain.VO.MessageListItemVO;
import com.jok.zxserver.domain.VO.MessageVO;
import com.jok.zxserver.domain.entity.Message;

import java.text.ParseException;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/20 21:33
 */

public interface ChatService extends IService<Message> {

    List<Message> getMessage(String userId);

    boolean saveMessage(MessageDO message) throws ParseException;

    List<MessageVO> getMessageVO(String userId);

    Integer getNoteCount(String uerId);

    boolean changeMessageStatus(String receiverId,String senderId,Integer status);

    List<MessageListItemVO> getMessageList(String userId);

    List<Message> getRecordsWithUId(String userId, String fid);
}
