package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.VO.ConsultantVO;
import com.jok.zxserver.domain.entity.Consultant;
import com.jok.zxserver.domain.entity.Schedule;
import com.jok.zxserver.mapper.ConsultantMapper;
import com.jok.zxserver.service.ConsultantService;
import com.jok.zxserver.service.ScheduleService;
import com.jok.zxserver.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/4 19:25
 */
@Service
public class ConsultantServiceImpl extends ServiceImpl<ConsultantMapper, Consultant> implements ConsultantService {
    @Autowired
    ConsultantMapper consultantMapper;

    @Autowired
    ScheduleService scheduleService;

    @Override
    public List<ConsultantVO> getOnlineConsultantList() {
        Date date = new Date();
        return getConsultantVOS(date);
    }

    @Override
    public List<ConsultantVO> getOnlineConsultantList(Date date) {
        return getConsultantVOS(date);
    }

    @Override
    public ConsultantVO getConsultantInfoById(Integer consultantId) {
        return getConsultantVO(consultantId);
    }


    /**
     * 根据日期获取在线咨询师
     * @param date
     * @return
     */
    private List<ConsultantVO> getConsultantVOS(Date date) {

        List<Schedule> scheduleByTime = scheduleService.getScheduleByDate(date);

        if(scheduleByTime == null){
            return null;
        }

        //存储相应Consultant的满足时间要求的安排记录
        HashMap<Integer, List<Schedule>> scheduleListHashMapWithId = new HashMap<>();
        for (Schedule s : scheduleByTime) {
            List<Schedule> schedules = scheduleListHashMapWithId.get(s.getConsultantId());
            if (schedules == null) {
                schedules = new ArrayList<>();
            }
            schedules.add(s);
            scheduleListHashMapWithId.put(s.getConsultantId(), schedules);
        }

        //构建返回对象
        List<ConsultantVO> consultantVOs = new ArrayList<>();
        //根据consultantId来封装相应的安排记录
        for (Integer key : scheduleListHashMapWithId.keySet()) {
            ConsultantVO consultantVO = getConsultantVO(key);
            if (consultantVO == null) continue;
            consultantVOs.add(consultantVO);
        }
        return consultantVOs;
    }

    /**
     * 封装页面返回对象ConsultantVO
     * @param consultantId
     * @return
     */
    private ConsultantVO getConsultantVO(Integer consultantId) {
        //根据id获取Consultant的信息
        Consultant consultant = consultantMapper.selectById(consultantId);
        //封装页面返回对象
        ConsultantVO consultantVO = new ConsultantVO();
        if (consultant != null) {
            BeanUtils.copyProperties(consultant, consultantVO);
        } else {
            return null;
        }
//
//        //为各Consultant赋值安排信息——每天的时段列表
//        HashMap<String, List<HashMap<String, String>>> schedules = new HashMap<>();
//
//        //按需封装
//        for (Schedule s : schedulesWithId) {
//            String date = s.getDate();
//            String timeStart = s.getTimeStart();
//            String timeEnd = s.getTimeEnd();
////            Integer maxPerson = s.getMaxPerson();//todo 修改添加最大时间最小时间
//            Integer minTime = s.getMinTime();
//            Integer maxTime = s.getMaxTime();
//            Integer status = s.getStatus();
//            List<HashMap<String, String>> schedulesWithDate = schedules.get(date);//一个工作日的安排列表每一项都相当于一个时间段对象，每一项包括开始时间，结束时间，最大预约时间，最小预约时间，以及状态
//            if (schedulesWithDate == null) {
//                schedulesWithDate = new ArrayList<>();
//            }
//            HashMap<String, String> scheduleWithDate = new HashMap<>();//一个时间段的集合
//            scheduleWithDate.put("sid",s.getId());
//            scheduleWithDate.put("start", CommonUtil.getTimeHM(timeStart));
//            scheduleWithDate.put("end", CommonUtil.getTimeHM(timeEnd));
//            scheduleWithDate.put("minTime", String.valueOf(minTime));//todo 修改添加最大时间最小时间
//            scheduleWithDate.put("maxTime", String.valueOf(maxTime));
//            scheduleWithDate.put("status", String.valueOf(status));
//
//            schedulesWithDate.add(scheduleWithDate);
//
//            schedules.put(date, schedulesWithDate);
//            consultantVO.setSchedules(schedules);
//        }
        return consultantVO;
    }

}
