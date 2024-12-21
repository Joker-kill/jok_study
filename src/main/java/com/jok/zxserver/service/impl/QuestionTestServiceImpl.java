package com.jok.zxserver.service.impl;

import com.anwen.mongo.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.QuestionTestDO;
import com.jok.zxserver.domain.entity.Question;
import com.jok.zxserver.domain.entity.QuestionTest;
import com.jok.zxserver.service.QuestionTestService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/11/24 14:23
 */

@Service
public class QuestionTestServiceImpl extends ServiceImpl<QuestionTest> implements QuestionTestService {
    @Override
    public List<QuestionTest> getQuestionTestList() {
        List<QuestionTest> simpleQuestionTestList= queryCommand("{},{_id:1,description:1,createTime:1,author:1}");

        return simpleQuestionTestList;
    }

    @Override
    public boolean createQuestionTest(QuestionTestDO questionTestDO, InputStream upload){
        try{
            Workbook wb = new XSSFWorkbook(upload);
            // 获取sheet对象
            Sheet sheet = wb.getSheetAt(0);
            int i = 0;
            // 遍历sheet的所有行
            for (Row row:sheet){
                // 题目内容从第第三行开始
                if(i <2){
                    i++;
                    continue;
                }
                int j = 0;
                List<Question> questionList = new ArrayList<>();
                // 遍历每行的所有单元格获取数据
                for (Cell cell:row){
                    CellType cellType = cell.getCellType();
                    System.out.println(cellType);
                    switch (cellType){
                        case STRING:
                            if(cell.getRichStringCellValue()!=null && !cell.getRichStringCellValue().equals("")){
                                System.out.println(cell.getStringCellValue());
                            }
                            break;
                        case NUMERIC:
                            System.out.println(String.valueOf(cell.getNumericCellValue()));
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
