package com.jok.zxserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jok.zxserver.domain.DO.UserDO;
import com.jok.zxserver.domain.VO.LoginVo;
import com.jok.zxserver.domain.entity.User;
import com.jok.zxserver.mapper.UserMapper;
import com.jok.zxserver.service.UserService;
import com.jok.zxserver.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author JOKER
 * create time 2024/8/4 16:09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public LoginVo login(UserDO userDO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userDO.getOpenId());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            User newUser = new User();
            String userName = "lzx_";
            long l = System.currentTimeMillis();
            userName+=l;
            newUser.setUserName(userName);
            newUser.setNickName(userDO.getNickName());
            newUser.setSex(userDO.getGender()==0?"男":"女");
            newUser.setAvatar(userDO.getAvatarUrl());
            newUser.setId(userDO.getOpenId());
            newUser.setPassword("pwd"+userDO.getNickName()+userDO.getGender());
            newUser.setRole("usr");
            userMapper.insert(newUser);
        }

        String sign = TokenUtil.sign(userDO.getOpenId());
        LoginVo loginVo = new LoginVo();
        loginVo.setOpenId(userDO.getOpenId());
        loginVo.setToken(sign);
        loginVo.setRole(user.getRole());
        return loginVo;
    }

    @Override
    public LoginVo login(String userName, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName).eq("password", password));
        LoginVo loginVo = new LoginVo();

        if(user == null){
            return null;
        }
        String sign = TokenUtil.sign(user.getId());

        loginVo.setRole(user.getRole());
        loginVo.setOpenId(user.getId());
        loginVo.setToken(sign);
        return loginVo;
    }
}
