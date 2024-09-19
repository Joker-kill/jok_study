package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.entity.Schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/4 18:10
 */

public interface ScheduleService extends IService<Schedule> {
    /**
     * 根据日期获取对应日期的所有安排记录
     * @param date
     * @return 对应日期的所有安排记录
     */
    List<Schedule> getScheduleByDate(Date date);

    HashMap<String,List<Schedule>> getScheduleByConsultantId(Integer cid);

    HashMap<String,List<Schedule>> getScheduleByConsultantId(Integer cid,String Date);


    int addOrUpdateSchedule(List<Schedule> schedules);

    boolean removeScheduleById(String id);
}
