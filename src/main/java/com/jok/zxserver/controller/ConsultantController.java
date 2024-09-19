package com.jok.zxserver.controller;

import com.jok.zxserver.domain.DO.ScheduleDO;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.ConsultantVO;
import com.jok.zxserver.domain.entity.Consultant;
import com.jok.zxserver.domain.entity.Schedule;
import com.jok.zxserver.service.ConsultantService;
import com.jok.zxserver.service.ScheduleService;
import com.jok.zxserver.utils.CommonUtil;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author JOKER
 * create time 2024/8/4 19:08
 */

@RestController
@RequestMapping("/consultant")
public class ConsultantController {
    private CommonUtil commonUtil = new CommonUtil(3);
    @Autowired
    ConsultantService consultantService;

    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/online")
    public R<List<ConsultantVO>> getOnlineConsultants() {
//        List<Consultant> consultantList = consultantService.list();
//        List<ConsultantVO> consultantVOList = new ArrayList<>();
//        for (Consultant c:consultantList){
//            ConsultantVO consultantVO = new ConsultantVO();
//            BeanUtils.copyProperties(c,consultantVO);
//            consultantVOList.add(consultantVO);
//        }
        return R.ok(consultantService.getOnlineConsultantList());
    }

    @PostMapping("/schedule")
    public R<String> addOrUpdateSchedule(@RequestBody ScheduleDO scheduleDO) {
        List<Schedule> changedScheduleList = scheduleDO.getChangedScheduleList();
//        Integer consultantId = scheduleDO.getConsultantId();
//
//        Set<Map.Entry<String, List<HashMap<String, Object>>>> entries = scheduleMap.entrySet();
//        List<Schedule> scheduleList = new ArrayList<>();
//        for(Map.Entry<String, List<HashMap<String, Object>>> s:entries){
//            String key = s.getKey();
//            List<HashMap<String, Object>> value = s.getValue();
//            System.out.println(value);
//            for(HashMap<String, Object> v: value){
//                Schedule schedule = new Schedule();
//
//                schedule.setStatus(Boolean.valueOf(String.valueOf(v.get("status")))?1:0);
//                System.out.println(schedule.getStatus());
//                schedule.setId(commonUtil.nextId());
//                schedule.setDate(key);
//                schedule.setTimeStart((String) v.get("start"));
//                schedule.setTimeEnd((String) v.get("end"));
//                schedule.setConsultantId(consultantId);
//                scheduleList.add(schedule);
//            }
//        }

        for (Schedule s : changedScheduleList) {
            System.out.println(s);
        }
        int i = scheduleService.addOrUpdateSchedule(changedScheduleList);
        return R.ok("成功");
    }

    @GetMapping("/getSchedule")
    public R<HashMap<String, List<Schedule>>> getSchedule(@RequestParam("cid") String cid) {
        System.out.println(cid);
        HashMap<String, List<Schedule>> scheduleByConsultantId = scheduleService.getScheduleByConsultantId(Integer.valueOf(cid));
        System.out.println(scheduleByConsultantId);
        return R.ok(scheduleByConsultantId);
    }

    @DeleteMapping
    public R<String> deleteSchedule(String sid) {
        boolean b = scheduleService.removeScheduleById(sid);
        if (b) {
            return R.ok("删除成功");
        }
        return R.fail(400, "删除失败，该时段已有预约");
    }

}
