package com.jok.zxserver.service;

import com.anwen.mongo.service.IService;
import com.jok.zxserver.domain.DO.QuestionTestDO;
import com.jok.zxserver.domain.entity.QuestionTest;

import java.io.InputStream;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/11/24 14:22
 */

public interface QuestionTestService extends IService<QuestionTest> {
    List<QuestionTest> getQuestionTestList();

    boolean createQuestionTest(QuestionTestDO questionTestDO, InputStream upload);
}
