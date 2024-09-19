package com.jok.zxserver.domain.VO;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/6 14:21
 */
@Data
public class AvailableVO {
    private String date;
    private Integer maxTime;
    private Integer minTime;
    List<HashMap<String,String>> timeList;
}
