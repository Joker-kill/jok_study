package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.LastMessage;
import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.ChatRecord;
import com.jok.zxserver.domain.VO.MessageListItemVO;
import com.jok.zxserver.domain.VO.MessageVO;
import com.jok.zxserver.domain.common.constant.MessageStatus;
import com.jok.zxserver.domain.entity.Consultant;
import com.jok.zxserver.domain.entity.Message;
import com.jok.zxserver.domain.entity.Relationship;
import com.jok.zxserver.domain.entity.User;
import com.jok.zxserver.mapper.ConsultantMapper;
import com.jok.zxserver.mapper.MessageMapper;
import com.jok.zxserver.mapper.RelationshipMapper;
import com.jok.zxserver.mapper.UserMapper;
import com.jok.zxserver.service.ChatService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/21 20:18
 */
@Service
public class ChatServiceImpl extends ServiceImpl<MessageMapper, Message> implements ChatService {
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RelationshipMapper relationshipMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ConsultantMapper consultantMapper;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据userId查询用户的所用消息
     *
     * @param userId
     * @return
     */
    @Override
    public List<Message> getMessage(String userId) {
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper
                .eq("sender_id", userId)
                .or()
                .eq("receiver_id", userId);
        List<Message> messages = messageMapper.selectList(messageQueryWrapper);
        return messages;
    }

    /**
     * 保存消息
     *
     * @param message
     * @return
     */
    @Override
    public boolean saveMessage(MessageDO message)  {
        //提取消息内容并封装
        Message newMessage = new Message();
        newMessage.setContent(message.getContent());
        try {
            newMessage.setDate(simpleDateFormat.parse(message.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newMessage.setReceiverId(message.getReceiverId());
        newMessage.setId(message.getId());
        newMessage.setSenderId(message.getSenderId());
        newMessage.setType(message.getType());
        newMessage.setStatus(MessageStatus.NOT_READ);// 0未读 1 已读
        return this.save(newMessage);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<MessageVO> getMessageVO(String userId) {


        // 根据用户id查询联系列表
        List<Relationship> relationships = relationshipMapper.selectRelationsByUId(userId);
        System.out.println(relationships);
        // 遍历联系列表，查询并封装对应联系用户的消息
        List<MessageVO> messageVOList = new ArrayList<>();
        for (Relationship r : relationships) {
            // 创建一个联系用户展示对象用于封装用户与该联系用户的消息
            MessageVO messageVO = new MessageVO();

            // 判断并获取好友id
            String friendId = r.getUser1Id().equals(userId) ? r.getUser2Id() : r.getUser1Id();
            messageVO.setUserId(friendId);
            messageVO.setDate(simpleDateFormat.format(r.getLastDate()));
            System.out.println(friendId);

            User user = userMapper.selectById(friendId);
            System.out.println(user);

            // 判断好友的角色，根据角色不同采用不同方式获取用户基础信息并赋值
            if(user.getRole().equals("cst")){
                QueryWrapper<Consultant> consultantQueryWrapper = new QueryWrapper<>();
                consultantQueryWrapper.eq("user_id",friendId);
                Consultant consultant = consultantMapper.selectOne(consultantQueryWrapper);

                messageVO.setName(consultant.getName());
                messageVO.setAvatar(consultant.getAvatar());
            }
            else if(user.getRole().equals("usr")){
                messageVO.setName(user.getNickName());
                messageVO.setAvatar(user.getAvatar());
            }

            // 获取彼此间的聊天消息列表
            List<Message> messages = messageMapper.selectMessageListByRelation(userId, friendId);
            messageVO.setMessageList(messages);
            // 获取对方发送而未被用户所阅读的消息数量
            messageVO.setNotReadCount(messageMapper.selectNotReadCount(userId,friendId));
            messageVOList.add(messageVO);
        }

        return messageVOList;
    }

    @Override
    public Integer getNoteCount(String uerId) {

        return messageMapper.selectAllNotReadCountByUserId(uerId);
    }

    @Override
    public boolean changeMessageStatus(String receiverId, String senderId,Integer status) {
        return messageMapper.updateStatus(receiverId,senderId,status)>0;
    }

    @Override
    public List<MessageListItemVO> getMessageList(String userId) {
        List<LastMessage> messages = messageMapper.selectLastMessagesByUserId(userId);
        List<MessageListItemVO> messageList = new ArrayList<>();
        for(LastMessage m :messages){
            MessageListItemVO messageListItemVO = new MessageListItemVO();
            messageListItemVO.setLastMessage(m.getContent());
            messageListItemVO.setUserId(m.getUserId());
            messageListItemVO.setLastDate(m.getDate());
            getSetInfo(m.getUserId(),(userName,avatar)->{
                messageListItemVO.setName(userName);
                messageListItemVO.setAvatar(avatar);
            });
            messageListItemVO.setNotReadCount(messageMapper.selectNotReadCount(userId,m.getUserId()));
            messageList.add(messageListItemVO);
        }
        return messageList;
    }

    @Override
    public List<Message> getRecordsWithUId(String userId, String fid) {
        return messageMapper.selectMessageListByRelation(userId,fid);
    }

    @FunctionalInterface
    interface SetFunction{
        void set(String userName,String avatar);
    }

    private void getSetInfo(String userId,SetFunction fun){
        User user = userMapper.selectById(userId);
        System.out.println(user);
        if(user.getRole().equals("cst")){
            QueryWrapper<Consultant> consultantQueryWrapper = new QueryWrapper<>();
            consultantQueryWrapper.eq("user_id",userId);
            Consultant consultant = consultantMapper.selectOne(consultantQueryWrapper);
            fun.set(consultant.getName(),consultant.getAvatar());
        }
        else if(user.getRole().equals("usr")){
            fun.set(user.getNickName(),user.getAvatar());
        }
    }
}
