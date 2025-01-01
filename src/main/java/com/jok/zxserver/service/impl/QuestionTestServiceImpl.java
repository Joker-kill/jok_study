package com.jok.zxserver.service.impl;

import com.anwen.mongo.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.QuestionTestDO;
import com.jok.zxserver.domain.entity.question.Answer;
import com.jok.zxserver.domain.entity.question.Question;
import com.jok.zxserver.domain.entity.question.QuestionTest;
import com.jok.zxserver.service.QuestionTestService;
import com.jok.zxserver.utils.CommonUtil;
import com.jok.zxserver.utils.TitleMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author JOKER
 * create time 2024/11/24 14:23
 */

@Service
public class QuestionTestServiceImpl extends ServiceImpl<QuestionTest> implements QuestionTestService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<QuestionTest> getQuestionTestList() {
        List<QuestionTest> simpleQuestionTestList = queryCommand("{},{_id:1,description:1,createTime:1,author:1}");
        return simpleQuestionTestList;
    }

    /**
     * 描述：通过上传的表格文件创建测试题
     * @param questionTestDO question的基本信息交换对象
     * @param upload 文件内容输入流
     * @return
     */
    @Override
    public boolean createQuestionTest(QuestionTestDO questionTestDO, InputStream upload) {
        Map<Integer, TitleMapper> titleMapperMap = new HashMap<>();
        titleMapperMap.put(0, new TitleMapper("no", Integer.class));
        titleMapperMap.put(1, new TitleMapper("questionContent", String.class));
        titleMapperMap.put(2, new TitleMapper("type", String.class));
        titleMapperMap.put(3, new TitleMapper("unityNo", Integer.class));

        try (Workbook wb = new XSSFWorkbook(upload)) {
            // 获取sheet对象
            Sheet sheet = wb.getSheetAt(0);
            List<Question> questionList = new ArrayList<>();
            int rowIndex = 0;

            // 遍历sheet的所有行
            for (Row row : sheet) {
                // 题目内容从第三行开始
                if (rowIndex++ < 2) {
                    continue;
                }

                Question question = new Question();
                question.setId(CommonUtil.generateId(10));
                List<Answer> answerList = new ArrayList<>();
                int nowColumnIndex = 0;

                // 遍历每行的所有单元格获取数据
                for (Cell cell : row) {
                    if (cell.getColumnIndex() != nowColumnIndex) {
                        continue;
                    }

                    Object value = getCellValue(cell);
                    TitleMapper titleMapper = titleMapperMap.get(cell.getColumnIndex());

                    if (titleMapper != null) {
                        if (titleMapper.getType() == Integer.class && value instanceof Double) {
                            value = ((Double) value).intValue();
                        }
                        invokeSetter(Question.class, question, titleMapper.getName(), titleMapper.getType(), value);
                    } else if (cell.getColumnIndex() >= titleMapperMap.size()) {
                        Answer answer = createAnswer(row, cell, value);
                        if (answer != null) {
                            answerList.add(answer);
                            nowColumnIndex = cell.getColumnIndex() + 1;
                        }
                    }
                    nowColumnIndex++;
                }

                question.setAnswerCollection(answerList);
                questionList.add(question);
            }

            QuestionTest questionTest = new QuestionTest();
            BeanUtils.copyProperties(questionTestDO,questionTest);
            questionTest.setQuestionList(questionList);
            logger.info(questionTest.toString());
            Boolean save = this.save(questionTest);
            logger.info("是否保存成功：{}", save);
        } catch (Exception e) {
            logger.error("Error creating question test", e);
            return false;
        }

        return true;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                logger.info("表格内容：{}",cell.getRichStringCellValue().getString());
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                logger.info("表格内容：{}",cell.getNumericCellValue());
                return cell.getNumericCellValue();
            default:
                return null;
        }
    }

    /**
     * 描述：创建answer对象
     * @param row 当前所在行
     * @param cell answer内容单元格
     * @param value answer 内容
     * @return
     */
    private Answer createAnswer(Row row, Cell cell, Object value) {
        logger.info("当前列：{}",cell.getColumnIndex());
        String answerContent = String.class.cast(value);
        logger.info("答案内容:{}",answerContent);
        if (answerContent == null || answerContent.isEmpty()) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(CommonUtil.generateId(16));
        answer.setAnswer(answerContent);
        Cell scoreCell = row.getCell(cell.getColumnIndex() + 1);
        logger.info("分数列识别到类型：{}",scoreCell.getCellType());
        Object cellValue = getCellValue(scoreCell);
        answer.setScore(Double.class.cast(cellValue));
        return answer;
    }

    /**
     * 描述: 根据属性名，来执行对应的setter方法
     * @param questionClass 需要执行setter方法的对象的class
     * @param question 需要执行setter方法的对象实例
     * @param fieldName 属性名
     * @param valueType 属性类型
     * @param value 属性值
     */
    private void invokeSetter(Class<?> questionClass, Question question, String fieldName, Class<?> valueType, Object value) {
        if (questionClass == null || question == null || fieldName == null || fieldName.isEmpty() || valueType == null || value == null) {
            throw new IllegalArgumentException("Input parameters cannot be null or empty");
        }

        String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        try {
            Method declaredMethod = questionClass.getDeclaredMethod(setMethodName, valueType);
            declaredMethod.invoke(question, valueType.cast(value));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Error invoking setter method: {}", setMethodName, e);
            throw new IllegalStateException("Error invoking setter method: " + setMethodName, e);
        }
    }



}
