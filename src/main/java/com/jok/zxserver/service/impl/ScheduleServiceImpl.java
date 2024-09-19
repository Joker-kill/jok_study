package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.entity.Booking;
import com.jok.zxserver.domain.entity.Schedule;
import com.jok.zxserver.mapper.BookingMapper;
import com.jok.zxserver.mapper.ConsultantMapper;
import com.jok.zxserver.mapper.ScheduleMapper;
import com.jok.zxserver.service.BookingService;
import com.jok.zxserver.service.ScheduleService;
import com.jok.zxserver.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @Author JOKER
 * create time 2024/8/4 18:10
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
    @Autowired
    ScheduleMapper scheduleMapper;

//    @Autowired
//    ConsultantMapper consultantMapper;

    @Autowired
    BookingMapper bookingMapper;

//    @Autowired
//    BookingMapper bookingMapper;

    private CommonUtil commonUtil  = new CommonUtil(2);
    @Override
    public List<Schedule> getScheduleByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);
        QueryWrapper<Schedule> scheduleQueryWrapper = new QueryWrapper<>();
        scheduleQueryWrapper
                .ge("date",dateString)
                .eq("status",1)
                .ne("deleted",1)
                .orderByAsc("time_start");

        List<Schedule> schedules = scheduleMapper.selectList(scheduleQueryWrapper);
        for (Schedule s:schedules){
            System.out.println(s);
        }
        return schedules;
    }

    @Override
    public  HashMap<String,List<Schedule>> getScheduleByConsultantId(Integer cid) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("consultant_id", cid);
        queryWrapper.ne("deleted",1);
        List<Schedule> scheduleList = scheduleMapper.selectList(queryWrapper);
        HashMap<String, List<Schedule>> scheduleMap = new HashMap<>();
        for(Schedule s:scheduleList){
            String date = s.getDate();
            List<Schedule> schedules = scheduleMap.get(date);
            if(scheduleMap.get(date)==null){
                schedules = new ArrayList<>();
            }
            s.setTimeStart(CommonUtil.getTimeHM(s.getTimeStart()));
            s.setTimeEnd(CommonUtil.getTimeHM(s.getTimeEnd()));
            schedules.add(s);

            scheduleMap.put(s.getDate(),schedules);
        }
        return scheduleMap;
    }

    @Override
    public HashMap<String, List<Schedule>> getScheduleByConsultantId(Integer cid, String Date) {

        return null;
    }

    @Override
    @Transactional
    public int addOrUpdateSchedule(List<Schedule> schedules) {
        for(Schedule schedule:schedules) {
            if(schedule.getId()==null){
                schedule.setId(String.valueOf(commonUtil.nextId()));
                schedule.setDeleted(0);
                scheduleMapper.insert(schedule);
            }else {
                if (schedule.getDeleted() == 1) {
                    scheduleMapper.deleteScheduleById(String.valueOf(schedule.getId()));
                } else {
                    scheduleMapper.updateById(schedule);
                }
            }
        }

        System.out.println(schedules);
        this.saveOrUpdateBatch(schedules);
        return 1;// 表示添加成功 todo 添加 “添加”安排记录执行状态常量-0-失败最大最小时间不为整十 -1-成功
    }

    @Override
    public boolean removeScheduleById(String id) {
        boolean exists = bookingMapper.exists(new QueryWrapper<Booking>().eq("sid", id));
        if(!exists){
            return scheduleMapper.deleteScheduleById(String.valueOf(id))>0;
        }
        //todo 定义并抛出已有预约不能删除异常
        return false;
    }
}
