package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.domain.entity.Relationship;
import com.jok.zxserver.mapper.RelationshipMapper;
import com.jok.zxserver.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author JOKER
 * create time 2024/8/28 19:43
 */
@Service
public class RelationshipServiceImpl extends ServiceImpl<RelationshipMapper, Relationship> implements RelationshipService {

    @Autowired
    RelationshipMapper relationshipMapper;
    @Override
    public boolean isRelated(MessageDO messageDO) {

//        QueryWrapper<Relationship> relationshipQueryWrapper = new QueryWrapper<>();
//        relationshipQueryWrapper.eq("user1_id",messageDO.getSenderId());
//        relationshipQueryWrapper.or();
//        relationshipQueryWrapper.eq("user2_id",messageDO.getReceiverId());

        return relationshipMapper.selectRelationship(messageDO.getSenderId(),messageDO.getReceiverId())>0;
    }

    @Override
    public boolean changeLastDate(String senderIId) {
        return relationshipMapper.updateLastDate(new Date(),senderIId);
    }
}
