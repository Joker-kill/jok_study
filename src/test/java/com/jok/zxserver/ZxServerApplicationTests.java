package com.jok.zxserver;

import com.jok.zxserver.domain.entity.Schedule;
import com.jok.zxserver.service.ConsultantService;
import com.jok.zxserver.service.ScheduleService;
import com.jok.zxserver.utils.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

@SpringBootTest
class ZxServerApplicationTests {

	@Test
	void contextLoads() throws IOException {
		Map<String, String> appInfo = CommonUtil.getAppInfo();
		System.out.println(appInfo.get("appId"));
		CommonUtil commonUtil = new CommonUtil(1);
		System.out.println(appInfo.get("appSecret"));
		for (int i = 0;i<10;i++){
			long l = commonUtil.nextId();
			System.out.println(l);
		}

	}

	@Autowired
	ScheduleService scheduleService;

	@Test
	void testScheduleService(){
		CommonUtil commonUtil = new CommonUtil(1);
		int timeStart = 8;
		for (int i = 0;i<10;i++){
			Schedule schedule = new Schedule();
			schedule.setId(String.valueOf(commonUtil.nextId()));
			schedule.setConsultantId(101+(i%3));
			schedule.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			schedule.setTimeStart(((timeStart+i)>9?"":0+"")+(timeStart+i)+":00");
			schedule.setTimeEnd(((timeStart+i+1)>9?"":0+"")+(timeStart+i+1)+":00");
//			schedule.setMaxPerson(5);
			schedule.setStatus(i%2);
			System.out.println(schedule);
			scheduleService.save(schedule);
		}
	}

	@Autowired
	ConsultantService consultantService;
	@Test
	void testScheduleService2(){
		List<Schedule> scheduleByTime = scheduleService.getScheduleByDate(new Date());
		for (Schedule s :scheduleByTime){
			System.out.println(s);
		}
		consultantService.getOnlineConsultantList();
	}

}
