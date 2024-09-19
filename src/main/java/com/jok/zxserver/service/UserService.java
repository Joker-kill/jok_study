package com.jok.zxserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jok.zxserver.domain.DO.UserDO;
import com.jok.zxserver.domain.VO.LoginVo;
import com.jok.zxserver.domain.entity.User;

/**
 * @Author JOKER
 * create time 2024/8/4 16:08
 */

public interface UserService extends IService<User> {

    LoginVo login(UserDO userDO);
    LoginVo login(String userName,String password);
}
