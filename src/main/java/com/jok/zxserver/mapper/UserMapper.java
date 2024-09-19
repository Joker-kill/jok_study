package com.jok.zxserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jok.zxserver.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author JOKER
 * create time 2024/8/4 16:08
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
