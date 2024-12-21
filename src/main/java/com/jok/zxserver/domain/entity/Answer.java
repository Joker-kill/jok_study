package com.jok.zxserver.domain.entity;

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
    private String id;
    private String answer;
    private double score;
}
