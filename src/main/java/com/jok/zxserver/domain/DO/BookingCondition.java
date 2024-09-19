package com.jok.zxserver.domain.DO;

import lombok.Data;

/**
 * @Author JOKER
 * create time 2024/8/11 18:32
 */
@Data
public class BookingCondition {
    private String dateStart;
    private String dateEnd;
    private Integer consultantId;
    private String reservationId;
    private Integer status;
    private String uName;
    private String cName;
//    private String timeStart;
//    private String timeEnd;

}
