package com.jok.zxserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jok.zxserver.domain.DO.BookingCondition;
import com.jok.zxserver.domain.VO.BookingVO;
import com.jok.zxserver.domain.common.enums.BooKingStatus;
import com.jok.zxserver.domain.entity.Booking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/5 10:40
 */

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {

//    @Update("update booking set status = #{status} where id = #{id}")
//    int updateStatus(@Param("id") String id, @Param("status")Integer status);

    @Update("update booking set status = #{status,typeHandler=com.jok.zxserver.config.handlers.BooKingStatusTypeHandler} where id = #{id}")
    int updateStatus(@Param("id") String id, @Param("status") BooKingStatus status);
    //todo 写一个根据consultantId，开始时间，结束时间获取对应咨询师在时间段内的预约记录
    List<BookingVO> selectBookingsByCIdAndTime(Integer consultantId,String startTime,String endTime);

    List<BookingVO> selectBookingsByCondition(BookingCondition condition);

}
