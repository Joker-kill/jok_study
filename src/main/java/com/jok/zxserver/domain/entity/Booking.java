package com.jok.zxserver.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jok.zxserver.config.handlers.BooKingStatusTypeHandler;
import com.jok.zxserver.domain.common.enums.BooKingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author JOKER
 * create time 2024/8/3 19:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String sid;
    private String reservationId;//预约者id
    private String name;
    private String bookStart;
    private String bookEnd;
    private String message;
    private String sex;
    private String phone;
    private String date;
    private Integer consultantId;
    @TableField(typeHandler = BooKingStatusTypeHandler.class)
    private BooKingStatus status;
    private String backMessage;
    private String bookingDate;
}
