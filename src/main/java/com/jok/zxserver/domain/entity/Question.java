package com.jok.zxserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/11/24 11:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String id;
    private int no;
    private String questionContent;
    private String type;// todo 改成枚举类
    private int unityNo;
    private List<Answer> answerCollection;
}
