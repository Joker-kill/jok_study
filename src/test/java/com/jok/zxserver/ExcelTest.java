package com.jok.zxserver;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Author JOKER
 * create time 2024/12/8 16:44
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExcelTest {

    @Test
    void testLodProperties() throws IOException {
        List<List<String>> titleHead = new ArrayList<>();

        List<String> heads = new ArrayList<>();
        heads.add("序号");
        heads.add("题目内容");
        heads.add("题目类型");
        for (int i = 0; i < 5; i++) {
            heads.add("答案" + i);
            heads.add("答案" + i + "分值");
        }
        titleHead.add(heads);
        String fileName = "G:test.xlsx";

    }
}

