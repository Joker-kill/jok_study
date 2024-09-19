package com.jok.zxserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jok.zxserver.domain.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author JOKER
 * create time 2024/8/4 18:09
 */
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
    @Update("update schedule set status = 0,deleted= 1 where id = #{id}")
    int deleteScheduleById(@Param("id") String id);
}
