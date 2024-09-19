package com.jok.zxserver.domain.DO;

import com.jok.zxserver.domain.entity.Schedule;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/9 10:24
 */
@Data
public class ScheduleDO {
    private Integer consultantId;
    private List<Schedule> changedScheduleList;
}
