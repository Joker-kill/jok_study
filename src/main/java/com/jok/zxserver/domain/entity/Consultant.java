package com.jok.zxserver.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author JOKER
 * create time 2024/8/3 19:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consultant {
    private Integer id;
    private String userId;
    private String info;
    private String qualification;
    private String name;
    private String phone;
    private String avatar;
    private Integer deleted;
    private Integer status;

}
