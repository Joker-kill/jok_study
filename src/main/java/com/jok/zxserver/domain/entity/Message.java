package com.jok.zxserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author JOKER
 * create time 2024/8/3 19:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String id;
    private String content;
    private String senderId;
    private String receiverId;
    private Date date;
    private String type;
    private Integer status;
}
