package com.jok.zxserver.domain.entity.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @Author JOKER
 * create time 2024/12/23 10:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRecord {
    private Integer id;
    private String userId;
    private String testName;
    private Date testTime;
    private short status;
}
