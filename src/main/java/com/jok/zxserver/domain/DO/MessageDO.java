package com.jok.zxserver.domain.DO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JOKER
 * create time 2024/8/21 20:23
 */
@Data
public class MessageDO implements Serializable {
    private String id;
    private String type;
    private String content;
    private String senderId;
    private String receiverId;
    private String date;
}
