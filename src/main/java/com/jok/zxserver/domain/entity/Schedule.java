package com.jok.zxserver.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author JOKER
 * create time 2024/8/3 19:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @TableId
    private String id;
    private Integer consultantId;
    private String date;
    private String timeStart;
    private String timeEnd;
    private Integer minTime;
    private Integer maxTime;
    private Integer status;
    private Integer deleted;
}
