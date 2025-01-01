package com.jok.zxserver.domain.entity.question;

import com.anwen.mongo.annotation.ID;
import com.anwen.mongo.enums.IdTypeEnum;

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
    @ID(type = IdTypeEnum.ASSIGN_ULID)
    private String id;
    private Integer no;
    private String questionContent;
    private String type;// todo 改成枚举类
    private Integer unityNo;
    private List<Answer> answerCollection;
}
