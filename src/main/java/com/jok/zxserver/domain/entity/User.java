package com.jok.zxserver.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_user")
public class User {
    private String id;
    private String userName;
    private String nickName;
    private String password;
    private String avatar;
    private String phone;
    private String sex;
    private Integer age;
    private String role;
    private String lastLoginTime;
}
