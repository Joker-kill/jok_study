package com.jok.zxserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author JOKER
 * create time 2024/8/28 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {
    private Integer id;
    private String user1Id;
    private String user2Id;
    private Date lastDate;
}
