package com.jok.zxserver.domain.VO;

import com.jok.zxserver.domain.entity.Booking;
import lombok.Data;


/**
 * @Author JOKER
 * create time 2024/8/5 11:14
 */
@Data
public class BookingVO extends Booking {
    private String consultantName;
    private String consultantAvatar;

    @Override
    public String toString() {

        return super.toString() +
                "consultantName='" + consultantName + '\'' +
                ", consultantAvatar='" + consultantAvatar + '\'' +
                '}';
    }
}
