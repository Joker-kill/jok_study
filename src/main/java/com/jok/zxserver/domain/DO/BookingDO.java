package com.jok.zxserver.domain.DO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/8/5 10:46
 */
@Data
public class BookingDO {
    private String reservationId;
    private Integer consultantId;
    private String name;
    private String sex;
    private String phone;
    private String message;
    private String date;
    private String startTime;
    private String endTime;
}
