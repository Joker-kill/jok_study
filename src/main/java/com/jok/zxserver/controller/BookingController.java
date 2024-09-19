package com.jok.zxserver.controller;

import com.jok.zxserver.domain.DO.BookingCondition;
import com.jok.zxserver.domain.R;
import com.jok.zxserver.domain.VO.BookingVO;
import com.jok.zxserver.domain.entity.Booking;
import com.jok.zxserver.service.BookingService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/10 9:26
 */
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public R<String> booking(@RequestBody Booking booking){
        System.out.println(booking);
        bookingService.booking(booking);
        return R.ok("请求成功");
    }

    @GetMapping("/r/list")
    public R<List<BookingVO>> getReservationBookingList(@PathParam("rid") String rid){
        List<BookingVO> recordsByUId = bookingService.getRecordsByUId(rid);
        return R.ok(recordsByUId);
    }

    @GetMapping("/c/list")
    public R<List<BookingVO>> getConsultantBookingList(@PathParam("cid") String cid){
        List<BookingVO> recordsByUId = bookingService.getRecordsByCId(Integer.valueOf(cid));
        return R.ok(recordsByUId);
    }

    @PostMapping("/withCondition")
    public R<List<BookingVO>> getBookingListWithCondition(@RequestBody BookingCondition condition){
        List<BookingVO> recordsByCondition = bookingService.getRecordsByCondition(condition);
        return R.ok(recordsByCondition);
    }

    @PostMapping("/revoke")
    public R<String> revokeBooking(){
        return R.ok("删除成功");
    }

}
