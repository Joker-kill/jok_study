package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.DO.BookingCondition;
import com.jok.zxserver.domain.VO.BookingVO;
import com.jok.zxserver.domain.common.enums.BooKingStatus;
import com.jok.zxserver.domain.entity.Booking;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/5 10:43
 */
public interface BookingService extends IService<Booking> {
    //预约
    boolean booking(Booking booking);
//    //用户撤销
//    boolean revokeRecordeByUser(String recordId);
//    //咨询师撤销
//    boolean revokeRecordeByConsultant(String recordId);
//    //咨询师接受
//    boolean acceptRecorde(String recordId);
//    //完成
//    boolean completeRecorde(String recordId);
    //根据咨询师id查询预约记录
    List<BookingVO> getRecordsByCId(Integer consultantId);
    //条件查询
    List<BookingVO> getRecordsByCondition(BookingCondition bookingCondition);
    //根据预约者id查询预约记录
    List<BookingVO> getRecordsByUId(String reservationId);
//    //根据id修改预约记录
    boolean changeRecordeById(String id);

    boolean updateStatus(String bookingId,BooKingStatus status);



}
