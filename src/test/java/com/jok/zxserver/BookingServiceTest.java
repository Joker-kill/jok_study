package com.jok.zxserver;

import com.jok.zxserver.domain.DO.BookingCondition;
import com.jok.zxserver.domain.VO.BookingVO;
import com.jok.zxserver.domain.common.Constant;
import com.jok.zxserver.domain.common.enums.BooKingStatus;
import com.jok.zxserver.domain.entity.Booking;
import com.jok.zxserver.mapper.BookingMapper;
import com.jok.zxserver.service.BookingService;
import com.jok.zxserver.utils.CommonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/5 18:59
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingServiceTest {
    @Autowired
    BookingService bookingService;

    private CommonUtil commonUtil = new CommonUtil(6);

    @Test
    void testBooking(){
        for (int i = 0 ; i < 4 ; i++){
            Booking booking = new Booking();
            long id = commonUtil.nextId();
            System.out.println(id);
            booking.setId(String.valueOf(id));
            booking.setReservationId("opXOV5GXAAZ1VbTUosbRSBS50e1c");
            booking.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            booking.setBookStart("09:00");
            booking.setBookEnd("10:00");
            booking.setName("测试人员"+i);
            booking.setPhone("15588888888");
            booking.setConsultantId(101+i%3);
            booking.setSex("男");
//            booking.setStatus(Constant.BOOKING_ACCEPT);
            booking.setMessage("工作压力大");
            bookingService.booking(booking);
        }
    }
    @Test
    void testGetBookingList(){
        List<BookingVO> list1 = bookingService.getRecordsByUId("opXOV5GXAAZ1VbTUosbRSBS50e1c");
        List<BookingVO> list2 = bookingService.getRecordsByCId(101);
        System.out.println(list1);
        System.out.println(list2);
    }

    @Autowired
    BookingMapper bookingMapper;
    @Test
    void testBookingMapper1(){
        List<BookingVO> bookingVOList = bookingMapper.selectBookingsByCIdAndTime(101, "09:55", "12:30");
        for (BookingVO bv:bookingVOList){
            System.out.println(bv);
        }

    }
    @Test
    void testMapper1(){
        BookingCondition bookingCondition = new BookingCondition();
        bookingCondition.setStatus(3);
        bookingCondition.setDateStart("2024-08-05");
        bookingCondition.setDateEnd("2024-08-11");
        bookingCondition.setCName("咨询师B");
        bookingCondition.setConsultantId(103);
        List<BookingVO> bookingVOList = bookingMapper.selectBookingsByCondition(bookingCondition);
        for (BookingVO bv:bookingVOList){
            System.out.println(bv);
        }
    }

    @Test
    void testUpdateStatus(){
        bookingService.updateStatus("56942913235456000", BooKingStatus.ACCEPTED);
    }
}
