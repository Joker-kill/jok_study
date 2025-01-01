package com.jok.zxserver.domain.entity.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author JOKER
 * create time 2024/12/23 10:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreStruct {
    private int no;
    private String content;
    private int score;
}
