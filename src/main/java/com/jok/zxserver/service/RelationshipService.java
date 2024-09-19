package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.DO.MessageDO;
import com.jok.zxserver.domain.entity.Relationship;

/**
 * @Author JOKER
 * create time 2024/8/28 19:43
 */
public interface RelationshipService extends IService<Relationship> {

    boolean isRelated(MessageDO messageDO);

    boolean changeLastDate(String senderIId);
}
