package com.jok.zxserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jok.zxserver.domain.entity.Relationship;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/8/28 19:42
 */
@Mapper
public interface RelationshipMapper extends BaseMapper<Relationship> {
    @Select("select * from relationship where user1_id = #{userId} or user2_id =#{userId}")
    List<Relationship> selectRelationsByUId(@Param("userId") String userId);

    @Select("select count(*) from relationship " +
            "where (user1_id = #{userId} and user2_id = #{receiverId})" +
            "or(user1_id = #{receiverId} and user2_id = #{userId})")
    int selectRelationship(@Param("userId")String userId,@Param("receiverId")String receiverId);

    @Update("update relationship set last_date =#{date} where user1_id = #{senderId} or user2_id =#{senderId}")
    boolean updateLastDate(@Param("date") Date date,@Param("senderId") String senderId);

    @Select({"  SELECT CASE WHEN user1_id = #{userId} THEN user2_id ELSE user1_id END AS friend_id",
            "  FROM relationship",
            "  WHERE user1_id = #{userId} OR user2_id = #{userId}",
    })
    List<String> selectFriendsIds(@Param("userId") String UserId);

}
