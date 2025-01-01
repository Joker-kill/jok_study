package com.jok.zxserver.domain.entity.question;

import com.anwen.mongo.annotation.ID;
import com.anwen.mongo.enums.IdTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author JOKER
 * create time 2024/11/24 11:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @ID(type = IdTypeEnum.ASSIGN_ULID)
    private String id;
    private String answer;
    private double score;
}
