package com.jok.zxserver.domain.DO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/9/17 10:38
 */
@Data
public class LastMessage{
    private String date;
    private String content;
    private String type;
    private Integer status;
    private String userId;
}
