package com.jok.zxserver.domain.entity.question;

import com.anwen.mongo.annotation.ID;
import com.anwen.mongo.annotation.collection.CollectionName;
import com.anwen.mongo.enums.IdTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/11/24 11:36
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@CollectionName("question_test")
@Builder
public class QuestionTest {
    @ID(type = IdTypeEnum.ASSIGN_ULID)
    private String id;
    private String name;
    private String description;
    private String createTime;
    private String author;
    private List<Question> questionList;
}
