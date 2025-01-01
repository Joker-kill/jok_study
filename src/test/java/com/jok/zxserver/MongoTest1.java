package com.jok.zxserver;

import com.jok.zxserver.domain.entity.question.Answer;
import com.jok.zxserver.domain.entity.question.Question;
import com.jok.zxserver.domain.entity.question.QuestionTest;
import com.jok.zxserver.service.QuestionTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/11/24 14:19
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MongoTest1 {
    @Autowired
    private QuestionTestService questionTestService;
    @Test
    void testQuestionTestInsert(){
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            List<Answer> answers = new ArrayList<>();
            for (int j = 0; j < 3; j++){
                Answer a = new Answer(String.valueOf(System.currentTimeMillis())+j,"测试题"+i+"答案"+j,3+j/10.0);
                answers.add(a);
            }
            Question q = new Question(String.valueOf(System.currentTimeMillis())+i,i,"测试试题"+i,"radio",1,answers);
            questionList.add(q);
        }

        QuestionTest questionTest = QuestionTest.builder()
                .id(String.valueOf(System.currentTimeMillis()))
                .name("测试心理测试问卷")
                .author("lxl")
                .createTime(new SimpleDateFormat().format(new Date()))
                .questionList(questionList).description("测试mongodb")
                .build();

        questionTestService.save(questionTest);
    }

    @Test
    void testQuerySimpleData(){
        List<QuestionTest> questionTestList = questionTestService.getQuestionTestList();
        System.out.println(questionTestList);
    }
}
