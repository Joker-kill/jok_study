package com.jok.zxserver.domain.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author JOKER
 * create time 2024/12/21 15:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionTestDO {
    private String name;
    private String description;
    private String createTime;
    private String author;
}
