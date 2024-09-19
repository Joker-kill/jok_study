package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.BookingCondition;
import com.jok.zxserver.domain.VO.BookingVO;
import com.jok.zxserver.domain.VO.ConsultantVO;
import com.jok.zxserver.domain.common.Constant;
import com.jok.zxserver.domain.common.enums.BooKingStatus;
import com.jok.zxserver.domain.entity.Booking;
import com.jok.zxserver.domain.entity.Consultant;
import com.jok.zxserver.mapper.BookingMapper;
import com.jok.zxserver.mapper.ScheduleMapper;
import com.jok.zxserver.service.BookingService;
import com.jok.zxserver.service.ConsultantService;
import com.jok.zxserver.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/5 10:43
 */
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    private CommonUtil commonUtil = new CommonUtil(1);

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    ConsultantService consultantService;

    @Override
    public boolean booking(Booking booking) {
        //查询用户预约的时间段是否已经被预约或重复预约
        String sid = booking.getSid();
        Boolean existBooking = bookingMapper.exists(new QueryWrapper<Booking>().eq("sid", sid));

        // todo 后续添加拒绝已存在的预约
        if(existBooking){
            System.out.println("预约失败，该时段已被预约");
            return false;
        }

        Long id = commonUtil.nextId();
        booking.setId(String.valueOf(id));
        booking.setStatus(BooKingStatus.NOT_ACCEPT);
        int insert = bookingMapper.insert(booking);
        return insert>0;
    }

//    @Override
//    public boolean revokeRecordeByUser(String recordId) {
//        int i = bookingMapper.updateStatus(recordId, Constant.BOOKING_REVOKE_BY_U);
//        return i>0;
//    }
//
//    @Override
//    public boolean revokeRecordeByConsultant(String recordId) {
//        int i = bookingMapper.updateStatus(recordId, Constant.BOOKING_REVOKE_BY_C);
//        return i>0;
//    }

//    @Override
//    public boolean acceptRecorde(String recordId) {
//        int i = bookingMapper.updateStatus(recordId, Constant.BOOKING_ACCEPT);
//        return i>0;
//    }
//
//    @Override
//    public boolean completeRecorde(String recordId) {
//        int i = bookingMapper.updateStatus(recordId, Constant.BOOKING_COMPLETE);
//        return i>0;
//    }

    @Override
    public List<BookingVO> getRecordsByCId(Integer consultantId) {
        ConsultantVO consultant = consultantService.getConsultantInfoById(consultantId);
//        Consultant consultant = consultantService.getById(consultantId);
        List<Booking> bookingList = bookingMapper.selectList(new QueryWrapper<Booking>().eq("consultant_id", consultantId));

        List<BookingVO> bookingVOS = new ArrayList<>();
        for(Booking booking:bookingList){
            BookingVO bookingVO = new BookingVO();
            BeanUtils.copyProperties(booking,bookingVO);
            bookingVO.setConsultantName(consultant.getName());
            bookingVO.setConsultantAvatar(consultant.getAvatar());
            bookingVOS.add(bookingVO);

        }
        return bookingVOS;
    }

    @Override
    public List<BookingVO> getRecordsByCondition(BookingCondition bookingCondition) {
        List<BookingVO> bookingVOList = bookingMapper.selectBookingsByCondition(bookingCondition);
        return bookingVOList;
    }

    @Override
    public List<BookingVO> getRecordsByUId(String reservationId) {

        List<Booking> bookingList = bookingMapper.selectList(new QueryWrapper<Booking>().eq("reservation_id", reservationId));

        List<BookingVO> bookingVOList = new ArrayList<>();
        for(Booking booking:bookingList){
            ConsultantVO consultant = consultantService.getConsultantInfoById(booking.getConsultantId());
            if(consultant == null){
                System.out.println("编号"+booking.getConsultantId()+"的咨询师不存在");
                continue;
            }
            BookingVO bookingVO = new BookingVO();
            BeanUtils.copyProperties(booking,bookingVO);
            bookingVO.setConsultantAvatar(consultant.getAvatar());
            bookingVO.setConsultantName(consultant.getName());
            bookingVOList.add(bookingVO);
        }
        return bookingVOList;
    }

    @Override
    public boolean changeRecordeById(String id) {
        return false;
    }

    @Override
    public boolean updateStatus(String bookingId,BooKingStatus status) {
        int i = bookingMapper.updateStatus(bookingId, status);
        return i>0;
    }
}
